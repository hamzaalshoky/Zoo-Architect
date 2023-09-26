package net.itshamza.za.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class OctopusEntity extends WaterAnimal implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    protected BlockState stateToDig;
    private static final EntityDataAccessor<Boolean> STONE = SynchedEntityData.defineId(FlamingoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> GRAVEL = SynchedEntityData.defineId(FlamingoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SAND = SynchedEntityData.defineId(FlamingoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CLAY = SynchedEntityData.defineId(FlamingoEntity.class, EntityDataSerializers.BOOLEAN);

    public OctopusEntity(EntityType<? extends WaterAnimal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        this.moveControl = new SmoothSwimmingMoveControl(this, 35, 12, 0.4F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 3);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    protected PathNavigation createNavigation(Level p_28362_) {
        return new WaterBoundPathNavigation(this, p_28362_);
    }


    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
    }

    // ANIMATIONS //

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.2F;
    }
    
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && this.isOnGround()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
            return PlayState.CONTINUE;
        }

        if (event.isMoving() && this.isInWaterOrBubble()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("swim", true));
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

    public void travel(Vec3 p_28383_) {
        if (this.isEffectiveAi() && this.isInWater()) {
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

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
        data.addAnimationController(new AnimationController(this, "attackController",
                0, this::attackPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public void tick(){
        super.tick();
        this.stateToDig = OctopusEntity.this.level.getBlockState(OctopusEntity.this.blockPosition().below());

        if (stateToDig.is(BlockTags.SAND)) {
            this.setSand(true);
        }else if (stateToDig.is(Blocks.GRAVEL)) {
            this.setGravel(true);
        }else if (stateToDig.is(Blocks.STONE)) {
            this.setStone(true);
        }else if (stateToDig.is(Blocks.CLAY)) {
            this.setClay(true);
        } else {
            this.stateToDig = null;
            this.setStone(false);
            this.setSand(false);
            this.setClay(false);
            this.setGravel(false);
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CLAY, false);
        this.entityData.define(SAND, false);
        this.entityData.define(STONE, false);
        this.entityData.define(GRAVEL, false);
    }

    public boolean isStone() {
        return this.entityData.get(STONE).booleanValue();
    }

    public void setStone(boolean sleeping) {
        this.entityData.set(STONE, Boolean.valueOf(sleeping));
    }

    public boolean isGravel() {
        return this.entityData.get(GRAVEL).booleanValue();
    }

    public void setGravel(boolean sleeping) {
        this.entityData.set(GRAVEL, Boolean.valueOf(sleeping));
    }

    public boolean isClay() {
        return this.entityData.get(CLAY).booleanValue();
    }

    public void setClay(boolean sleeping) {
        this.entityData.set(CLAY, Boolean.valueOf(sleeping));
    }

    public boolean isSand() { return this.entityData.get(SAND).booleanValue(); }

    public void setSand(boolean sleeping) {
        this.entityData.set(SAND, Boolean.valueOf(sleeping));
    }
}
