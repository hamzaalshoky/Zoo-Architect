package net.itshamza.za.entity.custom;

import net.itshamza.za.entity.ModEntityCreator;
import net.itshamza.za.entity.custom.ai.AttackPounce;
import net.itshamza.za.entity.custom.ai.StealthAttackableTargetGoal;
import net.itshamza.za.entity.custom.variant.JaguarVariant;
import net.itshamza.za.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;

public class JaguarEntity extends Animal implements GeoEntity {

    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
            SynchedEntityData.defineId(JaguarEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(JaguarEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> STEALTH_MODE = SynchedEntityData.defineId(JaguarEntity.class, EntityDataSerializers.BOOLEAN);
    public float prevStealthProgress;
    public float stealthProgress;
    private boolean hasSpedUp = false;
    private static final EntityDataAccessor<Boolean> BIPEDAL = SynchedEntityData.defineId(JaguarEntity.class, EntityDataSerializers.BOOLEAN);
    public float bipedProgress;
    public float prevBipedProgress;
    private static final EntityDataAccessor<Boolean> TACKLING = SynchedEntityData.defineId(JaguarEntity.class, EntityDataSerializers.BOOLEAN);
    public float tackleProgress;
    public float prevTackleProgress;
    public static final int RARE_VARIANT_CHANCE = 1200;

    public JaguarEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }


    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AttackPounce(this));
        this.goalSelector.addGoal(2, new AIMelee());
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new StealthAttackableTargetGoal(this, CapybaraEntity.class, true,  this));
        this.targetSelector.addGoal(1, new StealthAttackableTargetGoal(this, Pig.class, true,  this));
        this.targetSelector.addGoal(1, new StealthAttackableTargetGoal(this, Sheep.class, true,  this));
        this.targetSelector.addGoal(1, new StealthAttackableTargetGoal(this, Cow.class, true,  this));
        this.targetSelector.addGoal(1, new StealthAttackableTargetGoal(this, FlamingoEntity.class, true,  this));
        this.targetSelector.addGoal(1, new StealthAttackableTargetGoal(this, Player.class, true,  this));
        this.targetSelector.addGoal(1, new StealthAttackableTargetGoal(this, ChameleonEntity.class, true,  this));
        this.targetSelector.addGoal(1, new StealthAttackableTargetGoal(this, Monster.class, true,  this));
        this.targetSelector.addGoal(1, new StealthAttackableTargetGoal(this, GoldenTamarinEntity.class, true,  this));
        this.targetSelector.addGoal(1, new StealthAttackableTargetGoal(this, MouseEntity.class, true,  this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 15.0F));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 15.0F));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, (double)1.2F, true));
    }

    // ANIMATIONS //

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return isStealth() ? super.getAmbientSound() : ModSounds.JAGUAR_AMBIENT.get();
    }

    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (p_21372_ instanceof LivingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity)p_21372_).getMobType());
            f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            p_21372_.setSecondsOnFire(i * 4);
        }

        boolean flag = p_21372_.hurt(this.level().damageSources().mobAttack(this), f);
        if (flag) {
            if (f1 > 0.0F && p_21372_ instanceof LivingEntity) {
                ((LivingEntity)p_21372_).knockback((double)(f1 * 0.5F), (double)Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), (double)(-Mth.cos(this.getYRot() * ((float)Math.PI / 180F))));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 0.3D, 0.6D));
            }

            this.doEnchantDamageEffects(this, p_21372_);
            this.setLastHurtMob(p_21372_);
        }

        return flag;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.JAGUAR_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.JAGUAR_DEATH.get();
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

    public boolean isStealth() {
        return this.entityData.get(STEALTH_MODE).booleanValue();
    }

    public void setStealth(boolean bar) {
        this.entityData.set(STEALTH_MODE, Boolean.valueOf(bar));
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
        if(this.isTackling()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("pounce", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if(this.isFallFlying()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("pounce", Animation.LoopType.LOOP));
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

    public static class JaguarGroupData extends AgeableMob.AgeableMobGroupData {
        public final JaguarVariant[] types;

        public JaguarGroupData(JaguarVariant... p_149204_) {
            super(false);
            this.types = p_149204_;
        }

        public JaguarVariant getVariant(Random p_149206_) {
            return this.types[p_149206_.nextInt(this.types.length)];
        }
    }


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
        this.entityData.define(RUNNING, Boolean.valueOf(false));
        this.entityData.define(STEALTH_MODE, Boolean.valueOf(false));
        this.entityData.define(BIPEDAL, Boolean.valueOf(false));
        this.entityData.define(TACKLING, Boolean.valueOf(false));
    }

    public void addAdditionalSaveData(CompoundTag p_29495_) {
        super.addAdditionalSaveData(p_29495_);
        p_29495_.putInt("Variant", this.getTypeVariant());
        p_29495_.putBoolean("Bipedal", this.isBipedal());
    }

    public boolean isRunning() {
        return this.entityData.get(RUNNING).booleanValue();
    }

    public void setRunning(boolean running) {
        this.entityData.set(RUNNING, Boolean.valueOf(running));
    }

    public void readAdditionalSaveData(CompoundTag p_29478_) {
        super.readAdditionalSaveData(p_29478_);
        this.entityData.set(DATA_ID_TYPE_VARIANT, p_29478_.getInt("Variant"));
        this.setBipedal(p_29478_.getBoolean("Bipedal"));
    }

    public double getVisibilityPercent(@javax.annotation.Nullable Entity lookingEntity) {
        if (this.isStealth()) {
            return 0.3D;
        }
        return super.getVisibilityPercent(lookingEntity);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob p_146744_) {
        JaguarEntity jag = ModEntityCreator.JAGUAR.get().create(serverLevel);
        return jag;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public JaguarVariant getVariant() {
        return JaguarVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(JaguarVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    private static boolean useRareVariant(Random p_149143_) {
        return p_149143_.nextInt(1200) == 0;
    }

    public void tick(){
        super.tick();
        prevStealthProgress = stealthProgress;
        this.prevBipedProgress = bipedProgress;
        this.prevTackleProgress = tackleProgress;
        if (!level().isClientSide) {
            if (isRunning() && !hasSpedUp) {
                hasSpedUp = true;
                maxUpStep = 1F;
                this.setSprinting(true);
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4F);
            }
            if (!isRunning() && hasSpedUp) {
                hasSpedUp = false;
                maxUpStep = 0.6F;
                this.setSprinting(false);
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2F);
            }
            if (isStealth()) {
                maxUpStep = 0.6F;
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1F);
            }
            if (!isStealth()) {
                maxUpStep = 1F;
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3F);
            }
        }
        if (this.isTackling() && tackleProgress < 5.0F) {
            tackleProgress++;
        }
        if (!this.isTackling() && tackleProgress > 0.0F) {
            tackleProgress--;
        }
        if (isStealth() && stealthProgress < 10F) {
            stealthProgress += 0.25F;
        }
        if (!isStealth() && stealthProgress > 0F) {
            stealthProgress--;
        }
        if (this.isBipedal() && bipedProgress < 5.0F) {
            bipedProgress++;
        }
        if (!this.isBipedal() && bipedProgress > 0.0F) {
            bipedProgress--;
        }
    }

    public boolean isBipedal() {
        return this.entityData.get(BIPEDAL).booleanValue();
    }

    public boolean isTackling() {
        return this.entityData.get(TACKLING).booleanValue();
    }

    public void setTackling(boolean bar) {
        this.entityData.set(TACKLING, Boolean.valueOf(bar));
    }

    public void setBipedal(boolean bar) {
        this.entityData.set(BIPEDAL, Boolean.valueOf(bar));
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.getItem() == Items.BEEF;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == this.level().damageSources().fall() || super.isInvulnerableTo(source);
    }

    private class AIMelee extends Goal {
        private JaguarEntity jaguar;
        private int jumpAttemptCooldown = 0;

        public AIMelee() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            jaguar = JaguarEntity.this;
        }

        @Override
        public boolean canUse() {
            return jaguar.getTarget() != null && jaguar.getTarget().isAlive();
        }

        public void tick() {
            if (jumpAttemptCooldown > 0) {
                jumpAttemptCooldown--;
            }
            LivingEntity target = jaguar.getTarget();
            if (target != null && target.isAlive()) {
                double dist = jaguar.distanceTo(target);
                if (jaguar.getLastHurtByMob() != null && jaguar.getLastHurtByMob().isAlive() && dist < 10) {
                    jaguar.setStealth(false);
                } else {
                    if (dist > 20) {
                        jaguar.setRunning(false);
                        jaguar.setStealth(true);
                    }
                }
                if (dist <= 20) {
                    jaguar.setStealth(false);
                    jaguar.setRunning(true);
                }
                if (dist < 12 && !jaguar.isFallFlying() && jumpAttemptCooldown == 0) {
                    jumpAttemptCooldown = 70;
                }
                if (dist < 4 + target.getBbWidth()){
                    target.hurt(level().damageSources().mobAttack(JaguarEntity.this), 7 + jaguar.getRandom().nextInt(5));
                }
                jaguar.getNavigation().stop();
                Vec3 vec = target.position().subtract(jaguar.position());
                jaguar.setYRot(-((float) Mth.atan2(vec.x, vec.z)) * (180F / (float) Math.PI));
                jaguar.yBodyRot = jaguar.getYRot();
                if (!jaguar.isFallFlying()) {
                    Vec3 vector3d1 = new Vec3(target.getX() - this.jaguar.getX(), 0.0D, target.getZ() - this.jaguar.getZ());
                    if (vector3d1.lengthSqr() > 1.0E-7D) {
                        vector3d1 = vector3d1.normalize().scale(Math.min(dist, 15) * 0.2F);
                    }
                    this.jaguar.setDeltaMovement(vector3d1.x, vector3d1.y + 0.3F + 0.1F * Mth.clamp(target.getEyeY() - this.jaguar.getY(), 0, 2), vector3d1.z);
                }
                if (dist < target.getBbWidth() + 3) {
                    target.hurt(level().damageSources().mobAttack(JaguarEntity.this), 2);
                    jaguar.setRunning(false);
                    jaguar.setStealth(false);
                }
            } else {
                if(target != null){
                    jaguar.getNavigation().moveTo(target, jaguar.isStealth() ? 0.75F : 1.0F);
                }
            }
        }


        public void stop() {
            jaguar.setStealth(false);
            jaguar.setRunning(false);
        }
    }

    class AttackPlayerGoal extends NearestAttackableTargetGoal<Player> {

        public AttackPlayerGoal() {
            super(JaguarEntity.this, Player.class, 100, false, true, (Predicate<LivingEntity>)null);
        }

        public boolean canUse() {
            if (JaguarEntity.this.isBaby()) {
                return false;
            } else {
                return super.canUse();
            }
        }

        protected double getFollowDistance() {
            return 4.0D;
        }
    }

    @Override
    protected float getJumpPower() {
        return 0.7F * this.getBlockJumpFactor();
    }

    @Override
    protected void jumpFromGround() {
        double d0 = (double) this.getJumpPower() + this.getJumpBoostPower();
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, d0, vec3.z);
        float f = this.getYRot() * ((float) Math.PI / 180F);
        this.setDeltaMovement(this.getDeltaMovement().add(-Mth.sin(f) * 0.2F, 0, Mth.cos(f) * 0.2F));
        this.hasImpulse = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(this);
    }

    public void frostJump() {
        jumpFromGround();
    }
}
