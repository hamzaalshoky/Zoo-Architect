package net.itshamza.za.world.feature;

import net.itshamza.za.ZooArchitect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeature {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ZooArchitect.MOD_ID);
    public static final RegistryObject<PlacedFeature> PINEAPPLE_PLACED =
            PLACED_FEATURES.register("pineapple_placed",
                    () -> new PlacedFeature((Holder<ConfiguredFeature<?,?>>)(Holder<? extends ConfiguredFeature<?,?>>)
                            ModConfiguredFeature.PINEAPPLE_SPAWN, List.of(RarityFilter.onAverageOnceEvery(16),
                            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> PEAR_PLACED =
            PLACED_FEATURES.register("pear_placed",
                    () -> new PlacedFeature((Holder<ConfiguredFeature<?,?>>)(Holder<? extends ConfiguredFeature<?,?>>)
                            ModConfiguredFeature.PEAR_SPAWN, List.of(RarityFilter.onAverageOnceEvery(16),
                            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> CATTAIL_PLACED =
            PLACED_FEATURES.register("cattail_placed",
                    () -> new PlacedFeature((Holder<ConfiguredFeature<?,?>>)(Holder<? extends ConfiguredFeature<?,?>>)
                            ModConfiguredFeature.CATTAIL_SPAWN, List.of(RarityFilter.onAverageOnceEvery(8),
                            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));


    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }
}
