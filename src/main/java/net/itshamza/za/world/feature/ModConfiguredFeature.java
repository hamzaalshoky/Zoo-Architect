package net.itshamza.za.world.feature;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModConfiguredFeature {

    public static final ResourceKey<ConfiguredFeature<?, ?>> PINEAPPLE_SPAWN_KEY = registerKey("pineapple_spawn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PEAR_SPAWN_KEY = registerKey("pear_spawn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CATTAIL_SPAWN_KEY = registerKey("cattail_spawn");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        register(context, PINEAPPLE_SPAWN_KEY, Feature.FLOWER,
                new RandomPatchConfiguration(4, 1, 1, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.PINEAPPLE.get())))));

        register(context, PEAR_SPAWN_KEY, Feature.FLOWER,
                new RandomPatchConfiguration(4, 1, 1, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.PRICKLY_PEAR.get())))));

        register(context, CATTAIL_SPAWN_KEY, Feature.FLOWER,
                new RandomPatchConfiguration(4, 1, 1, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.CATTAIL.get())))));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ZooArchitect.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
