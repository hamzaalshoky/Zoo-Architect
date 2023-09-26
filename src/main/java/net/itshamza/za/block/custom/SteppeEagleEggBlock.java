package net.itshamza.za.block.custom;

import net.itshamza.za.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.TurtleEggBlock;

public class SteppeEagleEggBlock extends TurtleEggBlock {

    public SteppeEagleEggBlock(Properties p_57058_) {
        super(p_57058_);
    }

    public static boolean isSand(BlockGetter p_57801_, BlockPos p_57802_) {
        return p_57801_.getBlockState(p_57802_).is(ModBlocks.STEPPE_EAGLE_NEST.get());
    }
}
