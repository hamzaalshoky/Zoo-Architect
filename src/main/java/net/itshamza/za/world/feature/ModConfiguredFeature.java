package net.itshamza.za.world.feature;

import net.itshamza.za.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ModConfiguredFeature {

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PINEAPPLE_SPAWN =
            FeatureUtils.register("pineapple_spawn", Feature.FLOWER,
                    new RandomPatchConfiguration(4, 1, 1, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.PINEAPPLE.get())))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PEAR_SPAWN =
            FeatureUtils.register("pear_spawn", Feature.FLOWER,
                    new RandomPatchConfiguration(4, 1, 1, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.PRICKLY_PEAR.get())))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> CATTAIL_SPAWN =
            FeatureUtils.register("cattail_spawn", Feature.FLOWER,
                    new RandomPatchConfiguration(4, 1, 1, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.CATTAIL.get())))));
}
