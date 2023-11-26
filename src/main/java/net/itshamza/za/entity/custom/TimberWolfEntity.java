package net.itshamza.za.entity.custom;

import net.itshamza.za.entity.ModEntityCreator;
import net.itshamza.za.item.ModItems;
import net.itshamza.za.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.*;
import java.util.function.Predicate;


public class TimberWolfEntity extends Animal implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    public float nextJostleAngleFromServer;

    protected static final EntityDimensions JOSTLING_SIZE = EntityDimensions.scalable(0.4F, 0.5F);
    private static final EntityDataAccessor<Boolean> JOSTLING = SynchedEntityData.defineId(TimberWolfEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> JOSTLE_ANGLE = SynchedEntityData.defineId(TimberWolfEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Optional<UUID>> JOSTLER_UUID = SynchedEntityData.defineId(TimberWolfEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    public float prevJostleAngle;
    public float prevJostleProgress;
    public float jostleProgress;
    public boolean jostleDirection;
    public int jostleTimer = 0;
    public boolean instantlyTriggerJostleAI = false;
    public int jostleCooldown = 100 + random.nextInt(40);
    private boolean hasJostlingSize;

    public TimberWolfEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }


    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.25f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Cow.class, 5, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Pig.class, 5, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Sheep.class, 5, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Horse.class, 5, true, true, (Predicate<LivingEntity>)null));
        //this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, DeerEntity.class, 5, true, true, (Predicate<LivingEntity>)null));
        this.goalSelector.addGoal(4, new WolfAIJostle(this));
        this.goalSelector.addGoal(4, new TimberWolfEntityAttackPlayersGoal());
        this.goalSelector.addGoal(4, new TimberWolfEntityHurtByTargetGoal());
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(JOSTLING, Boolean.valueOf(false));
        this.entityData.define(JOSTLE_ANGLE, 0F);
        this.entityData.define(JOSTLER_UUID, Optional.empty());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.jostleCooldown = compound.getInt("JostlingCooldown");
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("JostlingCooldown", this.jostleCooldown);
    }

    // ANIMATIONS //

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.2F;
    }
    
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.isAggressive()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
            return PlayState.CONTINUE;
        }
        if (this.isJostling()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("jostle", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isAggressive()) {
            return SoundEvents.WOLF_GROWL;
        } else if (this.random.nextInt(3) == 0) {
            return this.getHealth() < 10.0F ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
        } else {
            return SoundEvents.WOLF_AMBIENT;
        }
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.WOLF_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }



    private PlayState attackPredicate(AnimationEvent event) {
        if(this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", false));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
        data.addAnimationController(new AnimationController(this, "attackController",
                0, this::attackPredicate));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return ModEntityCreator.TIMBER_WOLF.get().create(p_146743_);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.getItem() == ModItems.RAW_VENISON.get();
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        boolean flag = pEntity.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, pEntity);
            Random random = new Random();

            // Define the probability (chance) as a decimal between 0 and 1
            double probability = 0.5; // 50% chance, adjust as needed

            if (random.nextDouble() < probability) {
                this.spawnAtLocation(new ItemStack(Items.BONE));
            } else {
                return flag;
            }
        }

        return flag;
    }

    public void tick() {
        prevJostleAngle = this.getJostleAngle();
        super.tick();
        prevJostleProgress = jostleProgress;

        if (isJostling() && !hasJostlingSize){
            refreshDimensions();
            hasJostlingSize = true;
        }
        if (!isJostling() && hasJostlingSize){
            refreshDimensions();
            hasJostlingSize = false;
        }
        if (this.isJostling() && jostleProgress < 5F) {
            jostleProgress++;
        }
        if (!this.isJostling() && jostleProgress > 0F) {
            jostleProgress--;
        }

        if (jostleCooldown > 0) {
            jostleCooldown--;
        }
        if(!level.isClientSide){
            if(this.getJostleAngle() < nextJostleAngleFromServer){
                this.setJostleAngle(this.getJostleAngle() + 1);

            }
            if(this.getJostleAngle() > nextJostleAngleFromServer) {
                this.setJostleAngle(this.getJostleAngle() - 1);
            }
        }

        if(this.isAggressive()){
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35f);
        }else{
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25f);
        }
    }

    public boolean isJostling() {
        return this.entityData.get(JOSTLING).booleanValue();
    }

    public void setJostling(boolean jostle) {
        this.entityData.set(JOSTLING, jostle);
    }

    public float getJostleAngle() {
        return this.entityData.get(JOSTLE_ANGLE);
    }

    public void setJostleAngle(float scale) {
        this.entityData.set(JOSTLE_ANGLE, scale);
    }

    @javax.annotation.Nullable
    public UUID getJostlingPartnerUUID() {
        return this.entityData.get(JOSTLER_UUID).orElse(null);
    }

    public void setJostlingPartnerUUID(@javax.annotation.Nullable UUID uniqueId) {
        this.entityData.set(JOSTLER_UUID, Optional.ofNullable(uniqueId));
    }

    @javax.annotation.Nullable
    public Entity getJostlingPartner() {
        UUID id = getJostlingPartnerUUID();
        if (id != null && !level.isClientSide) {
            return ((ServerLevel) level).getEntity(id);
        }
        return null;
    }

    public void setJostlingPartner(@javax.annotation.Nullable Entity jostlingPartner) {
        if (jostlingPartner == null) {
            this.setJostlingPartnerUUID(null);
        } else {
            this.setJostlingPartnerUUID(jostlingPartner.getUUID());
        }
    }

    public void pushBackJostling(TimberWolfEntity entityMoose, float strength) {
        applyKnockbackFromMoose(strength, entityMoose.getX() - this.getX(), entityMoose.getZ() - this.getZ());
    }

    public boolean canJostleWith(TimberWolfEntity moose) {
        return !moose.isVehicle() && moose.isBaby() && moose.getJostlingPartnerUUID() == null && moose.jostleCooldown == 0;
    }

    private void applyKnockbackFromMoose(float strength, double ratioX, double ratioZ) {
        net.minecraftforge.event.entity.living.LivingKnockBackEvent event = net.minecraftforge.common.ForgeHooks.onLivingKnockBack(this, strength, ratioX, ratioZ);
        if (event.isCanceled()) return;
        strength = event.getStrength();
        ratioX = event.getRatioX();
        ratioZ = event.getRatioZ();
        if (!(strength <= 0.0F)) {
            this.hasImpulse = true;
            Vec3 vector3d = this.getDeltaMovement();
            Vec3 vector3d1 = (new Vec3(ratioX, 0.0D, ratioZ)).normalize().scale(strength);
            this.setDeltaMovement(vector3d.x / 2.0D - vector3d1.x, 0.3F, vector3d.z / 2.0D - vector3d1.z);
        }
    }

    public class WolfAIJostle  extends Goal {

        private static final TargetingConditions JOSTLE_PREDICATE = TargetingConditions.forNonCombat().range(16D).ignoreLineOfSight();
        protected TimberWolfEntity targetKomodoDragon;
        private TimberWolfEntity komodo;
        private Level world;
        private float angle;

        public WolfAIJostle(TimberWolfEntity moose) {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.TARGET));
            this.komodo = moose;
            this.world = moose.level;
        }

        @Override
        public boolean canUse() {
            if (this.komodo.isJostling() || this.komodo.isInLove() || this.komodo.isVehicle() || !this.komodo.isBaby() || this.komodo.getTarget() != null || this.komodo.jostleCooldown > 0) {
                return false;
            }
            if(this.komodo.instantlyTriggerJostleAI || this.komodo.getRandom().nextInt(30) == 0){
                this.komodo.instantlyTriggerJostleAI = false;
                if (this.komodo.getJostlingPartner() instanceof TimberWolfEntity) {
                    targetKomodoDragon = (TimberWolfEntity) komodo.getJostlingPartner();
                    return targetKomodoDragon.jostleCooldown == 0;
                } else {
                    TimberWolfEntity possiblePartner = this.getNearbyKomodoDragon();
                    if (possiblePartner != null) {
                        this.komodo.setJostlingPartner(possiblePartner);
                        possiblePartner.setJostlingPartner(komodo);
                        targetKomodoDragon = possiblePartner;
                        targetKomodoDragon.instantlyTriggerJostleAI = true;
                        return true;
                    }
                }
            }
            return false;
        }

        public void start(){
            this.komodo.jostleTimer = 0;
            this.angle = 0;
            setJostleDirection(this.komodo.getRandom().nextBoolean());
        }

        public void setJostleDirection(boolean dir){
            this.komodo.jostleDirection = dir;
            this.targetKomodoDragon.jostleDirection = dir;
        }

        public void stop() {
            this.komodo.setJostling(false);
            this.komodo.setJostlingPartner(null);
            this.komodo.jostleTimer = 0;
            this.angle = 0;
            this.komodo.getNavigation().stop();
            if (this.targetKomodoDragon != null) {
                this.targetKomodoDragon.setJostling(false);
                this.targetKomodoDragon.setJostlingPartner(null);
                this.targetKomodoDragon.jostleTimer = 0;
                this.targetKomodoDragon = null;
            }

        }

        public void tick() {
            if(targetKomodoDragon != null){
                this.komodo.lookAt(targetKomodoDragon, 360, 180);
                this.komodo.setJostling(true);
                float x = (float)(komodo.getX() - targetKomodoDragon.getX());
                float y = Math.abs((float)(komodo.getY() - targetKomodoDragon.getY()));
                float z = (float)(komodo.getZ() - targetKomodoDragon.getZ());
                double distXZ = Math.sqrt((x * x + z * z));
                if (distXZ < 1.8F) {
                    this.komodo.getNavigation().stop();
                    this.komodo.getMoveControl().strafe(-0.5F, 0);
                } else if(distXZ > 2.4F) {
                    this.komodo.setJostling(false);
                    this.komodo.getNavigation().moveTo(targetKomodoDragon, 1);
                }else{
                    this.komodo.lookAt(targetKomodoDragon, 360, 180);
                    float f = komodo.getRandom().nextFloat() - 0.5F;
                    //perfect jostle condition
                    if(komodo.jostleDirection){
                        if(angle < 10){
                            angle += 1;
                        }else{
                            komodo.jostleDirection = false;
                        }
                        this.komodo.getMoveControl().strafe(f * 1, -0.4F);
                    }
                    if(!komodo.jostleDirection){
                        if(angle > -10){
                            angle -= 1;
                        }else{
                            komodo.jostleDirection = true;
                        }
                        this.komodo.getMoveControl().strafe(f * 1, 0.4F);
                    }
                    if(this.komodo.getRandom().nextInt(15) == 0 && this.komodo.isOnGround()){
                        komodo.pushBackJostling(targetKomodoDragon, 0.1F);
                    }
                    komodo.nextJostleAngleFromServer = angle;
                    this.komodo.jostleTimer++;
                    this.targetKomodoDragon.jostleTimer++;
                    if(this.komodo.jostleTimer > 500 || y > 2.0F){
                        komodo.hasImpulse = true;
                        if(komodo.isOnGround()){
                            komodo.pushBackJostling(targetKomodoDragon, 0.4F);
                        }
                        if(targetKomodoDragon.isOnGround()){
                            targetKomodoDragon.pushBackJostling(komodo, 0.4F);
                        }
                        this.komodo.jostleTimer = 0;
                        this.targetKomodoDragon.jostleTimer = 0;
                        this.komodo.jostleCooldown = 700 + this.komodo.getRandom().nextInt(2000);
                        this.targetKomodoDragon.jostleTimer = 0;
                        this.targetKomodoDragon.jostleCooldown = 700 + this.targetKomodoDragon.getRandom().nextInt(2000);
                        this.stop();
                    }
                }
            }

        }


        @Override
        public boolean canContinueToUse() {
            return this.komodo.isBaby() && !this.komodo.isInLove() && !this.komodo.isVehicle() && this.komodo.getTarget() == null && targetKomodoDragon != null && targetKomodoDragon.isAlive() && komodo.jostleCooldown == 0 && targetKomodoDragon.jostleCooldown == 0;
        }

        @javax.annotation.Nullable
        private TimberWolfEntity getNearbyKomodoDragon() {
            List<TimberWolfEntity> komodoDragons = this.world.getNearbyEntities(TimberWolfEntity.class, JOSTLE_PREDICATE, this.komodo, this.komodo.getBoundingBox().inflate(16.0D));
            double lvt_2_1_ = 1.7976931348623157E308D;
            TimberWolfEntity lvt_4_1_ = null;
            Iterator var5 = komodoDragons.iterator();

            while (var5.hasNext()) {
                TimberWolfEntity lvt_6_1_ = (TimberWolfEntity) var5.next();
                if (this.komodo.canJostleWith(lvt_6_1_) && this.komodo.distanceToSqr(lvt_6_1_) < lvt_2_1_) {
                    lvt_4_1_ = lvt_6_1_;
                    lvt_2_1_ = this.komodo.distanceToSqr(lvt_6_1_);
                }
            }

            return lvt_4_1_;
        }

    }

    class TimberWolfEntityAttackPlayersGoal extends NearestAttackableTargetGoal<Player> {
        public TimberWolfEntityAttackPlayersGoal() {
            super(TimberWolfEntity.this, Player.class, 20, true, true, (Predicate<LivingEntity>)null);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (TimberWolfEntity.this.isBaby()) {
                return false;
            } else {
                if (super.canUse()) {
                    for(TimberWolfEntity polarbear : TimberWolfEntity.this.level.getEntitiesOfClass(TimberWolfEntity.class, TimberWolfEntity.this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D))) {
                        if (polarbear.isBaby()) {
                            return true;
                        }
                    }
                }

                return false;
            }
        }

        protected double getFollowDistance() {
            return super.getFollowDistance() * 0.5D;
        }
    }

    class TimberWolfEntityHurtByTargetGoal extends HurtByTargetGoal {
        public TimberWolfEntityHurtByTargetGoal() {
            super(TimberWolfEntity.this);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            if (TimberWolfEntity.this.isBaby()) {
                this.alertOthers();
                this.stop();
            }

        }

        protected void alertOther(Mob pMob, LivingEntity pTarget) {
            if (pMob instanceof TimberWolfEntity && !pMob.isBaby()) {
                super.alertOther(pMob, pTarget);
            }

        }
    }

    static class WolfPreyGoal extends NearestAttackableTargetGoal {


        public WolfPreyGoal(Mob pMob, Class pTargetType, boolean pMustSee) {
            super(pMob, pTargetType, pMustSee);
        }

        @Override
        public void start() {
            LivingEntity target = this.mob.getTarget();
            if (target == null) {
                return;
            }
            if(this.mob.level.isNight()) {
                this.mob.playSound(ModSounds.WOLF_HOWL.get());
            }
        }
    }

}
