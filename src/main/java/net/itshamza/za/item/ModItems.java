package net.itshamza.za.item;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.ModEntityCreator;
import net.itshamza.za.item.custom.InvisArmorItem;
import net.itshamza.za.item.custom.PeeledBananaItem;
import net.itshamza.za.item.custom.PoisonSpearItem;
import net.itshamza.za.sound.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ZooArchitect.MOD_ID);

    public static final RegistryObject<Item> JAGUAR_SPAWN_EGG = ITEMS.register("jaguar_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.JAGUAR,14265190, 1973794,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> MANATEE_SPAWN_EGG = ITEMS.register("manatee_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.MANATEE,6843239, 7895924,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> CAPYBARA_SPAWN_EGG = ITEMS.register("capybara_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.CAPYBARA,9591339, 4533538,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> FLAMINGO_SPAWN_EGG = ITEMS.register("flamingo_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.FLAMINGO,13849704, 16223113,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> SHRIMP_SPAWN_EGG = ITEMS.register("shrimp_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.SHRIMP,11484709, 13919805,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> CHAMELEON_SPAWN_EGG = ITEMS.register("chameleon_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.CHAMELEON,1332254, 4750385,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> LEECH_SPAWN_EGG = ITEMS.register("leech_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.LEECH,3540229, 6234889,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> GOLDEN_LION_TAMARIN_SPAWN_EGG = ITEMS.register("golden_lion_tamarin_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.GOLDEN_TAMARIN,12607753, 15179796,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> SCORPION_SPAWN_EGG = ITEMS.register("scorpion_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.SCORPION,2760474, 4930606,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> FENNEC_FOX_SPAWN_EGG = ITEMS.register("fennec_fox_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.FENNEC_FOX,15647122, 15720134,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> STEPPE_EAGLE_SPAWN_EGG = ITEMS.register("steppe_eagle_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.STEPPE_EAGLE,9591339, 2760474,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> MAMBA_SPAWN_EGG = ITEMS.register("mamba_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.MAMBA,7165262, 000000,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> VIPER_SPAWN_EGG = ITEMS.register("viper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.VIPER,12289865, 13475178,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> RATTLESNAKE_SPAWN_EGG = ITEMS.register("rattlesnake_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.RATTLESNAKE,10193519, 5721922,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> COBRA_SPAWN_EGG = ITEMS.register("cobra_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.COBRA,1384485, 14867093,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> FRILLED_LIZARD_SPAWN_EGG = ITEMS.register("frilled_lizard_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.FRILLED_LIZARD,13436960, 10174254,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> VULTURE_SPAWN_EGG = ITEMS.register("vulture_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.VULTURE,7162423, 8741958,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> CRAB_SPAWN_EGG = ITEMS.register("crab_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.CRAB,13514017, 14913351,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> PENGUIN_SPAWN_EGG = ITEMS.register("penguin_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.PENGUIN,2106926, 11447458,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> OCTOPUS_SPAWN_EGG = ITEMS.register("octopus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.OCTOPUS,12279305, 14715909,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> LIONFISH_SPAWN_EGG = ITEMS.register("lionfish_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.LIONFISH,11305859, 9908529,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> BASS_SPAWN_EGG = ITEMS.register("bass_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.BASS,5725487, 10658646,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> SEAL_SPAWN_EGG = ITEMS.register("seal_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.SEAL,5788231, 4604474,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));
    public static final RegistryObject<Item> COPEPOD_SPAWN_EGG = ITEMS.register("copepod_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.COPEPOD,9341892, 10739184,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> SHARK_SPAWN_EGG = ITEMS.register("shark_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.SHARK,3751495, 13421772,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> MANTA_RAY_SPAWN_EGG = ITEMS.register("manta_ray_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.MANTA_RAY,1776415, 12434879,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));
    public static final RegistryObject<Item> JAGUAR_TOOTH = ITEMS.register("jaguar_tooth",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> JUNGLE_DAGGER = ITEMS.register("jungle_dagger",
            () -> new SwordItem(Tiers.STONE, 2, 3f,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> MELON_ON_A_STICK = ITEMS.register("melon_on_a_stick",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> RAW_SHRIMP = ITEMS.register("raw_shrimp",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.RAW_SHRIMP)));

    public static final RegistryObject<Item> COOKED_SHRIMP = ITEMS.register("cooked_shrimp",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.COOKED_SHRIMP)));

    public static final RegistryObject<Item> SHRIMP_SANDWICH = ITEMS.register("shrimp_sandwich",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.SHRIMP_SANDWICH)));

    public static final RegistryObject<Item> CHAMELEON_TAIL = ITEMS.register("chameleon_tail",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> BLOOD_BOTTLE = ITEMS.register("blood_bottle",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> BANANA = ITEMS.register("banana",
           () -> new PeeledBananaItem(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.BANANA)));

   public static final RegistryObject<Item> PEELED_BANANA = ITEMS.register("peeled_banana",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.PEELED_BANANA)));

   public static final RegistryObject<Item> PINEAPPLE_SLICE = ITEMS.register("pineapple_slice",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.PINEAPPLE)));

   public static final RegistryObject<Item> SCORPION_TAIL = ITEMS.register("scorpion_tail",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> BOSCAGE_RECORD = ITEMS.register("boscage_music_disc",
            () -> new RecordItem(4, ModSounds.BOSCAGE,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).stacksTo(1), 3600));

   public static final RegistryObject<Item> FRILLED_LIZARD_MEAT = ITEMS.register("frilled_lizard_meat",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.FRILLED_LIZARD_MEAT)));

   public static final RegistryObject<Item> COOKED_FRILLED_LIZARD_MEAT = ITEMS.register("cooked_frilled_lizard_meat",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.COOKED_FRILLED_LIZARD_MEAT)));

   public static final RegistryObject<Item> FOX_HIDE = ITEMS.register("fox_hide",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> MAMBA_FANG = ITEMS.register("mamba_fang",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> RATTLESNAKE_FANG = ITEMS.register("rattlesnake_fang",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> VIPER_FANG = ITEMS.register("viper_fang",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> COBRA_FANG = ITEMS.register("cobra_fang",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> PRICKLY_PEAR_FOOD = ITEMS.register("prickly_pear_food",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.PRICKLY_PEAR)));

   public static final RegistryObject<Item> PARCH_RECORD = ITEMS.register("parch_music_disc",
           () -> new RecordItem(4, ModSounds.PARCH,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).stacksTo(1), 3600));

   public static final RegistryObject<Item> RAW_CRAB_LEG = ITEMS.register("raw_crab_leg",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.RAW_CRAB_LEG)));

   public static final RegistryObject<Item> COOKED_CRAB_LEG = ITEMS.register("cooked_crab_leg",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.COOKED_CRAB_LEG)));

   public static final RegistryObject<Item> TENTACLE = ITEMS.register("tentacle",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.RAW_TENTACLE)));
   public static final RegistryObject<Item> POISONOUS_SPINE = ITEMS.register("poisonous_spine",
           () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> POISONOUS_SPEAR = ITEMS.register("poisonous_spear",
           () -> new PoisonSpearItem(Tiers.IRON, 2, -1.0f,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> RAW_BASS = ITEMS.register("raw_bass",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.RAW_BASS)));

   public static final RegistryObject<Item> COOKED_BASS = ITEMS.register("cooked_bass",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.COOKED_BASS)));

   public static final RegistryObject<Item> COPEPOD_MEAT = ITEMS.register("copepod_meat",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.RAW_SHRIMP)));

   public static final RegistryObject<Item> BASS_BUCKET = ITEMS.register("bass_bucket",
            () -> new MobBucketItem(ModEntityCreator.BASS, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH,
                    (new Item.Properties()).stacksTo(1).tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> JAGUAR_FUR = ITEMS.register("jaguar_fur",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> JAGUAR_FUR_BOOTS = ITEMS.register("jaguar_fur_boots",
            () -> new InvisArmorItem(ModArmorMaterials.JAGUAR_FUR, EquipmentSlot.FEET,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> DRIED_SEA_LETTUCE = ITEMS.register("dried_sea_lettuce",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.DRIED_LETTUCE)));

   public static final RegistryObject<Item> SALAMANDER_SPAWN_EGG = ITEMS.register("salamander_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.SALAMANDER,1384229, 12493845,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static final RegistryObject<Item> CARDINAL_SPAWN_EGG = ITEMS.register("cardinal_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.CARDINAL,16252968, 0,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.CHEESE)));

    public static final RegistryObject<Item> CHEESE_SANDWICH = ITEMS.register("cheese_sandwich",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.CHEESE_SANDWICH)));

    public static final RegistryObject<Item> CHEESECAKE = ITEMS.register("cheesecake",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB).food(ModFoods.CHEESECAKE)));

    public static final RegistryObject<Item> MOUSE_SPAWN_EGG = ITEMS.register("mouse_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.MOUSE,16252968, 0,
                    new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
