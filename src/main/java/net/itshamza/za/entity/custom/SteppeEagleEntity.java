package net.itshamza.za.entity.custom;

import net.itshamza.za.block.ModBlocks;
import net.itshamza.za.block.custom.SteppeEagleEggBlock;
import net.itshamza.za.damagesource.ModDamageSources;
import net.itshamza.za.entity.ModEntityCreator;
import net.itshamza.za.entity.custom.ai.FlyingWanderGoal;
import net.itshamza.za.item.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;
import java.util.function.Predicate;

public class SteppeEagleEntity extends Animal implements IAnimatable, FlyingAnimal {

    private AnimationFactory factory = new AnimationFactory(this);
    public static final Predicate<LivingEntity> PREY_SELECTOR = (p_30437_) -> {
        EntityType<?> entitytype = p_30437_.getType();
        return entitytype == EntityType.SHEEP || entitytype == EntityType.SALMON || entitytype == ModEntityCreator.MOUSE.get() || entitytype == ModEntityCreator.FENNEC_FOX.get() || entitytype == ModEntityCreator.MAMBA.get() || entitytype == ModEntityCreator.RATTLESNAKE.get()|| entitytype == ModEntityCreator.VIPER.get();
    };
    private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(SteppeEagleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LAYING_EGG = SynchedEntityData.defineId(SteppeEagleEntity.class, EntityDataSerializers.BOOLEAN);
    int layEggCounter;

    public SteppeEagleEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.1f)
                .add(Attributes.FLYING_SPEED, 0.3f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new SteppeEagleWanderGoal(this){
            public boolean canUse() { return !SteppeEagleEntity.this.isBaby() && super.canUse(); }
        });
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
        this.goalSelector.addGoal(1, new SteppeEagleEntity.SteppeEagleEntityBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new SteppeEagleEntity.SteppeEagleEntityLayEggGoal(this, 1.0D));
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(ModItems.FRILLED_LIZARD_MEAT.get());
    }

    // ANIMATIONS //

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.PARROT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.PARROT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

    @Override
    public boolean isFlying() {
        return !this.isOnGround();
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    public boolean canFallInLove() {
        return super.canFallInLove() && !this.hasEgg();
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    public boolean hasEgg() {
        return this.entityData.get(HAS_EGG);
    }

    void setHasEgg(boolean p_30235_) {
        this.entityData.set(HAS_EGG, p_30235_);
    }

    public boolean isLayingEgg() {
        return this.entityData.get(LAYING_EGG);
    }

    void setLayingEgg(boolean p_30237_) {
        this.layEggCounter = p_30237_ ? 1 : 0;
        this.entityData.set(LAYING_EGG, p_30237_);
    }


    public void aiStep() {
        super.aiStep();
        if (this.isAlive() && this.isLayingEgg() && this.layEggCounter >= 1 && this.layEggCounter % 5 == 0) {
            BlockPos blockpos = this.blockPosition();
            if (SteppeEagleEggBlock.onSand(this.level, blockpos)) {
                this.level.levelEvent(2001, blockpos, Block.getId(this.level.getBlockState(blockpos.below())));
            }
        }

    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        SteppeEaglePathNavigation navigation = new SteppeEaglePathNavigation(this, pLevel);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isFlying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("fly", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob p_146744_) {
        return ModEntityCreator.STEPPE_EAGLE.get().create(serverLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_EGG, false);
        this.entityData.define(LAYING_EGG, false);
    }

    public void addAdditionalSaveData(CompoundTag p_30176_) {
        super.addAdditionalSaveData(p_30176_);
        p_30176_.putBoolean("HasEgg", this.hasEgg());
    }

    public void readAdditionalSaveData(CompoundTag p_30162_) {
        super.readAdditionalSaveData(p_30162_);
        this.setHasEgg(p_30162_.getBoolean("HasEgg"));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    static class SteppeEaglePathNavigation extends FlyingPathNavigation {
        public SteppeEaglePathNavigation(Mob mob, Level level) {
            super(mob, level);
        }

        @Override
        public boolean isStableDestination(BlockPos pos) {
            return super.isStableDestination(pos) && this.mob.level.getBlockState(pos).is(ModBlocks.STEPPE_EAGLE_NEST.get());
        }
    }

    static class SteppeEagleWanderGoal extends FlyingWanderGoal {
        public SteppeEagleWanderGoal(PathfinderMob mob) {
            super(mob);
        }

        @Nullable
        @Override
        protected Vec3 findPos() {
            Vec3 viewVector = mob.getViewVector(0.0F);
            return AirAndWaterRandomPos.getPos(mob, 12, 12, -1, viewVector.x, viewVector.z, Math.PI);
        }
    }
    public void killed(ServerLevel world, LivingEntity entity) {
        if((entity instanceof Animal || entity.getMobType() == MobType.UNDEAD)){
            entity.spawnAtLocation(new ItemStack(Items.BONE));
            entity.spawnAtLocation(new ItemStack(Items.BONE));
            entity.spawnAtLocation(new ItemStack(Items.BONE));
        }
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

        boolean flag = p_21372_.hurt(DamageSource.mobAttack(this), f);
        if (flag) {
            if (f1 > 0.0F && p_21372_ instanceof LivingEntity) {
                ((LivingEntity)p_21372_).knockback((double)(f1 * 0.5F), (double) Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), (double)(-Mth.cos(this.getYRot() * ((float)Math.PI / 180F))));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 0.3D, 0.6D));
                    if(Math.random() < 0.5){
                        this.spawnAtLocation(Items.BONE);
                        this.spawnAtLocation(Items.BONE);
                    }else if(Math.random() < 0.25){
                        this.spawnAtLocation(Items.BONE);
                    }else if(Math.random() < 0.25){
                        return flag;
                    }
            }
            if(Math.random() < 0.5){
                this.spawnAtLocation(Items.BONE);
                this.spawnAtLocation(Items.BONE);
            }else if(Math.random() < 0.25){
                this.spawnAtLocation(Items.BONE);
            }else if(Math.random() < 0.25){
                return flag;
            }
            this.doEnchantDamageEffects(this, p_21372_);
            this.setLastHurtMob(p_21372_);
        }

        return flag;
    }

    static class SteppeEagleEntityBreedGoal extends BreedGoal {
        private final SteppeEagleEntity turtle;

        SteppeEagleEntityBreedGoal(SteppeEagleEntity p_30244_, double p_30245_) {
            super(p_30244_, p_30245_);
            this.turtle = p_30244_;
        }

        public boolean canUse() {
            return super.canUse() && !this.turtle.hasEgg();
        }

        protected void breed() {
            ServerPlayer serverplayer = this.animal.getLoveCause();
            if (serverplayer == null && this.partner.getLoveCause() != null) {
                serverplayer = this.partner.getLoveCause();
            }

            if (serverplayer != null) {
                serverplayer.awardStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(serverplayer, this.animal, this.partner, (AgeableMob)null);
            }

            this.turtle.setHasEgg(true);
            this.animal.resetLove();
            this.partner.resetLove();
            Random random = (Random) this.animal.getRandom();
            if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
            }

        }
    }

    static class SteppeEagleEntityLayEggGoal extends MoveToBlockGoal {
        private final SteppeEagleEntity turtle;

        SteppeEagleEntityLayEggGoal(SteppeEagleEntity p_30276_, double p_30277_) {
            super(p_30276_, p_30277_, 16);
            this.turtle = p_30276_;
        }

        public boolean canUse() {
            return this.turtle.hasEgg();
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.turtle.hasEgg();
        }

        public void tick() {
            super.tick();
            BlockPos blockpos = this.turtle.blockPosition();
            if (this.isReachedTarget()) {
                if (this.turtle.layEggCounter < 1) {
                    this.turtle.setLayingEgg(true);
                } else if (this.turtle.layEggCounter > this.adjustedTickDelay(200)) {
                    Level level = this.turtle.level;
                    level.playSound((Player)null, blockpos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level.random.nextFloat() * 0.2F);
                    level.setBlock(this.blockPos.above(), ModBlocks.STEPPE_EAGLE_EGG.get().defaultBlockState().setValue(SteppeEagleEggBlock.EGGS, Integer.valueOf(this.turtle.random.nextInt(4) + 1)), 3);
                    this.turtle.setHasEgg(false);
                    this.turtle.setLayingEgg(false);
                    this.turtle.setInLoveTime(600);
                }

                if (this.turtle.isLayingEgg()) {
                    ++this.turtle.layEggCounter;
                }
            }

        }

        protected boolean isValidTarget(LevelReader p_30280_, BlockPos p_30281_) {
            return SteppeEagleEntity.isSand(p_30280_, p_30281_);
        }
    }

    public static boolean isSand(BlockGetter p_57801_, BlockPos p_57802_) {
        return p_57801_.getBlockState(p_57802_).is(Tags.Blocks.SAND);
    }
}
