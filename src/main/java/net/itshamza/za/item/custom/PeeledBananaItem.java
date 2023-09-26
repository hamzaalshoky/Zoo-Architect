package net.itshamza.za.item.custom;

import net.itshamza.za.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class PeeledBananaItem extends Item {
    public PeeledBananaItem(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack p_40712_, Level p_40713_, LivingEntity p_40714_) {
        super.finishUsingItem(p_40712_, p_40713_, p_40714_);
        if (p_40712_.isEmpty()) {
            return new ItemStack(ModItems.PEELED_BANANA.get());
        } else {
            if (p_40714_ instanceof Player && !((Player)p_40714_).getAbilities().instabuild) {
                ItemStack itemstack = new ItemStack(ModItems.PEELED_BANANA.get());
                Player player = (Player)p_40714_;
                if (!player.getInventory().add(itemstack)) {
                    player.drop(itemstack, false);
                }
            }

            return p_40712_;
        }
    }
}
