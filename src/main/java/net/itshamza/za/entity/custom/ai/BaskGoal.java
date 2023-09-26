package net.itshamza.za.entity.custom.ai;


import net.itshamza.za.entity.custom.SealEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GravelBlock;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

public class BaskGoal extends Goal {
    private final SealEntity seal;
    private final Level world;
    private BlockPos baskingPos;

    public BaskGoal(SealEntity seal) {
        this.seal = seal;
        this.world = seal.level;
    }

    @Override
    public boolean canUse() {
        if (this.seal.isPassenger() || this.seal.isInWater()) {
            return false;
        }

        // Check if the seal is standing on a basking block and is under the sun
        BlockPos pos = new BlockPos(this.seal.getX(), this.seal.getBoundingBox().minY, this.seal.getZ());
        BlockState state = this.world.getBlockState(pos);
        boolean isBaskingBlock = state.getBlock() instanceof SandBlock || state.getBlock() instanceof GravelBlock || state.is(Tags.Blocks.STONE);
        boolean isUnderSun = this.world.isDay() && this.world.canSeeSky(pos.above());

        if (isBaskingBlock && isUnderSun) {
            this.baskingPos = pos;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void start() {
        this.seal.setBasking(true);
    }

    @Override
    public void stop() {
        this.seal.setBasking(false);
    }

    @Override
    public void tick() {
        if (!this.canUse()) {
            this.stop();
        }
    }
}