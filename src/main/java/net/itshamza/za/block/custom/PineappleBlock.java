package net.itshamza.za.block.custom;

import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.StemGrownBlock;

public class PineappleBlock extends StemGrownBlock {

    public PineappleBlock(Properties p_57058_) {
        super(p_57058_);
    }

    @Override
    public StemBlock getStem() {
        return null;
    }

    @Override
    public AttachedStemBlock getAttachedStem() {
        return null;
    }
}
