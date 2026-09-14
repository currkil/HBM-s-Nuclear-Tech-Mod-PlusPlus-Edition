package currkill.hbms_ntm_pp.block;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
import currkill.hbms_ntm_pp.item.modItems;
import currkill.hbms_ntm_pp.modCreativeModeTab.Tab;
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
import static currkill.hbms_ntm_pp.tag.modTags.addBlockToTag;

/**
 * 常规方块的注册类。
 * <p>
 * 矿石类方块请见 {@link modOres}，这里只放其余方块。每个方块会同时注册对应的 {@code BlockItem}，
 * 并自动完成两件事：通过 {@link currkill.hbms_ntm_pp.modCreativeModeTab#addItemToTab}
 * 归入创造模式物品栏，以及通过 {@link currkill.hbms_ntm_pp.tag.modTags#addBlockToTag} 登记挖掘标签。
 *
 * @author currkill-deepseek
 */
public class modBlocks {

    /** 方块延迟注册器。 */
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Hbms_ntm_pp.MODID);

    /** 钢块，金属材质，抗爆等级较高。 */
    public static final RegistryObject<Block> STEEL_BLOCK =
            registerBlock("steel_block", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,50.0F)
                    .sound(SoundType.METAL)), Tab.BLOCK
            ,"iron","pickaxe");

    /** 发射台部件，导弹发射台多方块结构的组成方块。 */
    public static final RegistryObject<Block> STRUCT_LAUNCHER =
            registerBlock("struct_launcher", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.METAL)), Tab.BLOCK
            ,"iron","pickaxe");

    /**
     * 为指定方块注册对应的方块物品。
     *
     * @param name  方块的注册名，将作为物品的注册名
     * @param block 目标方块
     * @param <T>   方块类型
     * @return 注册得到的方块物品
     */
    private static <T extends Block> RegistryObject<Item> registerBlockItems(String name, RegistryObject<T> block) {
        return modItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    /**
     * 注册一个方块并完成配套工作：注册方块物品、归入创造模式物品栏、登记挖掘标签。
     *
     * @param name    方块的注册名
     * @param block   方块实例的构造器
     * @param tab     归属的创造模式物品栏
     * @param tagType 挖掘标签标识，可用取值见 {@link currkill.hbms_ntm_pp.tag.modTags}
     * @param <T>     方块类型
     * @return 注册得到的方块
     */
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,Tab tab,String... tagType) {
        RegistryObject<T> blocks = BLOCKS.register(name, block);
        RegistryObject<Item> blockitem = registerBlockItems(name, blocks);
        addItemToTab(blockitem,tab);
        addBlockToTag((RegistryObject<Block>) blocks, tagType);
        return blocks;
    }


    /**
     * 把本类持有的方块注册器挂到模组事件总线上。
     *
     * @param eventBus 模组事件总线
     */
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
