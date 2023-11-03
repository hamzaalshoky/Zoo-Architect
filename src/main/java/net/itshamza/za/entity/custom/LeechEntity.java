package net.itshamza.za.entity.custom;

import net.itshamza.za.entity.ModEntityCreator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
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

import java.util.EnumSet;
import java.util.function.Predicate;

public class LeechEntity extends Animal implements GeoEntity{
    private int fleeAfterStealTime = 0;
    private int attachTime = 0;
    private int dismountCooldown = 0;
    public int passengerIndex = 0;

    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    public static final Predicate<LivingEntity> PREY_SELECTOR = (p_30437_) -> {
        EntityType<?> entitytype = p_30437_.getType();
        return entitytype == EntityType.SHEEP || entitytype == EntityType.COW || entitytype == EntityType.HORSE || entitytype == EntityType.SALMON || entitytype == ModEntityCreator.CHAMELEON.get() || entitytype == ModEntityCreator.CAPYBARA.get();
    };

    public LeechEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }


    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.15f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new FlyTowardsTarget(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Zombie.class, false));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    // ANIMATIONS //

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SILVERFISH_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.SILVERFISH_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SILVERFISH_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }


    private PlayState predicate(software.bernie.geckolib.core.animation.AnimationState animationState) {
        if(animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if(this.isInWaterOrBubble()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("swim", Animation.LoopType.LOOP));
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

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob p_146744_) {
        return null;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public void rideTick() {
        Entity entity = this.getVehicle();
        if (this.isPassenger() && !entity.isAlive()) {
            this.stopRiding();
        } else {
            this.setDeltaMovement(0, 0, 0);
            this.tick();
            if (this.isPassenger()) {
                attachTime++;
                Entity mount = this.getVehicle();
                if (mount instanceof LivingEntity) {
                    passengerIndex = mount.getPassengers().indexOf(this);
                    this.yBodyRot = ((LivingEntity) mount).yBodyRot * -2;
                    this.setYRot( ((LivingEntity) mount).getYRot() * -2);
                    this.yHeadRot = ((LivingEntity) mount).yHeadRot * -2;
                    this.yRotO = ((LivingEntity) mount).yHeadRot * -2;
                    this.setDeltaMovement(mount.getDeltaMovement());
                    float radius = mount.getBbWidth();
                    float angle = (0.01745329251F * (((LivingEntity) mount).yBodyRot + passengerIndex * 90F));
                    double extraX = radius * Mth.sin((float) (Math.PI + angle));
                    double extraZ = radius * Mth.cos(angle);
                    this.setPos(mount.getX() + extraX, Math.max(mount.getY() + mount.getEyeHeight() * 0.25F, mount.getY()), mount.getZ() + extraZ);
                    if (!mount.isAlive()) {
                        this.removeVehicle();
                    }
                    if (!level().isClientSide && attachTime > 15) {
                        LivingEntity target = (LivingEntity) mount;
                        float dmg = 1F;
                        if (target.getHealth() > target.getMaxHealth() * 0.2F) {
                            dmg = 1F;
                        }
                        if ((target.getHealth() < 1.5D || mount.hurt(this.level().damageSources().mobAttack(this), dmg)) && mount instanceof LivingEntity) {
                            if(target.isFallFlying()){
                                this.hurt(this.level().damageSources().generic(), 100);
                            }else{
                                this.doHurtTarget(target);
                                this.setPos(mount.getX() + extraX, Math.max(mount.getY() + mount.getEyeHeight() * 0.25F, mount.getY()), mount.getZ() + extraZ);
                                this.heal(5);
                                this.setLastHurtMob(null);
                                this.setLastHurtByMob(null);
                                this.goalSelector.getRunningGoals().forEach(Goal::stop);
                                this.targetSelector.getRunningGoals().forEach(Goal::stop);
                            }
                        }
                        if (((LivingEntity) mount).getHealth() <= 0 || this.fleeAfterStealTime > 0) {
                            this.removeVehicle();
                            this.setTarget(null);
                            dismountCooldown = 100;
                        }
                    }
                }
            }
        }

    }

    public void tick() {
        super.tick();
        if (dismountCooldown > 0) {
            dismountCooldown--;
        }
        if (!level().isClientSide) {
            if (!this.isPassenger() && attachTime != 0) {
                attachTime = 0;
            }
        }
        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
    }

    @Override
    public boolean canRiderInteract() {
        return true;
    }

    public class FlyTowardsTarget extends Goal {
        private final LeechEntity parentEntity;

        public FlyTowardsTarget(LeechEntity phage) {
            this.parentEntity = phage;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {

            return !parentEntity.isPassenger() && parentEntity.getTarget() != null && !isBittenByPhage(parentEntity.getTarget()) && parentEntity.fleeAfterStealTime == 0;
        }

        public boolean canContinueToUse() {
            return parentEntity.getTarget() != null && !isBittenByPhage(parentEntity.getTarget()) && !parentEntity.horizontalCollision && !parentEntity.isPassenger() && parentEntity.getMoveControl().hasWanted() && parentEntity.fleeAfterStealTime == 0;
        }

        public boolean isBittenByPhage(Entity entity) {
            int phageCount = 0;
            for (Entity e : entity.getPassengers()) {
                if (e instanceof LeechEntity) {
                    phageCount++;
                }
            }
            return phageCount > 3;
        }

        public void stop() {
        }

        public void tick() {
            if (parentEntity.getTarget() != null) {
                float width =  parentEntity.getTarget().getBbWidth() + parentEntity.getBbWidth() + 2;
                boolean isWithinReach = parentEntity.distanceToSqr(parentEntity.getTarget()) < width * width;
                if (isWithinReach) {
                    this.parentEntity.getMoveControl().setWantedPosition(parentEntity.getTarget().getX(), parentEntity.getTarget().getY(), parentEntity.getTarget().getZ(), isWithinReach ? 1.6D : 1.0D);
                } else {
                    this.parentEntity.getNavigation().moveTo(parentEntity.getTarget().getX(), parentEntity.getTarget().getY(), parentEntity.getTarget().getZ(), 1.2D);
                }
                if (parentEntity.getTarget().getY() > this.parentEntity.getY() + 1.2F) {
                }
                if (parentEntity.dismountCooldown == 0 && parentEntity.getBoundingBox().inflate(0.3, 0.3, 0.3).intersects(parentEntity.getTarget().getBoundingBox()) && !isBittenByPhage(parentEntity.getTarget())) {
                    parentEntity.startRiding(parentEntity.getTarget(), true);
                    parentEntity.setPos(parentEntity.getTarget().getPosition(1f));
                }
            }
        }
    }
}
