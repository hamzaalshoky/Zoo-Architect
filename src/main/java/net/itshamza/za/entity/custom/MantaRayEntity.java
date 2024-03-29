package net.itshamza.za.entity.custom;

import net.itshamza.za.effects.ModEffects;
import net.itshamza.za.entity.custom.ai.ManateeSleep;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
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
import net.minecraft.world.entity.EntityType;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class MantaRayEntity extends WaterAnimal implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private static final EntityDataAccessor<Integer> MOISTNESS_LEVEL = SynchedEntityData.defineId(ManateeEntity.class, EntityDataSerializers.INT);
    static final TargetingConditions SWIM_WITH_PLAYER_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
    private static final EntityDataAccessor<Integer> FEEDING_TIME = SynchedEntityData.defineId(ManateeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<BlockPos>> FEEDING_POS = SynchedEntityData.defineId(ManateeEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    public static final int TOTAL_AIR_SUPPLY = 4800;
    private static final int TOTAL_MOISTNESS_LEVEL = 2400;
    public float sleepProgress;
    public float prevSleepProgress;
    public float prevFeedProgress;
    public float feedProgress;

    public MantaRayEntity(EntityType<? extends WaterAnimal> p_30341_, Level p_30342_) {
        super(p_30341_, p_30342_);
        this.moveControl = new SmoothSwimmingMoveControl(this, 2, 2, 0.1F, 0.15F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 0);
    }


    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(2, new MantaRayEntity.MantaRayEntitySwimWithPlayerGoal(this, 4.0D));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    // ANIMATIONS //

    protected float getSoundVolume() {
        return 0.2F;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isInWaterOrBubble()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
            return PlayState.CONTINUE;
        }

        if (this.isInWaterOrBubble() && event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("swim", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Nullable

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @javax.annotation.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28332_, DifficultyInstance p_28333_, MobSpawnType p_28334_, @javax.annotation.Nullable SpawnGroupData p_28335_, @javax.annotation.Nullable CompoundTag p_28336_) {
        this.setAirSupply(this.getMaxAirSupply());
        this.setXRot(0.0F);
        return super.finalizeSpawn(p_28332_, p_28333_, p_28334_, p_28335_, p_28336_);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    protected void handleAirSupply(int p_28326_) {

    }

    public int getMoistnessLevel() {
        return this.entityData.get(MOISTNESS_LEVEL);
    }

    public void setMoisntessLevel(int p_28344_) {
        this.entityData.set(MOISTNESS_LEVEL, p_28344_);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MOISTNESS_LEVEL, 2400);
        this.entityData.define(FEEDING_TIME, 0);
        this.entityData.define(FEEDING_POS, Optional.empty());
    }

    public void addAdditionalSaveData(CompoundTag p_28364_) {
        super.addAdditionalSaveData(p_28364_);
        p_28364_.putInt("Moistness", this.getMoistnessLevel());
    }

    public void readAdditionalSaveData(CompoundTag p_28340_) {
        int i = p_28340_.getInt("TreasurePosX");
        int j = p_28340_.getInt("TreasurePosY");
        int k = p_28340_.getInt("TreasurePosZ");
        super.readAdditionalSaveData(p_28340_);
        this.setMoisntessLevel(p_28340_.getInt("Moistness"));
    }

    protected PathNavigation createNavigation(Level p_28362_) {
        return new WaterBoundPathNavigation(this, p_28362_);
    }

    public int getMaxAirSupply() {
        return 4800;
    }

    protected int increaseAirSupply(int p_28389_) {
        return this.getMaxAirSupply();
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

    public void tick() {
        super.tick();
        if (this.isNoAi()) {
            this.setAirSupply(this.getMaxAirSupply());
        } else {
            if (this.isInWaterRainOrBubble()) {
                this.setMoisntessLevel(2400);
            } else {
                this.setMoisntessLevel(this.getMoistnessLevel() - 1);
                if (this.getMoistnessLevel() <= 0) {
                    this.hurt(DamageSource.DRY_OUT, 1.0F);
                }

                if (this.onGround) {
                    this.setDeltaMovement(this.getDeltaMovement().add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5D, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F)));
                    this.setYRot(this.random.nextFloat() * 360.0F);
                    this.onGround = false;
                    this.hasImpulse = true;
                }
            }

            if (this.level.isClientSide && this.isInWater() && this.getDeltaMovement().lengthSqr() > 0.03D) {
                Vec3 vec3 = this.getViewVector(0.0F);
                float f = Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * 0.3F;
                float f1 = Mth.sin(this.getYRot() * ((float)Math.PI / 180F)) * 0.3F;
                float f2 = 1.2F - this.random.nextFloat() * 0.7F;

                for(int i = 0; i < 2; ++i) {
                    this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vec3.x * (double)f2 + (double)f, this.getY() - vec3.y, this.getZ() - vec3.z * (double)f2 + (double)f1, 0.0D, 0.0D, 0.0D);
                    this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vec3.x * (double)f2 - (double)f, this.getY() - vec3.y, this.getZ() - vec3.z * (double)f2 - (double)f1, 0.0D, 0.0D, 0.0D);
                }
            }

        }
        if (this.getFeedingTime() > 0 && feedProgress < 5F) {
            feedProgress++;
        }
        if (this.getFeedingTime() <= 0 && feedProgress > 0F) {
            feedProgress--;
        }
        BlockPos feedingPos = this.entityData.get(FEEDING_POS).orElse(null);
        if(feedingPos == null){
            float f2 = (float) -((float) this.getDeltaMovement().y * 2.2F * (double) (180F / (float) Math.PI));
            this.setXRot(f2);
        }else if(this.getFeedingTime() > 0){
            Vec3 face = Vec3.atCenterOf(feedingPos).subtract(this.position());
            double d0 = face.horizontalDistance();
            this.setXRot((float)(-Mth.atan2(face.y, d0) * (double)(180F / (float)Math.PI)));
            this.setYRot(((float) Mth.atan2(face.z, face.x)) * (180F / (float) Math.PI) - 90F);
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.getYRot();
            BlockState state = level.getBlockState(feedingPos);
            if(random.nextInt(2) == 0 && !state.isAir()){
                Vec3 mouth = new Vec3(0, this.getBbHeight() * 0.5F, 1.4F);
                for (int i = 0; i < 4 + random.nextInt(2); i++) {
                    double motX = this.random.nextGaussian() * 0.02D;
                    double motY = 0.1F + random.nextFloat() * 0.2F;
                    double motZ = this.random.nextGaussian() * 0.02D;
                    level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), this.getX() + mouth.x, this.getY() + mouth.y, this.getZ() + mouth.z, motX, motY, motZ);
                }
            }
        }

        // Check for nearby creatures
        List<Entity> nearbyEntities = this.level.getEntities(this, this.getBoundingBox().inflate(8.0D), entity -> entity instanceof LivingEntity && !(entity instanceof Player));

        // Apply poison effect to nearby creatures
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Monster) {
                ((Monster) entity).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 1));
            }
        }
    }

    public void handleEntityEvent(byte p_28324_) {
        if (p_28324_ == 38) {
            this.addParticlesAroundSelf(ParticleTypes.HAPPY_VILLAGER);
        } else {
            super.handleEntityEvent(p_28324_);
        }

    }

    private void addParticlesAroundSelf(ParticleOptions p_28338_) {
        for(int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.01D;
            double d1 = this.random.nextGaussian() * 0.01D;
            double d2 = this.random.nextGaussian() * 0.01D;
            this.level.addParticle(p_28338_, this.getRandomX(1.0D), this.getRandomY() + 0.2D, this.getRandomZ(1.0D), d0, d1, d2);
        }

    }
    protected SoundEvent getHurtSound(DamageSource p_28374_) {
        return null;
    }

    @javax.annotation.Nullable
    protected SoundEvent getDeathSound() {
        return null;
    }

    @javax.annotation.Nullable
    protected SoundEvent getAmbientSound() {
        return null;
    }

    public int getFeedingTime() {
        return this.entityData.get(FEEDING_TIME);
    }

    public void setFeedingTime(int feedingTime) {
        this.entityData.set(FEEDING_TIME, feedingTime);
    }

    protected SoundEvent getSwimSplashSound() {
        return SoundEvents.DOLPHIN_SPLASH;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.DOLPHIN_SWIM;
    }

    public void travel(Vec3 p_28383_) {
        if (this.isEffectiveAi() && this.isInWaterOrBubble()) {
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

    static class MantaRayEntitySwimWithPlayerGoal extends Goal {
        private final MantaRayEntity ManateeEntity;
        private final double speedModifier;
        @javax.annotation.Nullable
        private Player player;

        MantaRayEntitySwimWithPlayerGoal(MantaRayEntity p_28413_, double p_28414_) {
            this.ManateeEntity = p_28413_;
            this.speedModifier = p_28414_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            this.player = this.ManateeEntity.level.getNearestPlayer(ManateeEntity.SWIM_WITH_PLAYER_TARGETING, this.ManateeEntity);
            if (this.player == null) {
                return false;
            } else {
                return this.player.isSwimming() && this.ManateeEntity.getTarget() != this.player;
            }
        }

        public boolean canContinueToUse() {
            return this.player != null && this.player.isSwimming() && this.ManateeEntity.distanceToSqr(this.player) < 256.0D;
        }

        public void stop() {
            this.player = null;
            this.ManateeEntity.getNavigation().stop();
        }

        public void tick() {
            this.ManateeEntity.getLookControl().setLookAt(this.player, (float)(this.ManateeEntity.getMaxHeadYRot() + 20), (float)this.ManateeEntity.getMaxHeadXRot());
            if (this.ManateeEntity.distanceToSqr(this.player) < 6.25D) {
                this.ManateeEntity.getNavigation().stop();
            } else {
                this.ManateeEntity.getNavigation().moveTo(this.player, this.speedModifier);
            }

        }
    }

    private boolean canSeeBlock(BlockPos destinationBlock) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        Vec3 blockVec = net.minecraft.world.phys.Vec3.atCenterOf(destinationBlock);
        BlockHitResult result = this.level.clip(new ClipContext(Vector3d, blockVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        return result.getBlockPos().equals(destinationBlock);
    }

}