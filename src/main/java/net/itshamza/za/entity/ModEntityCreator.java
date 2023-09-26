package net.itshamza.za.entity;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.client.*;
import net.itshamza.za.entity.custom.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ZooArchitect.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityCreator {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ZooArchitect.MOD_ID);

    // REGESTRIES

    public static final RegistryObject<EntityType<JaguarEntity>> JAGUAR = ENTITY_TYPES.register("jaguar", () -> EntityType.Builder.of(JaguarEntity::new, MobCategory.CREATURE).sized(1.4F, 1.4F).build(new ResourceLocation(ZooArchitect.MOD_ID, "jaguar").toString()));
    public static final RegistryObject<EntityType<ManateeEntity>> MANATEE = ENTITY_TYPES.register("manatee", () -> EntityType.Builder.of(ManateeEntity::new, MobCategory.WATER_CREATURE).sized(2.2F, 1.4F).build(new ResourceLocation(ZooArchitect.MOD_ID, "manatee").toString()));
    public static final RegistryObject<EntityType<CapybaraEntity>> CAPYBARA = ENTITY_TYPES.register("capybara", () -> EntityType.Builder.of(CapybaraEntity::new, MobCategory.CREATURE).sized(1F, 1.4F).build(new ResourceLocation(ZooArchitect.MOD_ID, "capybara").toString()));
    public static final RegistryObject<EntityType<FlamingoEntity>> FLAMINGO = ENTITY_TYPES.register("flamingo", () -> EntityType.Builder.of(FlamingoEntity::new, MobCategory.CREATURE).sized(0.4F, 1.7F).build(new ResourceLocation(ZooArchitect.MOD_ID, "flamingo").toString()));
    public static final RegistryObject<EntityType<ShrimpEntity>> SHRIMP = ENTITY_TYPES.register("shrimp", () -> EntityType.Builder.of(ShrimpEntity::new, MobCategory.WATER_CREATURE).sized(0.2F, 0.1F).build(new ResourceLocation(ZooArchitect.MOD_ID, "shrimp").toString()));
    public static final RegistryObject<EntityType<ChameleonEntity>> CHAMELEON = ENTITY_TYPES.register("chameleon", () -> EntityType.Builder.of(ChameleonEntity::new, MobCategory.CREATURE).sized(0.8F, 0.4F).build(new ResourceLocation(ZooArchitect.MOD_ID, "chameleon").toString()));
    public static final RegistryObject<EntityType<LeechEntity>> LEECH = ENTITY_TYPES.register("leech", () -> EntityType.Builder.of(LeechEntity::new, MobCategory.CREATURE).sized(0.4F, 0.3F).build(new ResourceLocation(ZooArchitect.MOD_ID, "leech").toString()));
    public static final RegistryObject<EntityType<GoldenTamarinEntity>> GOLDEN_TAMARIN = ENTITY_TYPES.register("golden_tamarin", () -> EntityType.Builder.of(GoldenTamarinEntity::new, MobCategory.CREATURE).sized(0.7F, 0.8F).build(new ResourceLocation(ZooArchitect.MOD_ID, "golden_tamarin").toString()));
    public static final RegistryObject<EntityType<ScorpionEntity>> SCORPION = ENTITY_TYPES.register("scorpion", () -> EntityType.Builder.of(ScorpionEntity::new, MobCategory.CREATURE).sized(0.4F, 0.1F).build(new ResourceLocation(ZooArchitect.MOD_ID, "scorpion").toString()));
    public static final RegistryObject<EntityType<FennecFoxEntity>> FENNEC_FOX = ENTITY_TYPES.register("fennec_fox", () -> EntityType.Builder.of(FennecFoxEntity::new, MobCategory.CREATURE).sized(0.6F, 0.6F).build(new ResourceLocation(ZooArchitect.MOD_ID, "fennec_fox").toString()));
    public static final RegistryObject<EntityType<SteppeEagleEntity>> STEPPE_EAGLE = ENTITY_TYPES.register("steppe_eagle", () -> EntityType.Builder.of(SteppeEagleEntity::new, MobCategory.CREATURE).sized(1F, 1.1F).build(new ResourceLocation(ZooArchitect.MOD_ID, "steppe_eagle").toString()));
    public static final RegistryObject<EntityType<MambaEntity>> MAMBA = ENTITY_TYPES.register("mamba", () -> EntityType.Builder.of(MambaEntity::new, MobCategory.CREATURE).sized(0.9F, 0.2F).build(new ResourceLocation(ZooArchitect.MOD_ID, "mamba").toString()));
    public static final RegistryObject<EntityType<ViperEntity>> VIPER = ENTITY_TYPES.register("viper", () -> EntityType.Builder.of(ViperEntity::new, MobCategory.CREATURE).sized(0.9F, 0.2F).build(new ResourceLocation(ZooArchitect.MOD_ID, "viper").toString()));
    public static final RegistryObject<EntityType<RattlesnakeEntity>> RATTLESNAKE = ENTITY_TYPES.register("rattlesnake", () -> EntityType.Builder.of(RattlesnakeEntity::new, MobCategory.CREATURE).sized(0.9F, 0.2F).build(new ResourceLocation(ZooArchitect.MOD_ID, "rattlesnake").toString()));
    public static final RegistryObject<EntityType<CobraEntity>> COBRA = ENTITY_TYPES.register("cobra", () -> EntityType.Builder.of(CobraEntity::new, MobCategory.CREATURE).sized(1.3F, 0.3F).build(new ResourceLocation(ZooArchitect.MOD_ID, "cobra").toString()));
    public static final RegistryObject<EntityType<FrilledLizardEntity>> FRILLED_LIZARD = ENTITY_TYPES.register("frilled_lizard", () -> EntityType.Builder.of(FrilledLizardEntity::new, MobCategory.CREATURE).sized(0.7F, 0.3F).build(new ResourceLocation(ZooArchitect.MOD_ID, "frilled_lizard").toString()));
    public static final RegistryObject<EntityType<VultureEntity>> VULTURE = ENTITY_TYPES.register("vulture", () -> EntityType.Builder.of(VultureEntity::new, MobCategory.CREATURE).sized(0.9F, 1.2F).build(new ResourceLocation(ZooArchitect.MOD_ID, "vulture").toString()));
    public static final RegistryObject<EntityType<CrabEntity>> CRAB = ENTITY_TYPES.register("crab", () -> EntityType.Builder.of(CrabEntity::new, MobCategory.WATER_CREATURE).sized(0.7F, 0.6F).build(new ResourceLocation(ZooArchitect.MOD_ID, "crab").toString()));
    public static final RegistryObject<EntityType<PenguinEntity>> PENGUIN = ENTITY_TYPES.register("penguin", () -> EntityType.Builder.of(PenguinEntity::new, MobCategory.CREATURE).sized(0.7F, 1F).build(new ResourceLocation(ZooArchitect.MOD_ID, "penguin").toString()));
    public static final RegistryObject<EntityType<OctopusEntity>> OCTOPUS = ENTITY_TYPES.register("octopus", () -> EntityType.Builder.of(OctopusEntity::new, MobCategory.WATER_CREATURE).sized(0.8F, 1.6F).build(new ResourceLocation(ZooArchitect.MOD_ID, "octopus").toString()));
    public static final RegistryObject<EntityType<LionfishEntity>> LIONFISH = ENTITY_TYPES.register("lionfish", () -> EntityType.Builder.of(LionfishEntity::new, MobCategory.WATER_CREATURE).sized(0.9F, 1F).build(new ResourceLocation(ZooArchitect.MOD_ID, "lionfish").toString()));
    public static final RegistryObject<EntityType<BassEntity>> BASS = ENTITY_TYPES.register("bass", () -> EntityType.Builder.of(BassEntity::new, MobCategory.WATER_CREATURE).sized(0.6F, 0.4F).build(new ResourceLocation(ZooArchitect.MOD_ID, "bass").toString()));
    public static final RegistryObject<EntityType<SealEntity>> SEAL = ENTITY_TYPES.register("seal", () -> EntityType.Builder.of(SealEntity::new, MobCategory.WATER_CREATURE).sized(1.8F, 0.7F).build(new ResourceLocation(ZooArchitect.MOD_ID, "seal").toString()));
    public static final RegistryObject<EntityType<CopepodEntity>> COPEPOD = ENTITY_TYPES.register("copepod", () -> EntityType.Builder.of(CopepodEntity::new, MobCategory.WATER_CREATURE).sized(0.5F, 0.1F).build(new ResourceLocation(ZooArchitect.MOD_ID, "copepod").toString()));
    public static final RegistryObject<EntityType<SharkEntity>> SHARK = ENTITY_TYPES.register("shark", () -> EntityType.Builder.of(SharkEntity::new, MobCategory.WATER_CREATURE).sized(1.9F, 1.4F).build(new ResourceLocation(ZooArchitect.MOD_ID, "shark").toString()));
    public static final RegistryObject<EntityType<MantaRayEntity>> MANTA_RAY = ENTITY_TYPES.register("manta_ray", () -> EntityType.Builder.of(MantaRayEntity::new, MobCategory.WATER_CREATURE).sized(2.6F, 0.4F).build(new ResourceLocation(ZooArchitect.MOD_ID, "manta_ray").toString()));
    public static final RegistryObject<EntityType<SalamanderEntity>> SALAMANDER = ENTITY_TYPES.register("salamander", () -> EntityType.Builder.of(SalamanderEntity::new, MobCategory.CREATURE).sized(0.7F, 0.2F).build(new ResourceLocation(ZooArchitect.MOD_ID, "salamander").toString()));
    public static final RegistryObject<EntityType<CardinalEntity>> CARDINAL = ENTITY_TYPES.register("cardinal", () -> EntityType.Builder.of(CardinalEntity::new, MobCategory.CREATURE).sized(0.3F, 0.4F).build(new ResourceLocation(ZooArchitect.MOD_ID, "cardinal").toString()));
    public static final RegistryObject<EntityType<MouseEntity>> MOUSE = ENTITY_TYPES.register("mouse", () -> EntityType.Builder.of(MouseEntity::new, MobCategory.CREATURE).sized(0.6F, 0.4F).build(new ResourceLocation(ZooArchitect.MOD_ID, "mouse").toString()));
    // ATTRIBUTES

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntityCreator.JAGUAR.get(), JaguarEntity.setAttributes());
        event.put(ModEntityCreator.MANATEE.get(), ManateeEntity.setAttributes());
        event.put(ModEntityCreator.CAPYBARA.get(), CapybaraEntity.setAttributes());
        event.put(ModEntityCreator.FLAMINGO.get(), FlamingoEntity.setAttributes());
        event.put(ModEntityCreator.SHRIMP.get(), FlamingoEntity.setAttributes());
        event.put(ModEntityCreator.CHAMELEON.get(), ChameleonEntity.setAttributes());
        event.put(ModEntityCreator.LEECH.get(), LeechEntity.setAttributes());
        event.put(ModEntityCreator.GOLDEN_TAMARIN.get(), GoldenTamarinEntity.setAttributes());
        event.put(ModEntityCreator.SCORPION.get(), ScorpionEntity.setAttributes());
        event.put(ModEntityCreator.FENNEC_FOX.get(), FennecFoxEntity.setAttributes());
        event.put(ModEntityCreator.STEPPE_EAGLE.get(), SteppeEagleEntity.setAttributes());
        event.put(ModEntityCreator.MAMBA.get(), MambaEntity.setAttributes());
        event.put(ModEntityCreator.VIPER.get(), ViperEntity.setAttributes());
        event.put(ModEntityCreator.RATTLESNAKE.get(), RattlesnakeEntity.setAttributes());
        event.put(ModEntityCreator.COBRA.get(), CobraEntity.setAttributes());
        event.put(ModEntityCreator.FRILLED_LIZARD.get(), FrilledLizardEntity.setAttributes());
        event.put(ModEntityCreator.VULTURE.get(), VultureEntity.setAttributes());
        event.put(ModEntityCreator.CRAB.get(), CrabEntity.setAttributes());
        event.put(ModEntityCreator.PENGUIN.get(), PenguinEntity.setAttributes());
        event.put(ModEntityCreator.OCTOPUS.get(), OctopusEntity.setAttributes());
        event.put(ModEntityCreator.LIONFISH.get(), LionfishEntity.setAttributes());
        event.put(ModEntityCreator.BASS.get(), BassEntity.setAttributes());
        event.put(ModEntityCreator.SEAL.get(), SealEntity.setAttributes());
        event.put(ModEntityCreator.COPEPOD.get(), CopepodEntity.setAttributes());
        event.put(ModEntityCreator.SHARK.get(), SharkEntity.setAttributes());
        event.put(ModEntityCreator.MANTA_RAY.get(), MantaRayEntity.setAttributes());
        event.put(ModEntityCreator.SALAMANDER.get(), SalamanderEntity.setAttributes());
        event.put(ModEntityCreator.CARDINAL.get(), CardinalEntity.setAttributes());
        event.put(ModEntityCreator.MOUSE.get(), MouseEntity.setAttributes());
    }

    // RENDERERS

    @SubscribeEvent
    public static void registerEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityCreator.JAGUAR.get(), JaguarRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.MANATEE.get(), ManateeRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.CAPYBARA.get(), CapybaraRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.FLAMINGO.get(), FlamingoRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.SHRIMP.get(), ShrimpRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.CHAMELEON.get(), ChameleonRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.LEECH.get(), LeechRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.GOLDEN_TAMARIN.get(), GoldenTamarinRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.SCORPION.get(), ScorpionRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.FENNEC_FOX.get(), FennecFoxRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.STEPPE_EAGLE.get(), SteppeEagleRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.MAMBA.get(), MambaRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.VIPER.get(), ViperRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.RATTLESNAKE.get(), RattlesnakeRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.COBRA.get(), CobraRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.FRILLED_LIZARD.get(), FrilledLizardRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.VULTURE.get(), VultureRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.CRAB.get(), CrabRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.PENGUIN.get(), PenguinRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.OCTOPUS.get(), OctopusRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.LIONFISH.get(), LionfishRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.BASS.get(), BassRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.SEAL.get(), SealRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.COPEPOD.get(), CopepodRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.SHARK.get(), SharkRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.MANTA_RAY.get(), MantaRayRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.SALAMANDER.get(), SalamanderRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.CARDINAL.get(), CardinalRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.MOUSE.get(), MouseRenderer::new);
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
