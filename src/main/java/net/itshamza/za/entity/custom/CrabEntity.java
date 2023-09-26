package net.itshamza.za.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;


public class CrabEntity extends Animal implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private static final EntityDataAccessor<Boolean> BURRIED = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);
    private static final Predicate<LivingEntity> WARNABLE_PREDICATE = (mob) -> {
        return mob instanceof Player && !((Player) mob).isCreative() && !mob.isSpectator();
    };
    private static final Predicate<LivingEntity> TARGETABLE_PREDICATE = (mob) -> {
        return mob instanceof Turtle;
    };
    protected BlockState stateToDig;

    public CrabEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public MobType getMobType() {
        return MobType.WATER;
    }

    public boolean isPushedByFluid() {
        return false;
    }

    public boolean canBeLeashed(Player p_30346_) {
        return false;
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new WarnPredatorsGoal());
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
        this.targetSelector.addGoal(4, new ShortDistanceTarget());
        this.targetSelector.addGoal(5, new DigGoal());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Turtle.class, false));
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

    public void tick(){
        super.tick();
        this.stateToDig = CrabEntity.this.level.getBlockState(CrabEntity.this.blockPosition().below());
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BURRIED, Boolean.valueOf(false));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    class WarnPredatorsGoal extends Goal {
        Entity target = null;

        @Override
        public boolean canUse() {
            double dist = 10D;
            List<LivingEntity> list = CrabEntity.this.level.getEntitiesOfClass(LivingEntity.class, CrabEntity.this.getBoundingBox().inflate(dist, dist, dist), WARNABLE_PREDICATE);
            double d0 = dist;
            Entity possibleTarget = null;
            for(Entity entity : list) {
                double d1 = CrabEntity.this.distanceToSqr(entity);
                if (!(d1 > d0)) {
                    d0 = d1;
                    possibleTarget = entity;
                }
            }
            target = possibleTarget;
            return !list.isEmpty();
        }

        @Override
        public boolean canContinueToUse(){
            return target != null && CrabEntity.this.distanceTo(target) < 20D && CrabEntity.this.getTarget() == null;
        }

        @Override
        public void stop() {
            target = null;
            CrabEntity.this.setRattling(false);
        }

        @Override
        public void tick(){
            CrabEntity.this.setRattling(true);
        }
    }

    public boolean isRattling() {
        return this.entityData.get(BURRIED).booleanValue();
    }

    public void setRattling(boolean rattling) {
        this.entityData.set(BURRIED, rattling);
    }

    class ShortDistanceTarget extends NearestAttackableTargetGoal<Player> {
        public ShortDistanceTarget() {
            super(CrabEntity.this, Player.class, 6, true, true, TARGETABLE_PREDICATE);
        }

        public boolean canUse() {
            if (CrabEntity.this.isBaby()) {
                return false;
            } else {
                return super.canUse();
            }
        }

        public void start() {
            super.start();
            CrabEntity.this.setRattling(true);
        }

        protected double getFollowDistance() {
            return 4D;

        }
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
            return true;
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
                    CrabEntity.this.level.playSound(null, CrabEntity.this, SoundEvents.GRAVEL_HIT, SoundSource.BLOCKS, 0.2F, 1.2F);
                    for (int i = 0; i < 4; ++i) {
                        double d0 = CrabEntity.this.random.nextGaussian() * 0.01D;
                        double d1 = CrabEntity.this.random.nextGaussian() * 0.01D;
                        double d2 = CrabEntity.this.random.nextGaussian() * 0.01D;
                        ((ServerLevel) CrabEntity.this.level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, CrabEntity.this.stateToDig), CrabEntity.this.getX(), CrabEntity.this.getY(), CrabEntity.this.getZ(), 2, d0, d1, d2, 0.1D);
                    }
                }
                if (this.digTime == 10) {
                    CrabEntity.this.spawnAtLocation(Items.IRON_NUGGET);
                }
            } else {
                this.stop();
            }
        }

        @Override
        public void stop() {
            this.digTime = 0;
        }
    }
}
