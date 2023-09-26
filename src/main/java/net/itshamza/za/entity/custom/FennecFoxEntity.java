package net.itshamza.za.entity.custom;

import net.itshamza.za.entity.ModEntityCreator;
import net.itshamza.za.entity.custom.ai.*;
import net.itshamza.za.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
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

import java.util.Optional;
import java.util.Random;


public class FennecFoxEntity extends Animal implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(FennecFoxEntity.class, EntityDataSerializers.BOOLEAN);
    public float sleepProgress;
    public float prevSleepProgress;
    public FennecFoxEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.ATTACK_DAMAGE, 100.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F){
            public boolean canUse() { return !FennecFoxEntity.this.isSleeping() && super.canUse(); }
        });
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this){
            public boolean canUse() { return !FennecFoxEntity.this.isSleeping() && super.canUse(); }
        });
        this.goalSelector.addGoal(8, new FoxSleepGoal(this){
            public boolean canUse() { return !FennecFoxEntity.this.isInWater() && super.canUse(); }
        });
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(ModItems.FRILLED_LIZARD_MEAT.get(), ModItems.SCORPION_TAIL.get(), ModItems.COOKED_FRILLED_LIZARD_MEAT.get()), false));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Player.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Husk.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, FrilledLizardEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MouseEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Rabbit.class, false));
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

        if (this.isSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("sleep", true));
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

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.DOLPHIN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob p_146744_) {
        return ModEntityCreator.FENNEC_FOX.get().create(serverLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.getItem() == ModItems.SCORPION_TAIL.get();
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SLEEPING, Boolean.valueOf(false));
    }

    public void addAdditionalSaveData(CompoundTag p_29495_) {
        super.addAdditionalSaveData(p_29495_);
        p_29495_.putBoolean("Sleeping", isSleeping());
    }

    public void readAdditionalSaveData(CompoundTag p_29478_) {
        super.readAdditionalSaveData(p_29478_);
        this.setSleeping(p_29478_.getBoolean("Sleeping"));
    }

    public void tick(){
        super.tick();
        this.prevSleepProgress = sleepProgress;

        if (this.isSleeping() && sleepProgress < 5F) {
            sleepProgress++;
        }
        if (!this.isSleeping() && sleepProgress > 0F) {
            sleepProgress--;
        }
        if (!this.level.isClientSide && this != null) {
            if (this.level.isDay()) {
                this.setSleeping(true);
            } else if (!this.level.isDay()) {
                this.setSleeping(false);
            }
        }
    }

    public boolean isSleeping() {
        return this.entityData.get(SLEEPING).booleanValue();
    }

    public void setSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, Boolean.valueOf(sleeping));
    }

    public boolean isFoxy() {
        String s = ChatFormatting.stripFormatting(this.getName().getString());
        return s != null && (s.toLowerCase().contains("foxy") || s.toLowerCase().equals("pirate"));
    }
}
