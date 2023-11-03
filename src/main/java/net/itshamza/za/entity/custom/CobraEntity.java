package net.itshamza.za.entity.custom;

import net.itshamza.za.entity.custom.ai.AnimalAIWanderRanged;
import net.itshamza.za.entity.custom.ai.PoisonTwoMeleeAttackGoal;
import net.itshamza.za.entity.custom.ai.VenomMeleeAttackGoal;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.List;
import java.util.function.Predicate;


public class CobraEntity extends Animal implements GeoEntity {

    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    public float prevCurlProgress;
    public float curlProgress;
    public int maxCurlTime = 75;
    private int curlTime = 0;
    private int loopSoundTick = 0;
    private static final EntityDataAccessor<Boolean> BURRIED = SynchedEntityData.defineId(CobraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CURLED = SynchedEntityData.defineId(CobraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final Predicate<LivingEntity> WARNABLE_PREDICATE = (mob) -> {
        return mob instanceof Player && !((Player) mob).isCreative() && !mob.isSpectator() || mob instanceof SteppeEagleEntity;
    };
    private static final Predicate<LivingEntity> TARGETABLE_PREDICATE = (mob) -> {
        return mob instanceof Player && !((Player) mob).isCreative() && !mob.isSpectator() || mob instanceof SteppeEagleEntity;
    };

    public CobraEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }


    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WarnPredatorsGoal());
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F){
            public boolean canUse() { return !CobraEntity.this.isRattling() && super.canUse(); }
        });
        this.goalSelector.addGoal(5, new AnimalAIWanderRanged(this, 60, 1.0D, 7, 7));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new VenomMeleeAttackGoal(this, (double)1.2F, true));
        this.targetSelector.addGoal(4, new ShortDistanceTarget());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Rabbit.class, 15, true, true, null));
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

    private PlayState predicate(software.bernie.geckolib.core.animation.AnimationState animationState) {
        if(animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if(this.isRattling()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("hood", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationState state) {
        if(this.swinging && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            state.getController().forceAnimationReset();
            state.getController().setAnimation(RawAnimation.begin().then("attack", Animation.LoopType.PLAY_ONCE));
            this.swinging = false;
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller",
                0, this::predicate));
        controllers.add(new AnimationController(this, "attackController",
                0, this::attackPredicate));
    }

    public void travel(Vec3 vec3d) {
        if (!this.isFallFlying() && this.isCurled()) {
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
    public AnimatableInstanceCache getAnimatableInstanceCache() {
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
        if(!level().isClientSide && this.isCurled() && (this.getTarget() != null && this.getTarget().isAlive())){
            this.setCurled(false);
        }
        if(!level().isClientSide && this.isRattling()  && this.getTarget() == null){
            this.setCurled(true);

        }
        if (!level().isClientSide && !this.isCurled() && this.getTarget() == null && random.nextInt(500) == 0) {
            maxCurlTime = 300 + random.nextInt(250);
            this.setCurled(true);
        }
    }

    class WarnPredatorsGoal extends Goal {
        Entity target = null;

        @Override
        public boolean canUse() {
            double dist = 20D;
            List<LivingEntity> list = CobraEntity.this.level().getEntitiesOfClass(LivingEntity.class, CobraEntity.this.getBoundingBox().inflate(dist, dist, dist), WARNABLE_PREDICATE);
            double d0 = dist;
            Entity possibleTarget = null;
            for(Entity entity : list) {
                double d1 = CobraEntity.this.distanceToSqr(entity);
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
            return target != null && CobraEntity.this.distanceTo(target) < 20D && CobraEntity.this.getTarget() == null;
        }

        @Override
        public void stop() {
            target = null;
            CobraEntity.this.setRattling(false);
        }

        @Override
        public void tick(){
            CobraEntity.this.setRattling(true);
            CobraEntity.this.curlTime = 0;
        }
    }

    class ShortDistanceTarget extends NearestAttackableTargetGoal<Player> {
        public ShortDistanceTarget() {
            super(CobraEntity.this, Player.class, 6, true, true, TARGETABLE_PREDICATE);
        }

        public boolean canUse() {
            if (CobraEntity.this.isBaby()) {
                return false;
            } else {
                return super.canUse();
            }
        }

        public void start(){
            super.start();
            CobraEntity.this.setCurled(false);
            CobraEntity.this.setRattling(true);
        }

        protected double getFollowDistance() {
            return 2D;
        }
    }
}
