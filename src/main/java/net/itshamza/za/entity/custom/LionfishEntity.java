package net.itshamza.za.entity.custom;

import net.itshamza.za.effects.ModEffects;
import net.itshamza.za.entity.custom.ai.GivePoisonWhenNear;
import net.itshamza.za.entity.custom.ai.GoInvisibleWhenNear;
import net.itshamza.za.entity.custom.ai.PoisonTwoMeleeAttackGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.function.Predicate;

public class LionfishEntity extends AbstractSchoolingFish implements GeoEntity {

    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    private static final EntityDataAccessor<Integer> MOISTNESS_LEVEL = SynchedEntityData.defineId(LionfishEntity.class, EntityDataSerializers.INT);
    private static final Predicate<LivingEntity> TARGETABLE_PREDICATE = (mob) -> {
        return mob instanceof Player && !((Player) mob).isCreative() && !mob.isSpectator();
    };
    public LionfishEntity(EntityType<? extends AbstractSchoolingFish> p_30341_, Level p_30342_) {
        super(p_30341_, p_30342_);
        this.moveControl = new SmoothSwimmingMoveControl(this, 20, 12, 0.4F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 3);
    }


    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.ATTACK_DAMAGE, 0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(1, new PoisonTwoMeleeAttackGoal(this, (double)1.2F, true));
        this.goalSelector.addGoal(1, new PoisonTwoMeleeAttackGoal(this, (double)1.2F, true));
        this.targetSelector.addGoal(4, new ShortDistanceTarget());
        this.goalSelector.addGoal(4, new GivePoisonWhenNear<>(this, LivingEntity.class, 10.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new GivePoisonWhenNear<>(this, Player.class, 10.0F, 2.2D, 2.2D));
    }

    // ANIMATIONS //

    protected float getSoundVolume() {
        return 0.2F;
    }

    private PlayState predicate(software.bernie.geckolib.core.animation.AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("swim", Animation.LoopType.LOOP));
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

    @Nullable

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }


    class ShortDistanceTarget extends NearestAttackableTargetGoal<Player> {
        public ShortDistanceTarget() {
            super(LionfishEntity.this, Player.class, 2, true, true, TARGETABLE_PREDICATE);
        }

        public boolean canUse() {
            if (LionfishEntity.this.isBaby()) {
                return false;
            } else {
                return super.canUse();
            }
        }

        public void start(){
            super.start();
        }

        protected double getFollowDistance() {
            return 2D;
        }
    }


    protected PathNavigation createNavigation(Level p_28362_) {
        return new WaterBoundPathNavigation(this, p_28362_);
    }

    protected float getStandingEyeHeight(Pose p_28352_, EntityDimensions p_28353_) {
        return 0.3F;
    }

    public int getMaxHeadXRot() {
        return 1;
    }

    public int getMaxHeadYRot() {
        return 1;
    }

    protected SoundEvent getSwimSplashSound() {
        return SoundEvents.DOLPHIN_SPLASH;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.DOLPHIN_SWIM;
    }

    public void travel(Vec3 p_28383_) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), p_28383_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(p_28383_);
        }

    }

    @Override
    protected SoundEvent getFlopSound() {
        return null;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return null;
    }

    private void touch(Mob p_29606_) {
        if (p_29606_.hurt(this.level().damageSources().mobAttack(this), 1)) {
            p_29606_.addEffect(new MobEffectInstance(MobEffects.POISON, 2400, 1), this);
        }

    }

    public void playerTouch(LivingEntity p_29617_) {
        if (p_29617_ instanceof LivingEntity && p_29617_.hurt(this.level().damageSources().mobAttack(this), (float)(1))) {
            p_29617_.addEffect(new MobEffectInstance(MobEffects.POISON, 2400, 1), this);
        }

    }

}
