package net.itshamza.za.item.custom;

import net.itshamza.za.item.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PearItem extends Item {
    public PearItem(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack p_40712_, Level p_40713_, LivingEntity p_40714_) {
        super.finishUsingItem(p_40712_, p_40713_, p_40714_);
        p_40714_.hurt(DamageSource.CACTUS, 1);
        return p_40712_;
    }
}
