package net.itshamza.za.entity.custom;

import net.itshamza.za.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.Random;


public class SquirrelEntity extends Animal implements GeoEntity{

    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    private static final Item POISONOUS_FOOD = ModItems.RAT_POISON.get();
    private static final EntityDataAccessor<Integer> SAPLING_TIME = SynchedEntityData.defineId(SquirrelEntity.class, EntityDataSerializers.INT);

    public SquirrelEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }
    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.35f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0D, 1));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new SaplingPlantingGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Wolf.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Fox.class, 8.0F, 2.2D, 2.2D));
    }

    // ANIMATIONS //

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.BAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.DOLPHIN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
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

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }



    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    public boolean isHamza() {
        String s = ChatFormatting.stripFormatting(this.getName().getString());
        return s != null && (s.toLowerCase().contains("hamza"));
    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.is(POISONOUS_FOOD)) {
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            this.addEffect(new MobEffectInstance(MobEffects.POISON, 900));
            if (pPlayer.isCreative() || !this.isInvulnerable()) {
                this.hurt(this.level().damageSources().mobAttack(pPlayer), Float.MAX_VALUE);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    public class SaplingPlantingGoal extends Goal {
        private final LivingEntity entity;
        private final Level world;
        private final Random random;
        private int timeUntilNextPlant;

        public SaplingPlantingGoal(LivingEntity entity) {
            this.entity = entity;
            this.world = entity.level();
            this.random = new Random();
            this.timeUntilNextPlant = this.random.nextInt(12) + 60; // Random time between 10 and 30 seconds
        }

        @Override
        public boolean canUse() {
            return this.timeUntilNextPlant <= 0;
        }

        @Override
        public void tick() {
            BlockPos frontBlockPos = this.entity.getOnPos().offset(this.entity.getOnPos());
            if (this.world.isEmptyBlock(frontBlockPos) && this.world.isEmptyBlock(frontBlockPos.above())) {
                ItemStack saplingStack = new ItemStack(Items.OAK_SAPLING); // Change to your desired sapling type
                if (this.entity.getMainHandItem().isEmpty()) {
                    this.entity.setItemInHand(InteractionHand.MAIN_HAND, saplingStack);
                } else if (this.entity.getOffhandItem().isEmpty()) {
                    this.entity.setItemInHand(InteractionHand.MAIN_HAND, saplingStack);
                } else {
                    // Both hands are occupied, cannot plant sapling
                    return;
                }

                Vec3 lookVec = Vec3.directionFromRotation(this.entity.getRotationVector());
                double offsetX = lookVec.x * 2.0;
                double offsetY = lookVec.y * 2.0;
                double offsetZ = lookVec.z * 2.0;

                BlockPos targetBlockPos = frontBlockPos.offset((int) offsetX, (int) offsetY, (int) offsetZ);
                this.world.setBlock(targetBlockPos, Blocks.OAK_SAPLING.defaultBlockState(), 2);
            }

            this.timeUntilNextPlant = this.random.nextInt(12) + 60; // Random time between 10 and 30 seconds
        }
    }
}
