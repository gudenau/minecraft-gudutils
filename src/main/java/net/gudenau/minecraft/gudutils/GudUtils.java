package net.gudenau.minecraft.gudutils;

import java.util.Optional;
import java.util.UUID;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.gudenau.minecraft.gudutils.block.ElevatorBlock;
import net.gudenau.minecraft.gudutils.block.entity.ElevatorBlockEntity;
import net.gudenau.minecraft.gudutils.command.FlimFlamCommand;
import net.gudenau.minecraft.gudutils.enchantment.*;
import net.gudenau.minecraft.gudutils.item.ColorRuneItem;
import net.gudenau.minecraft.gudutils.utils.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.minecraft.item.Items.BEDROCK;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class GudUtils implements ModInitializer{
    public static final String MOD_ID = "gud_utils";
    
    @Override
    public void onInitialize(){
        Blocks.init();
        Items.init();
        Enchantments.init();
        registerCommands();
    }
    
    private void registerCommands(){
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated)->{
            FlimFlamCommand.register(dispatcher);
        });
    }
    
    public static final class Blocks{
        public static final Optional<Block> ELEVATOR = Configuration.ELEVATOR_ENABLE.optional(()->new ElevatorBlock(FabricBlockSettings.of(Material.WOOL).allowsSpawning(Predicates::never)));
        
        private static void register(String name, Optional<Block> block){
            block.ifPresent((value)->Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), value));
        }
        
        private static void init(){
            register("elevator", ELEVATOR);
            
            Entities.init();
        }
        
        public static final class Entities{
            public static Optional<BlockEntityType<ElevatorBlockEntity>> ELEVATOR = Blocks.ELEVATOR.map((block)->BlockEntityType.Builder.create(ElevatorBlockEntity::new, block).build(null));
            
            private static <T extends BlockEntity> void register(String name, Optional<BlockEntityType<T>> type){
                type.ifPresent((value)->Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, name), value));
            }
            
            private static void init(){
                register("elevator", ELEVATOR);
            }
        }
    }
    
    public static final class Items{
        private static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "group"), ()->new ItemStack(Items.ELEVATOR.orElseGet(()->(BlockItem)BEDROCK)));
        
        public static final Optional<Item> COLOR_RUNE = Configuration.COLOR_RUNE_ENABLE.optional(()->new ColorRuneItem(new FabricItemSettings().group(GROUP).maxCount(1)));
        
        public static final Optional<BlockItem> ELEVATOR = Blocks.ELEVATOR.map((block)->new BlockItem(block, new FabricItemSettings().group(GROUP)));
    
        private static void register(Optional<BlockItem> item){
            item.ifPresent((value)->Registry.register(Registry.ITEM, Registry.BLOCK.getId(value.getBlock()), value));
        }
    
        private static void register(String name, Optional<Item> item){
            item.ifPresent((value)->Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), value));
        }
        
        private static void init(){
            register("color_rune", COLOR_RUNE);
            
            register(ELEVATOR);
        }
    }
    
    public static final class Enchantments{
        public static final Optional<Enchantment> ATTRACTION = Configuration.ATTRACTION_ENABLE.optional(AttractionEnchantment::new);
        public static final Optional<Enchantment> BREAKING = Configuration.BREAKING_ENABLE.optional(BreakingEnchantment::new);
        public static final Optional<Enchantment> FLIGHT = Configuration.FLIGHT_ENABLE.optional(FlightEnchantment::new);
        public static final Optional<Enchantment> FLIM_FLAM = Configuration.FLIM_FLAM_ENABLE.optional(FlimFlamEnchantment::new);
        public static final Optional<Enchantment> ICE = Configuration.ICE_ENABLE.optional(IceEnchantment::new);
        public static final Optional<Enchantment> SWIFT_STRIKE = Configuration.SWIFT_STRIKE_ENABLE.optional(SwiftStrikeEnchantment::new);
        public static final UUID SWIFT_STRIKE_UUID = UUID.fromString("E9290735-0FE7-43C1-B269-E10714B3239C");
        public static final Optional<Enchantment> SWIFTNESS = Configuration.SWIFTNESS_ENABLE.optional(SwiftnessEnchantment::new);
        public static final UUID SWIFTNESS_UUID = UUID.fromString("E286D96C-9E93-4347-9DA9-FCD2D14D153D");
        public static final Optional<Enchantment> USELESS = Configuration.USELESS_ENABLE.optional(UselessEnchantment::new);
        public static final Optional<Enchantment> WARDING = Configuration.WARDING_ENABLE.optional(WardingEnchantment::new);
        
        private static void register(String name, Optional<Enchantment> enchantment){
            enchantment.ifPresent((value)->Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, name), value));
        }
        
        private static void init(){
            register("attraction", ATTRACTION);
            register("flight", FLIGHT);
            register("breaking", BREAKING);
            register("flim_flam", FLIM_FLAM);
            register("ice", ICE);
            register("swift_strike", SWIFT_STRIKE);
            register("swiftness", SWIFTNESS);
            register("useless", USELESS);
            register("warding", WARDING);
        }
    }
}
