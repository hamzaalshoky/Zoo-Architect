package net.itshamza.za.block;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.block.custom.*;
import net.itshamza.za.item.ModCreativeModeTabs;
import net.itshamza.za.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ZooArchitect.MOD_ID);

    public static final RegistryObject<Block> PINEAPPLE = registerBlock("pineapple",
            () -> new PineappleBlock(BlockBehaviour.Properties.of(Material.VEGETABLE, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.WOOD)), ModCreativeModeTabs.AFRICA_TAB);

    public static final RegistryObject<Block> PRICKLY_PEAR = registerBlock("prickly_pear",
            () -> new PricklyPearBlock(BlockBehaviour.Properties.of(Material.CACTUS, MaterialColor.COLOR_GREEN).strength(0.4F).sound(SoundType.WOOL)), ModCreativeModeTabs.AFRICA_TAB);

    public static final RegistryObject<Block> STEPPE_EAGLE_EGG = registerBlock("steppe_eagle_egg",
            () -> new SteppeEagleEggBlock(BlockBehaviour.Properties.of(Material.EGG, MaterialColor.TERRACOTTA_WHITE).strength(0.2F).sound(SoundType.WOOD)), ModCreativeModeTabs.AFRICA_TAB);
    public static final RegistryObject<Block> CATTAIL = registerBlock("cattail",
            () -> new CattailBlock(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_LIGHT_GREEN).strength(0.1F).sound(SoundType.GRASS)), ModCreativeModeTabs.AFRICA_TAB);

    public static final RegistryObject<Block> WET_SAND = registerBlock("wet_sand",
            () -> new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(0.5F).sound(SoundType.SAND)), ModCreativeModeTabs.AFRICA_TAB);

    public static final RegistryObject<Block> SAND_PATH = registerBlock("sand_path",
            () -> new ZAPathBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(0.5F).sound(SoundType.SAND)), ModCreativeModeTabs.AFRICA_TAB);

    public static final RegistryObject<Block> STONE_PATH = registerBlock("stone_path",
            () -> new ZAPathBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).strength(1F).sound(SoundType.STONE)), ModCreativeModeTabs.AFRICA_TAB);

    public static final RegistryObject<Block> GRAVEL_PATH = registerBlock("gravel_path",
            () -> new ZAPathBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_GRAY).strength(0.5F).sound(SoundType.SAND)), ModCreativeModeTabs.AFRICA_TAB);
    public static final RegistryObject<Block> SEA_URCHIN = registerBlock("sea_urchin",
            () -> new SeaUrchinBlock(BlockBehaviour.Properties.of(Material.CACTUS, MaterialColor.COLOR_PURPLE).noCollission().strength(0.2F).sound(SoundType.BAMBOO)), ModCreativeModeTabs.AFRICA_TAB);
    public static final RegistryObject<Block> SEA_LETTUCE = registerBlock("sea_lettuce",
            () -> new KelpBlock(BlockBehaviour.Properties.of(Material.DECORATION, MaterialColor.COLOR_LIGHT_GREEN).noCollission().strength(0.1F).sound(SoundType.LILY_PAD)), ModCreativeModeTabs.AFRICA_TAB);
    public static final RegistryObject<Block> HOLLOW_LOG = registerBlock("hollow_log",
            () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD)), ModCreativeModeTabs.AFRICA_TAB);
    public static final RegistryObject<Block> BIRCH_HOLLOW_LOG = registerBlock("birch_hollow_log",
            () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD)), ModCreativeModeTabs.AFRICA_TAB);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
