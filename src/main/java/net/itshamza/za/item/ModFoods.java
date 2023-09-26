package net.itshamza.za.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties RAW_SHRIMP = new FoodProperties.Builder().nutrition(1).saturationMod(0.2f)
            .meat().build();

    public static final FoodProperties CHEESE = new FoodProperties.Builder().nutrition(2).saturationMod(0.2f)
            .build();
    public static final FoodProperties CHEESE_SANDWICH = new FoodProperties.Builder().nutrition(4).saturationMod(0.45f)
            .build();

    public static final FoodProperties CHEESECAKE = new FoodProperties.Builder().nutrition(5).saturationMod(0.6f)
            .build();
    public static final FoodProperties COOKED_SHRIMP = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).meat()
            .build();

    public static final FoodProperties SHRIMP_SANDWICH = new FoodProperties.Builder().nutrition(7).saturationMod(0.7f).meat()
            .build();

    public static final FoodProperties BANANA = new FoodProperties.Builder().nutrition(0).saturationMod(0f)
            .build();

    public static final FoodProperties PEELED_BANANA = new FoodProperties.Builder().nutrition(4).saturationMod(0.5f)
            .build();

    public static final FoodProperties PINEAPPLE = new FoodProperties.Builder().nutrition(4).saturationMod(0.2f)
            .build();

    public static final FoodProperties FRILLED_LIZARD_MEAT = new FoodProperties.Builder().nutrition(4).saturationMod(0.2f)
            .meat().effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 1), 0.3F).effect(new MobEffectInstance(MobEffects.CONFUSION, 600, 0), 0.3F).build();

    public static final FoodProperties COOKED_FRILLED_LIZARD_MEAT = new FoodProperties.Builder().nutrition(4).saturationMod(0.2f)
            .meat().build();

    public static final FoodProperties PRICKLY_PEAR = new FoodProperties.Builder().nutrition(4).saturationMod(0.6f)
            .build();

    public static final FoodProperties RAW_CRAB_LEG = new FoodProperties.Builder().nutrition(2).saturationMod(0.2f)
            .meat().build();

    public static final FoodProperties COOKED_CRAB_LEG = new FoodProperties.Builder().nutrition(5).saturationMod(0.6f)
            .meat().build();

    public static final FoodProperties RAW_TENTACLE = new FoodProperties.Builder().nutrition(1).saturationMod(0.1f)
            .meat().build();

    public static final FoodProperties RAW_BASS = new FoodProperties.Builder().nutrition(1).saturationMod(0.2f)
            .meat().build();

    public static final FoodProperties COOKED_BASS = new FoodProperties.Builder().nutrition(5).saturationMod(0.5f).meat()
            .build();
    public static final FoodProperties DRIED_LETTUCE = new FoodProperties.Builder().nutrition(1).saturationMod(0.3f)
            .build();
}
