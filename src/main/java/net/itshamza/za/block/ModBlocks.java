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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ZooArchitect.MOD_ID);

    public static final RegistryObject<Block> PINEAPPLE = registerBlock("pineapple",
            () -> new PineappleBlock(BlockBehaviour.Properties.copy(Blocks.MELON).strength(1.0F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> PRICKLY_PEAR = registerBlock("prickly_pear",
            () -> new PricklyPearBlock(BlockBehaviour.Properties.copy(Blocks.CACTUS).strength(0.4F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> STEPPE_EAGLE_EGG = registerBlock("steppe_eagle_egg",
            () -> new SteppeEagleEggBlock(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.2F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> STEPPE_EAGLE_NEST = registerBlock("steppe_eagle_nest",
            () -> new SteppeEagleEggBlock(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK).strength(0.5F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> CATTAIL = registerBlock("cattail",
            () -> new CattailBlock(BlockBehaviour.Properties.copy(Blocks.TALL_SEAGRASS).strength(0.1F).sound(SoundType.GRASS)));

    public static final RegistryObject<Block> WET_SAND = registerBlock("wet_sand",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SAND).strength(0.5F).sound(SoundType.SAND)));

    public static final RegistryObject<Block> SAND_PATH = registerBlock("sand_path",
            () -> new ZAPathBlock(BlockBehaviour.Properties.copy(Blocks.DIRT_PATH).strength(0.5F).sound(SoundType.SAND)));

    public static final RegistryObject<Block> STONE_PATH = registerBlock("stone_path",
            () -> new ZAPathBlock(BlockBehaviour.Properties.copy(Blocks.DIRT_PATH).strength(1F).sound(SoundType.STONE)));

    public static final RegistryObject<Block> GRAVEL_PATH = registerBlock("gravel_path",
            () -> new ZAPathBlock(BlockBehaviour.Properties.copy(Blocks.DIRT_PATH).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> SEA_URCHIN = registerBlock("sea_urchin",
            () -> new SeaUrchinBlock(BlockBehaviour.Properties.copy(Blocks.CACTUS).noCollission().strength(0.2F).sound(SoundType.BAMBOO)));
    public static final RegistryObject<Block> SEA_LETTUCE = registerBlock("sea_lettuce",
            () -> new KelpBlock(BlockBehaviour.Properties.copy(Blocks.SEAGRASS).noCollission().strength(0.1F).sound(SoundType.LILY_PAD)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
