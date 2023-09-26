package net.itshamza.za.entity.custom;

import net.itshamza.za.entity.custom.ai.AnimalAIWanderRanged;
import net.itshamza.za.entity.custom.ai.PoisonTwoMeleeAttackGoal;
import net.itshamza.za.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.function.Predicate;


public class RattlesnakeEntity extends Animal implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    public float prevCurlProgress;
    public float curlProgress;
    public int randomToungeTick = 0;
    public int maxCurlTime = 75;
    private int curlTime = 0;
    private int loopSoundTick = 0;
    private static final EntityDataAccessor<Boolean> BURRIED = SynchedEntityData.defineId(RattlesnakeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CURLED = SynchedEntityData.defineId(RattlesnakeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final Predicate<LivingEntity> WARNABLE_PREDICATE = (mob) -> {
        return mob instanceof Player && !((Player) mob).isCreative() && !mob.isSpectator() || mob instanceof SteppeEagleEntity;
    };
    private static final Predicate<LivingEntity> TARGETABLE_PREDICATE = (mob) -> {
        return mob instanceof Player && !((Player) mob).isCreative() && !mob.isSpectator() || mob instanceof SteppeEagleEntity;
    };

    public RattlesnakeEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }


    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WarnPredatorsGoal());
        this.goalSelector.addGoal(5, new AnimalAIWanderRanged(this, 60, 1.0D, 7, 7));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new PoisonTwoMeleeAttackGoal(this, (double)1.2F, true));
        this.targetSelector.addGoal(4, new ShortDistanceTarget());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Rabbit.class, 15, true, true, null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, FennecFoxEntity.class, 15, true, true, null));
    }

    // ANIMATIONS //

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.HISS.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.SNAKE_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.SNAKE_DIE.get();
    }

    public boolean canBeAffected(MobEffectInstance potioneffectIn) {
        if (potioneffectIn.getEffect() == MobEffects.POISON) {
            return false;
        }
        return super.canBeAffected(potioneffectIn);
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CURLED, Boolean.valueOf(false));
        this.entityData.define(BURRIED, Boolean.valueOf(false));
    }

    public boolean isCurled() {
        return this.entityData.get(CURLED).booleanValue();
    }

    public void setCurled(boolean curled) {
        this.entityData.set(CURLED, curled);
    }

    public boolean isRattling() {
        return this.entityData.get(BURRIED).booleanValue();
    }

    public void setRattling(boolean rattling) {
        this.entityData.set(BURRIED, rattling);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("move", true));
            return PlayState.CONTINUE;
        }

        if (this.isRattling()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("rattle", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
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

    public void travel(Vec3 vec3d) {
        if (this.isOnGround() && this.isCurled()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            vec3d = Vec3.ZERO;
        }
        super.travel(vec3d);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public void tick(){
        super.tick();
        prevCurlProgress = curlProgress;
        if (this.isCurled() && curlProgress < 5) {
            curlProgress += 0.5F;
        }
        if (!this.isCurled() && curlProgress > 0) {
            curlProgress -= 1;
        }
        if (isCurled() && !isRattling() && ++curlTime > maxCurlTime) {
            this.setCurled(false);
            curlTime = 0;
            maxCurlTime = 75 + random.nextInt(50);
        }
        if(!level.isClientSide && this.isCurled() && (this.getTarget() != null && this.getTarget().isAlive())){
            this.setCurled(false);
        }
        if(!level.isClientSide && this.isRattling()  && this.getTarget() == null){
            this.setCurled(true);

        }
        if (!level.isClientSide && !this.isCurled() && this.getTarget() == null && random.nextInt(500) == 0) {
            maxCurlTime = 300 + random.nextInt(250);
            this.setCurled(true);
        }
        if(isRattling()){
            if(loopSoundTick == 0){
                this.playSound(ModSounds.RATTLE.get(), this.getSoundVolume() * 0.5F, this.getVoicePitch());
            }
            loopSoundTick++;
            if(loopSoundTick > 20){
                loopSoundTick = 0;
            }
        }
    }

    class WarnPredatorsGoal extends Goal {
        Entity target = null;

        @Override
        public boolean canUse() {
            double dist = 20D;
            List<LivingEntity> list = RattlesnakeEntity.this.level.getEntitiesOfClass(LivingEntity.class, RattlesnakeEntity.this.getBoundingBox().inflate(dist, dist, dist), WARNABLE_PREDICATE);
            double d0 = dist;
            Entity possibleTarget = null;
            for(Entity entity : list) {
                double d1 = RattlesnakeEntity.this.distanceToSqr(entity);
                if (!(d1 > d0)) {
                    d0 = d1;
                    possibleTarget = entity;
                }
            }
            target = possibleTarget;
            return !list.isEmpty();
        }

        @Override
        public boolean canContinueToUse(){
            return target != null && RattlesnakeEntity.this.distanceTo(target) < 6D && RattlesnakeEntity.this.getTarget() == null;
        }

        @Override
        public void stop() {
            target = null;
            RattlesnakeEntity.this.setRattling(false);
        }

        @Override
        public void tick(){
            RattlesnakeEntity.this.setRattling(true);
            RattlesnakeEntity.this.curlTime = 0;
        }
    }

    class ShortDistanceTarget extends NearestAttackableTargetGoal<Player> {
        public ShortDistanceTarget() {
            super(RattlesnakeEntity.this, Player.class, 2, true, true, TARGETABLE_PREDICATE);
        }

        public boolean canUse() {
            if (RattlesnakeEntity.this.isBaby()) {
                return false;
            } else {
                return super.canUse();
            }
        }

        public void start(){
            super.start();
            RattlesnakeEntity.this.setCurled(false);
            RattlesnakeEntity.this.setRattling(true);
        }

        protected double getFollowDistance() {
            return 2D;
        }
    }
}
