package net.itshamza.za.entity.custom;

import net.itshamza.za.entity.ModEntityCreator;
import net.itshamza.za.entity.custom.ai.DirectPathNavigator;
import net.itshamza.za.entity.custom.ai.FlightMoveController;
import net.itshamza.za.entity.custom.ai.GoInvisibleWhenNear;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;


public class ChameleonEntity extends Animal implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(ChameleonEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> UPSIDE_DOWN = SynchedEntityData.defineId(ChameleonEntity.class, EntityDataSerializers.BOOLEAN);
    public float prevUpsideDownProgress;
    public float upsideDownProgress;
    private boolean jumpingUp = false;
    private int upwardsFallingTicks = 0;
    private boolean isUpsideDownNavigator;
    
    public ChameleonEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        switchNavigator(true);
    }

    public static BlockPos getLowestPos(LevelAccessor world, BlockPos pos) {
        while (!world.getBlockState(pos).isFaceSturdy(world, pos, Direction.DOWN) && pos.getY() < 255) {
            pos = pos.above();
        }
        return pos;
    }


    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 100.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(6, (new HurtByTargetGoal(this)).setAlertOthers());
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new GoInvisibleWhenNear<>(this, Monster.class, 10.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new GoInvisibleWhenNear<>(this, JaguarEntity.class, 10.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new GoInvisibleWhenNear<>(this, Player.class, 10.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Spider.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Bee.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Silverfish.class, false));
    }

    // ANIMATIONS //

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.2F;
    }


    private void switchNavigator(boolean rightsideUp) {
        if (rightsideUp) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level);
            this.isUpsideDownNavigator = false;
        } else {
            this.moveControl = new FlightMoveController(this, 1.1F, false);
            this.navigation = new DirectPathNavigator(this, level);
            this.isUpsideDownNavigator = true;
        }
    }
    
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent event) {
        if(this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", false));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
        data.addAnimationController(new AnimationController(this, "attackController",
                0, this::attackPredicate));
    }



    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            boolean prev = super.hurt(source, amount);
            if (prev) {
                this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 500, 0), this);
            }
            return prev;
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob p_146744_) {
        return ModEntityCreator.CHAMELEON.get().create(serverLevel);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.FALL || super.isInvulnerableTo(source);
    }

    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }

        if(isClimbing()){
            this.navigation.moveTo(0f, 5f, 0f, 4f);
        }
        
        prevUpsideDownProgress = upsideDownProgress;
        if (this.isUpsideDown() && upsideDownProgress < 5F) {
            upsideDownProgress++;
        }
        if (!this.isUpsideDown() && upsideDownProgress > 0F) {
            upsideDownProgress--;
        }
        if (!level.isClientSide) {
            BlockPos abovePos = this.getPositionAbove();
            BlockState aboveState = level.getBlockState(abovePos);
            BlockState belowState = level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement());
            BlockPos worldHeight = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, this.blockPosition());
            boolean validAboveState = aboveState.isFaceSturdy(level, abovePos, Direction.DOWN);
            boolean validBelowState = belowState.isFaceSturdy(level, this.getBlockPosBelowThatAffectsMyMovement(), Direction.UP);
            LivingEntity attackTarget = this.getTarget();
            if (jumpingUp && this.getY() > worldHeight.getY()) {
                jumpingUp = false;
            }
            if ((this.onGround && jumpingUp)) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, 2F, 0));
                jumpingUp = true;
            }
            if (this.isUpsideDown()) {
                jumpingUp = false;
                this.setNoGravity(!this.onGround);
                float f = 0.91F;
                this.setDeltaMovement(this.getDeltaMovement().multiply(f, 1F, f));
                if (!this.verticalCollision) {
                    if (this.onGround || validBelowState || upwardsFallingTicks > 5) {
                        this.setUpsideDown(false);
                        upwardsFallingTicks = 0;
                    } else {
                        if (!validAboveState) {
                            upwardsFallingTicks++;
                        }
                        this.setDeltaMovement(this.getDeltaMovement().add(0, 0.2F, 0));
                    }
                } else {
                    upwardsFallingTicks = 0;
                }
                if (this.horizontalCollision) {
                    upwardsFallingTicks = 0;
                    this.setDeltaMovement(this.getDeltaMovement().add(0, -0.3F, 0));
                }
                if (this.isInWall() && level.isEmptyBlock(this.getBlockPosBelowThatAffectsMyMovement())) {
                    this.setPos(this.getX(), this.getY() - 1, this.getZ());
                }
            } else {
                this.setNoGravity(false);
                if (validAboveState) {
                    this.setUpsideDown(true);
                }
            }
            if (this.isUpsideDown() && !this.isUpsideDownNavigator) {
                switchNavigator(false);
            }
            if (!this.isUpsideDown() && this.isUpsideDownNavigator) {
                switchNavigator(true);
            }
        }
    }

    public boolean isUpsideDown() {
        return this.entityData.get(UPSIDE_DOWN).booleanValue();
    }

    public void setUpsideDown(boolean upsideDown) {
        this.entityData.set(UPSIDE_DOWN, Boolean.valueOf(upsideDown));
    }

    protected BlockPos getPositionAbove() {
        return new BlockPos(this.position().x, this.getBoundingBox().maxY + 0.5000001D, this.position().z);
    }

    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setClimbing(boolean p_33820_) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (p_33820_) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
        this.entityData.define(UPSIDE_DOWN, false);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.getItem() == Items.GRASS;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    class AIUpsideDownWander extends RandomStrollGoal {

        public AIUpsideDownWander() {
            super(ChameleonEntity.this, 1D, 50);
        }

        @javax.annotation.Nullable
        protected Vec3 getPosition() {
            if (ChameleonEntity.this.isUpsideDown()) {
                for (int i = 0; i < 15; i++) {
                    Random rand = new Random();
                    BlockPos randPos = ChameleonEntity.this.blockPosition().offset(rand.nextInt(16) - 8, -2, rand.nextInt(16) - 8);
                    BlockPos lowestPos = ChameleonEntity.getLowestPos(level, randPos);
                    if (level.getBlockState(lowestPos).isFaceSturdy(level, lowestPos, Direction.DOWN)) {
                        return Vec3.atCenterOf(lowestPos);
                    }
                }
                return null;
            } else {
                return super.getPosition();
            }
        }

        public boolean canUse() {
            return super.canUse();
        }

        public boolean canContinueToUse() {
            if (ChameleonEntity.this.isUpsideDown()) {
                double d0 = ChameleonEntity.this.getX() - wantedX;
                double d2 = ChameleonEntity.this.getZ() - wantedZ;
                double d4 = d0 * d0 + d2 * d2;
                return d4 > 4;
            } else {
                return super.canContinueToUse();
            }
        }

        public void stop() {
            super.stop();
            this.wantedX = 0;
            this.wantedY = 0;
            this.wantedZ = 0;
        }

        public void start() {
            if (ChameleonEntity.this.isUpsideDown()) {
                this.mob.getMoveControl().setWantedPosition(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier * 0.7F);
            } else {
                this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
            }
        }

    }
}
