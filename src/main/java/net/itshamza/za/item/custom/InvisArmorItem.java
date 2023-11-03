package net.itshamza.za.item.custom;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.util.Locals;

public class InvisArmorItem extends ArmorItem {
    public InvisArmorItem(ArmorMaterial material, Type slot, Properties settings) {
        super(material, slot, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (entity instanceof Player) {
            Player player = (Player) entity;

            // Check if the player is wearing the invisibility boots
            if (player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof InvisArmorItem) {
                // Check if the player is not moving
                if (player.isSteppingCarefully()) {
                    // Apply the invisibility effect
                    player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 10, 1), player);
                }
            }
        }
    }
}