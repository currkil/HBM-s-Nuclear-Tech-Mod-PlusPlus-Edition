package currkill.hbms_ntm_pp.ore;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
import currkill.hbms_ntm_pp.item.modItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static currkill.hbms_ntm_pp.modCreativeModeTab.addItemToTab;


public class modOres {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Hbms_ntm_pp.MODID);

    public static final RegistryObject<Block> TUNGSTEN_ORE =
            registerOre("tungsten_ore", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.STONE)),"block");//钨矿石
    public static final RegistryObject<Block> TITANIUM_ORE =
            registerOre("titanium_ore", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.STONE)),"block");//钛矿石
    public static final RegistryObject<Block> LEAD_ORE =
            registerOre("lead_ore", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.STONE)),"block");//铅矿石


    private static <T extends Block> RegistryObject<Item> registerOreBlockItems(String name, RegistryObject<T> block) {
        return modItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }//注册矿石方块物品
    private static <T extends Block> RegistryObject<T> registerOre(String name, Supplier<T> block,String tab) {
        RegistryObject<T> blocks = BLOCKS.register(name, block);
        RegistryObject<Item> oreitem = registerOreBlockItems(name, blocks);
        addItemToTab(oreitem, tab);
        return blocks;
    }//注册矿石

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
