package net.itshamza.za.entity.custom;

import net.itshamza.za.block.ModBlocks;
import net.itshamza.za.block.custom.SteppeEagleEggBlock;
import net.itshamza.za.damagesource.ModDamageSources;
import net.itshamza.za.entity.ModEntityCreator;
import net.itshamza.za.entity.custom.ai.FlyingWanderGoal;
import net.itshamza.za.entity.custom.ai.VultureAIStealFromPlayers;
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
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Random;
import java.util.function.Predicate;

public class VultureEntity extends Animal implements GeoEntity, FlyingAnimal {

    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    public static final Predicate<LivingEntity> PREY_SELECTOR = (p_30437_) -> {
        EntityType<?> entitytype = p_30437_.getType();
        return entitytype == EntityType.ZOMBIE || entitytype == EntityType.HUSK || entitytype == EntityType.DROWNED || entitytype == EntityType.ZOMBIE || entitytype == EntityType.SKELETON || entitytype == EntityType.WITHER_SKELETON || entitytype == EntityType.WITHER;
    };
    public int stealCooldown = random.nextInt(500);
    public boolean aiItemFlag = false;

    public VultureEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
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
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.1f)
                .add(Attributes.FLYING_SPEED, 0.3f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new VultureEntity.SteppeEagleWanderGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new VultureAIStealFromPlayers(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.0D, Ingredient.of(Items.ROTTEN_FLESH, Items.BEEF, Items.MUTTON, Items.CHICKEN), false){
            public boolean canUse(){
                return !VultureEntity.this.aiItemFlag && super.canUse();
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == this.level().damageSources().cactus() || super.isInvulnerableTo(source);
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
        return this.isFallFlying();
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            boolean prev = super.hurt(source, amount);
            if (prev) {
                if (!this.getMainHandItem().isEmpty()) {
                    this.spawnAtLocation(this.getMainHandItem());
                    this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                    stealCooldown = 1500 + random.nextInt(1500);
                }
            }
            return prev;
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

    private PlayState predicate(software.bernie.geckolib.core.animation.AnimationState animationState) {
        if(this.isFlying()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("fly", Animation.LoopType.LOOP));
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


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob p_146744_) {
        return null;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();

    }

    public void addAdditionalSaveData(CompoundTag p_30176_) {
        super.addAdditionalSaveData(p_30176_);
        p_30176_.putInt("StealCooldown", this.stealCooldown);
    }

    public void readAdditionalSaveData(CompoundTag p_30162_) {
        super.readAdditionalSaveData(p_30162_);
        this.stealCooldown = p_30162_.getInt("StealCooldown");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller",
                0, this::predicate));
        controllers.add(new AnimationController(this, "attackController",
                0, this::attackPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    static class SteppeEaglePathNavigation extends FlyingPathNavigation {
        public SteppeEaglePathNavigation(Mob mob, Level level) {
            super(mob, level);
        }

        @Override
        public boolean isStableDestination(BlockPos pos) {
            return super.isStableDestination(pos) && this.mob.level().getBlockState(pos).is(Blocks.CACTUS);
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

    public void tick(){
        super.tick();
        if (stealCooldown > 0) {
            stealCooldown--;
        }
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        InteractionResult type = super.mobInteract(player, hand);
        if (!this.getMainHandItem().isEmpty() && type != InteractionResult.SUCCESS) {
            this.spawnAtLocation(this.getMainHandItem().copy());
            this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            stealCooldown = 1500 + random.nextInt(1500);
            return InteractionResult.SUCCESS;
        } else {
            return type;
        }
    }

    public Vec3 getBlockInViewAway(Vec3 fleePos, float radiusAdd) {
        float radius = 5 + radiusAdd + this.getRandom().nextInt(5);
        float neg = this.getRandom().nextBoolean() ? 1 : -1;
        float renderYawOffset = this.yBodyRot;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
        double extraX = radius * Mth.sin((float) (Math.PI + angle));
        double extraZ = radius * Mth.cos(angle);
        BlockPos radialPos = new BlockPos((int) (fleePos.x() + extraX), 0, (int) (fleePos.z() + extraZ));
        BlockPos ground = getSeagullGround(radialPos);
        int distFromGround = (int) this.getY() - ground.getY();
        int flightHeight = 8 + this.getRandom().nextInt(4);
        BlockPos newPos = ground.above(distFromGround > 3 ? flightHeight : this.getRandom().nextInt(4) + 8);
        if (!this.isTargetBlocked(Vec3.atCenterOf(newPos)) && this.distanceToSqr(Vec3.atCenterOf(newPos)) > 1) {
            return Vec3.atCenterOf(newPos);
        }
        return null;
    }

    public BlockPos getSeagullGround(BlockPos in) {
        BlockPos position = new BlockPos(in.getX(), (int) this.getY(), in.getZ());
        while (position.getY() < 320 && !level().getFluidState(position).isEmpty()) {
            position = position.above();
        }
        while (position.getY() > -64 && level().getFluidState(position).isEmpty()) {
            position = position.below();
        }
        return position;
    }

    public boolean isTargetBlocked(Vec3 target) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());

        return this.level().clip(new ClipContext(Vector3d, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.MISS;
    }
}
