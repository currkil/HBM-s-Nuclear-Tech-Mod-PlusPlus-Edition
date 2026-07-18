package currkill.hbms_ntm_pp.block;

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

//注册常规方块
public class modBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Hbms_ntm_pp.MODID);

    public static final RegistryObject<Block> STEEL_BLOCK =
            registerBlock("steel_block", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,50.0F)
                    .sound(SoundType.METAL)),"block");//钢块
    public static final RegistryObject<Block> STRUCT_LAUNCHER =
            registerBlock("struct_launcher", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.METAL)),"block");//发射台部件

    private static <T extends Block> RegistryObject<Item> registerBlockItems(String name, RegistryObject<T> block) {
        return modItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }//注册方块物品
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,String tab) {
        RegistryObject<T> blocks = BLOCKS.register(name, block);
        RegistryObject<Item> blockitem = registerBlockItems(name, blocks);
        addItemToTab(blockitem,tab);
        return blocks;
    }//注册方块


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
