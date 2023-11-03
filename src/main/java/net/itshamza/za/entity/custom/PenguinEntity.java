package net.itshamza.za.entity.custom;

import net.itshamza.za.entity.custom.ai.CreatureAITargetItems;
import net.itshamza.za.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.animal.Salmon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.CocoaDecorator;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.ForgeEventFactory;
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
import java.util.UUID;
import java.util.function.Predicate;


public class PenguinEntity extends TamableAnimal implements GeoEntity {
    private static final EntityDataAccessor<Boolean> DIGGING = SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.BOOLEAN);
    protected BlockState stateToDig;
    protected int digCooldown = 10;

    private boolean isOnIce = false;

    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    public PenguinEntity(EntityType<? extends TamableAnimal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        if(this.isInWaterOrBubble()){
            this.moveControl = new SmoothSwimmingMoveControl(this, 20, 40, 0.1F, 0.1F, true);
            this.lookControl = new SmoothSwimmingLookControl(this, 3);
        }else{
            this.lookControl = new LookControl(this);
            this.moveControl = new MoveControl(this);
        }
    }


    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new PenguinEntity.DigGoal());
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, ShrimpEntity.class, 5, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Salmon.class, 5, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Cod.class, 5, true, true, (Predicate<LivingEntity>)null));
    }

    // ANIMATIONS //

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

    private PlayState predicate(software.bernie.geckolib.core.animation.AnimationState animationState) {
        if(animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if(this.isDigging()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("dig", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if(this.isTame() && animationState.isMoving()) {
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

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DIGGING, false);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

        Item itemForTaming = Items.COD;
        Item itemForBartering = ModItems.RAW_SHRIMP.get();

        if (item == itemForTaming && !isTame()) {
            if (this.level().isClientSide) {
                return InteractionResult.CONSUME;
            } else {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                    if (!this.level().isClientSide) {
                        super.tame(player);
                        this.navigation.recomputePath();
                        this.setTarget(null);
                        this.level().broadcastEntityEvent(this, (byte) 7);
                    }
                }

                return InteractionResult.SUCCESS;
            }
        }else if (item == itemForBartering) {
            if (this.digCooldown <= 0) {
                this.stateToDig = PenguinEntity.this.level().getBlockState(PenguinEntity.this.blockPosition().below());

                if (this.isInWaterOrBubble()) {
                    this.setDigging(true);
                    this.digCooldown = 10;
                } else {
                    this.stateToDig = null;
                }
            }
        }

        if(isTame() && !this.level().isClientSide && hand == InteractionHand.MAIN_HAND) {
            return InteractionResult.SUCCESS;
        }

        if (itemstack.getItem() == itemForTaming) {
            return InteractionResult.PASS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
    }

    @Override
    public Team getTeam() {
        return super.getTeam();
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
            return PenguinEntity.this.isDigging();
        }

        @Override
        public void start() {
            this.digTime = 15;
        }

        @Override
        public void tick() {
            if (this.digTime > 0) {
                this.digTime--;
                if (this.digTime == 10) {
                    if(Math.random() < 0.5){
                        PenguinEntity.this.spawnAtLocation(Items.COD);
                    }else if(Math.random() < 0.25){
                        PenguinEntity.this.spawnAtLocation(Items.SALMON);
                    }else if(Math.random() < 0.125){
                        PenguinEntity.this.spawnAtLocation(Items.PUFFERFISH);
                    } else if(Math.random() < 0.125){
                        PenguinEntity.this.spawnAtLocation(Items.TROPICAL_FISH);
                    }
                }
            } else {
                this.stop();
            }
        }

        @Override
        public void stop() {
            PenguinEntity.this.setDigging(false);
            PenguinEntity.this.stateToDig = null;
            this.digTime = 0;
        }
    }

    public boolean isDigging() {
        return this.entityData.get(DIGGING);
    }

    public void setDigging(boolean digging) {
        this.entityData.set(DIGGING, digging);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.digCooldown > 0) {
            this.digCooldown--;
        }
    }

}
