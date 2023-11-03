package net.itshamza.za;

import com.mojang.logging.LogUtils;
import net.itshamza.za.block.ModBlocks;
import net.itshamza.za.effects.ModEffects;
import net.itshamza.za.entity.ModEntityCreator;
import net.itshamza.za.entity.ModEntitySpawns;
import net.itshamza.za.event.ServerEvents;
import net.itshamza.za.item.ModCreativeModeTabs;
import net.itshamza.za.item.ModItems;
import net.itshamza.za.potion.ModPotions;
import net.itshamza.za.sound.ModSounds;
import net.itshamza.za.util.BetterBrewingRecipe;
import net.itshamza.za.world.feature.ModPlacedFeature;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ZooArchitect.MOD_ID)
public class ZooArchitect
{
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "za";

    public ZooArchitect()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntityCreator.register(eventBus);
        ModCreativeModeTabs.register(eventBus);
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModEffects.register(eventBus);
        ModPotions.register(eventBus);
        MinecraftForge.EVENT_BUS.register(new ServerEvents());
        GeckoLib.initialize();
        eventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTab() == ModCreativeModeTabs.AFRICA_TAB.get()) {
            event.accept(ModItems.JAGUAR_SPAWN_EGG);
            event.accept(ModItems.CAPYBARA_SPAWN_EGG);
            event.accept(ModItems.FLAMINGO_SPAWN_EGG);
            event.accept(ModItems.MELON_ON_A_STICK);
            event.accept(ModItems.CAPYBARA_SPAWN_EGG);
            event.accept(ModItems.MANATEE_SPAWN_EGG);
            event.accept(ModItems.SHRIMP_SPAWN_EGG);
            event.accept(ModItems.CHAMELEON_SPAWN_EGG);
            event.accept(ModItems.LEECH_SPAWN_EGG);
            event.accept(ModItems.GOLDEN_LION_TAMARIN_SPAWN_EGG);
            event.accept(ModItems.SCORPION_SPAWN_EGG);
            event.accept(ModItems.FENNEC_FOX_SPAWN_EGG);
            event.accept(ModItems.VULTURE_SPAWN_EGG);
            event.accept(ModItems.STEPPE_EAGLE_SPAWN_EGG);
            event.accept(ModItems.MAMBA_SPAWN_EGG);
            event.accept(ModItems.VIPER_SPAWN_EGG);
            event.accept(ModItems.RATTLESNAKE_SPAWN_EGG);
            event.accept(ModItems.COBRA_SPAWN_EGG);
            event.accept(ModItems.FRILLED_LIZARD_SPAWN_EGG);
            event.accept(ModItems.CRAB_SPAWN_EGG);
            event.accept(ModItems.PENGUIN_SPAWN_EGG);
            event.accept(ModItems.OCTOPUS_SPAWN_EGG);
            event.accept(ModItems.LIONFISH_SPAWN_EGG);
            event.accept(ModItems.SEAL_SPAWN_EGG);
            event.accept(ModItems.COPEPOD_SPAWN_EGG);
            event.accept(ModItems.SHARK_SPAWN_EGG);
            event.accept(ModItems.MANTA_RAY_SPAWN_EGG);
            event.accept(ModItems.SQUIRREL_SPAWN_EGG);
            event.accept(ModItems.OPOSSUM_SPAWN_EGG);
            event.accept(ModItems.CARDINAL_SPAWN_EGG);
            event.accept(ModItems.MOUSE_SPAWN_EGG);

            event.accept(ModItems.JAGUAR_TOOTH);
            event.accept(ModItems.JUNGLE_DAGGER);
            event.accept(ModItems.MELON_ON_A_STICK);
            event.accept(ModItems.RAW_SHRIMP);
            event.accept(ModItems.COOKED_SHRIMP);
            event.accept(ModItems.SHRIMP_SANDWICH);
            event.accept(ModItems.CHAMELEON_TAIL);
            event.accept(ModItems.BLOOD_BOTTLE);
            event.accept(ModItems.BANANA);
            event.accept(ModItems.PEELED_BANANA);
            event.accept(ModItems.PINEAPPLE_SLICE);
            event.accept(ModItems.SCORPION_TAIL);
            //event.accept(ModItems.BOSCAGE_RECORD);
            event.accept(ModItems.FRILLED_LIZARD_MEAT);
            event.accept(ModItems.COOKED_FRILLED_LIZARD_MEAT);
            event.accept(ModItems.FOX_HIDE);
            event.accept(ModItems.MAMBA_FANG);
            event.accept(ModItems.RATTLESNAKE_FANG);
            event.accept(ModItems.COBRA_FANG);
            event.accept(ModItems.VIPER_FANG);
            event.accept(ModItems.PRICKLY_PEAR_FOOD);
            //event.accept(ModItems.PARCH_RECORD);
            event.accept(ModItems.RAW_CRAB_LEG);
            event.accept(ModItems.COOKED_CRAB_LEG);
            event.accept(ModItems.TENTACLE);
            event.accept(ModItems.POISONOUS_SPINE);
            event.accept(ModItems.POISONOUS_SPEAR);
            event.accept(ModItems.RAW_BASS);
            event.accept(ModItems.COOKED_BASS);
            event.accept(ModItems.COPEPOD_MEAT);
            event.accept(ModItems.BASS_BUCKET);
            event.accept(ModItems.JAGUAR_FUR);
            event.accept(ModItems.JAGUAR_FUR_BOOTS);
            event.accept(ModItems.DRIED_SEA_LETTUCE);
            event.accept(ModItems.CHEESE);
            event.accept(ModItems.CHEESE_SANDWICH);
            event.accept(ModItems.CHEESECAKE);
            event.accept(ModItems.RAT_POISON);


            event.accept(ModBlocks.PINEAPPLE);
            event.accept(ModBlocks.PRICKLY_PEAR);
            event.accept(ModBlocks.STEPPE_EAGLE_EGG);
            event.accept(ModBlocks.STEPPE_EAGLE_NEST);
            event.accept(ModBlocks.CATTAIL);
            event.accept(ModBlocks.WET_SAND);
            event.accept(ModBlocks.SAND_PATH);
            event.accept(ModBlocks.GRAVEL_PATH);
            event.accept(ModBlocks.STONE_PATH);
            event.accept(ModBlocks.SEA_URCHIN);
            event.accept(ModBlocks.SEA_LETTUCE);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModEntitySpawns.entitySpawnPlacementRegistry();

    }

    private void clientSetup(final FMLCommonSetupEvent event){

    }

    private void setup(final FMLCommonSetupEvent event)
    {

        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD,
                ModItems.CHAMELEON_TAIL.get(), Potions.LONG_INVISIBILITY));

        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD,
               ModItems.BLOOD_BOTTLE.get(), Potions.REGENERATION));

        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD,
                ModItems.VIPER_FANG.get(), Potions.POISON));

        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD,
                ModItems.RATTLESNAKE_FANG.get(), Potions.STRONG_POISON));

        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD,
                ModItems.MAMBA_FANG.get(), ModPotions.TOXIN_POTION.get()));

        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD,
                ModItems.COBRA_FANG.get(), ModPotions.VENOM_POTION.get()));
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
