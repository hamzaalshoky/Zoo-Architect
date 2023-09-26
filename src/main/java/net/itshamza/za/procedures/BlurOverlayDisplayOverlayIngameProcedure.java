package net.itshamza.za.procedures;

import net.itshamza.za.effects.ModEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BlurOverlayDisplayOverlayIngameProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (entity instanceof LivingEntity _livEnt ? _livEnt.hasEffect(ModEffects.TOXIN.get()) : false) {
			return true;
		}
		return false;
	}
}
