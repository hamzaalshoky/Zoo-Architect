package net.itshamza.za;

import com.mojang.logging.LogUtils;
import net.itshamza.za.block.ModBlocks;
import net.itshamza.za.effects.ModEffects;
import net.itshamza.za.entity.ModEntityCreator;
import net.itshamza.za.entity.ModEntitySpawns;
import net.itshamza.za.event.ServerEvents;
import net.itshamza.za.item.ModItems;
import net.itshamza.za.potion.ModPotions;
import net.itshamza.za.sound.ModSounds;
import net.itshamza.za.util.BetterBrewingRecipe;
import net.itshamza.za.world.feature.ModPlacedFeature;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
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
import software.bernie.geckolib3.GeckoLib;

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

        GeckoLib.initialize();

        ModEntityCreator.register(eventBus);
        ModItems.register(eventBus);
        ModSounds.register(eventBus);
        ModBlocks.register(eventBus);
        ModEffects.register(eventBus);
        ModPotions.register(eventBus);
        ModPlacedFeature.register(eventBus);
        MinecraftForge.EVENT_BUS.register(new ServerEvents());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModEntitySpawns.entitySpawnPlacementRegistry();

    }

    private void clientSetup(final FMLCommonSetupEvent event){
        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.PINEAPPLE.get(), RenderType.cutout());
        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.PRICKLY_PEAR.get(), RenderType.cutout());
        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.STEPPE_EAGLE_NEST.get(), RenderType.cutout());
        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.CATTAIL.get(), RenderType.cutout());
        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.SEA_LETTUCE.get(), RenderType.cutout());
        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.SEA_URCHIN.get(), RenderType.cutout());
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
