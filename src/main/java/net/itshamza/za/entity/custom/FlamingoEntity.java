package net.itshamza.za.entity.custom;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.ModEntityCreator;
import net.itshamza.za.entity.custom.ai.DirectPathNavigator;
import net.itshamza.za.entity.custom.ai.FlightMoveController;
import net.itshamza.za.entity.custom.ai.StealthAttackableTargetGoal;
import net.itshamza.za.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.EnumSet;
import java.util.List;

public class FlamingoEntity extends Animal implements GeoEntity{

    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(FlamingoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(FlamingoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DIGGING = SynchedEntityData.defineId(FlamingoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final ResourceLocation DIGGABLES = new ResourceLocation(ZooArchitect.MOD_ID, "gameplay/digging");
    protected BlockState stateToDig;
    protected int digCooldown = 10;

    public FlamingoEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }


    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D){
            public boolean canUse() {
                return !FlamingoEntity.this.isSleeping() && super.canUse();
            }
        });
        this.goalSelector.addGoal(2, new DigGoal());
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ShrimpEntity.class, false));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
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
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.digCooldown > 0) {
            this.digCooldown--;
        }
    }


    public boolean isDigging() {
        return this.entityData.get(DIGGING);
    }

    public void setDigging(boolean digging) {
        this.entityData.set(DIGGING, digging);
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
        if(this.isDigging() && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            state.getController().forceAnimationReset();
            state.getController().setAnimation(RawAnimation.begin().then("eat", Animation.LoopType.PLAY_ONCE));
            this.setDigging(false);
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
        return ModEntityCreator.FLAMINGO.get().create(serverLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.getItem() == ModItems.RAW_SHRIMP.get();
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public void tick(){
        super.tick();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == this.level().damageSources().fall() || super.isInvulnerableTo(source);
    }

    public boolean isSleeping() {
        return this.entityData.get(SLEEPING).booleanValue();
    }

    public void setSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, Boolean.valueOf(sleeping));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SLEEPING, Boolean.valueOf(false));
        this.entityData.define(FLYING, false);
        this.entityData.define(DIGGING, false);
    }

    public void addAdditionalSaveData(CompoundTag p_29495_) {
        super.addAdditionalSaveData(p_29495_);
        p_29495_.putBoolean("Sleeping", isSleeping());
    }

    public void readAdditionalSaveData(CompoundTag p_29478_) {
        super.readAdditionalSaveData(p_29478_);
        this.setSleeping(p_29478_.getBoolean("Sleeping"));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand){
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

        Item itemForBartering = ModItems.COOKED_SHRIMP.get();
        if (item == itemForBartering && !this.isBaby()) {
            if (this.digCooldown <= 0) {
                this.stateToDig = FlamingoEntity.this.level().getBlockState(FlamingoEntity.this.blockPosition().below());

                if (!this.isInWater() && stateToDig.is(BlockTags.SAND) || stateToDig.is(Tags.Blocks.GRAVEL) || stateToDig.is(Blocks.CLAY) || stateToDig.is(Blocks.DIRT) || stateToDig.is(Blocks.GRASS_BLOCK)) {
                    this.setDigging(true);
                    this.digCooldown = 10;
                } else {
                    this.stateToDig = null;
                }
            }
        }
        if(!level().isClientSide()){

        }
        return InteractionResult.CONSUME;
    }

    public class DigGoal extends Goal {
        protected int digTime;

        public DigGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public boolean canUse() {
            return FlamingoEntity.this.isDigging();
        }

        @Override
        public void start() {
            this.digTime = 35;
        }

        @Override
        public void tick() {
            if (this.digTime > 0) {
                this.digTime--;

                if (this.digTime % 5 == 0 && this.digTime >= 10) {
                    FlamingoEntity.this.level().playSound(null, FlamingoEntity.this, SoundEvents.GRAVEL_HIT, SoundSource.BLOCKS, 0.2F, 1.2F);
                    for (int i = 0; i < 4; ++i) {
                        double d0 = FlamingoEntity.this.random.nextGaussian() * 0.01D;
                        double d1 = FlamingoEntity.this.random.nextGaussian() * 0.01D;
                        double d2 = FlamingoEntity.this.random.nextGaussian() * 0.01D;
                        ((ServerLevel) FlamingoEntity.this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, FlamingoEntity.this.stateToDig), FlamingoEntity.this.getX(), FlamingoEntity.this.getY(), FlamingoEntity.this.getZ(), 2, d0, d1, d2, 0.1D);
                    }
                }
                if (this.digTime == 10) {
                    LootTable digTable = FlamingoEntity.this.level().getServer().getLootData().getLootTable(DIGGABLES);
                    //List<ItemStack> dugItems = digTable.getRandomItems(new LootContext.Builder((ServerLevel) FlamingoEntity.this.level()).create(LootContextParamSets.EMPTY));

                    //if (!dugItems.isEmpty()) {
                    //    FlamingoEntity.this.level().playSound(null, FlamingoEntity.this, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.1F, 1.2F);
                    //}

                    //for (ItemStack stack : dugItems) {
                    //    ItemEntity itemEntity = new ItemEntity(FlamingoEntity.this.level(), FlamingoEntity.this.getX(), FlamingoEntity.this.getY(), FlamingoEntity.this.getZ(), stack);
                   //     FlamingoEntity.this.level().addFreshEntity(itemEntity);
                    //}
                    if(Math.random() < 0.25){
                        FlamingoEntity.this.spawnAtLocation(Items.FLINT);
                    }else if(Math.random() < 0.25){
                        FlamingoEntity.this.spawnAtLocation(Items.CLAY_BALL);
                    }else if(Math.random() < 0.22){
                        FlamingoEntity.this.spawnAtLocation(Items.STRING);
                    }else{
                        if(Math.random() < 0.1){
                            FlamingoEntity.this.spawnAtLocation(Items.COD);
                        }else if(Math.random() < 0.1){
                            FlamingoEntity.this.spawnAtLocation(Items.SALMON);
                        }else if(Math.random() < 0.1){
                            FlamingoEntity.this.spawnAtLocation(Items.PUFFERFISH);
                        } else if(Math.random() < 0.05){
                            FlamingoEntity.this.spawnAtLocation(Items.NAME_TAG);
                        }
                        else if(Math.random() < 0.03){
                            FlamingoEntity.this.spawnAtLocation(Items.RAW_IRON);
                        }
                    }
                    ExperienceOrb xp = new ExperienceOrb(FlamingoEntity.this.level(), FlamingoEntity.this.getX(), FlamingoEntity.this.getY(), FlamingoEntity.this.getZ(), FlamingoEntity.this.random.nextInt(1, 6));
                    FlamingoEntity.this.level().addFreshEntity(xp);
                }
            } else {
                this.stop();
            }
        }

        @Override
        public void stop() {
            FlamingoEntity.this.setDigging(false);
            FlamingoEntity.this.stateToDig = null;
            this.digTime = 0;
        }
    }
}
