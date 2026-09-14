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
 * 矿石类方块的注册类。
 * <p>
 * 其它方块请见 {@link modBlocks}。每个矿石会同时注册对应的 {@code BlockItem}，并自动归入
 * 「矿石和方块」创造模式物品栏、登记挖掘标签。
 * <p>
 * 矿石分为两类：普通矿石（如铅、钛、钨）与矿簇（{@code cluster_} 前缀，另有 {@code depth_}
 * 前缀的深层变体，对应原版深层矿石）。
 *
 * @author currkill-deepseek
 */
public class modOres {

    /** 方块延迟注册器。 */
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Hbms_ntm_pp.MODID);

    /** 钨矿石。 */
    public static final RegistryObject<Block> TUNGSTEN_ORE =
            registerOre("tungsten_ore", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.STONE)), Tab.BLOCK,
                    "iron","pickaxe");

    /** 钛矿石。 */
    public static final RegistryObject<Block> TITANIUM_ORE =
            registerOre("titanium_ore", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.STONE)), Tab.BLOCK,
                    "iron","pickaxe");

    /** 铅矿石。 */
    public static final RegistryObject<Block> LEAD_ORE =
            registerOre("lead_ore", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.STONE)), Tab.BLOCK,
                    "iron","pickaxe");

    /** 铁矿簇。 */
    public static final RegistryObject<Block> CLUSTER_IRON_ORE =
            registerOre("cluster_iron_ore", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.STONE)), Tab.BLOCK,
                    "iron","pickaxe");

    /** 钛矿簇。 */
    public static final RegistryObject<Block> CLUSTER_TITANIUM_ORE =
            registerOre("cluster_titanium_ore", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.STONE)), Tab.BLOCK,
                    "iron","pickaxe");

    /** 铜矿簇。 */
    public static final RegistryObject<Block> CLUSTER_COPPER_ORE =
            registerOre("cluster_copper_ore", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.STONE)), Tab.BLOCK,
                    "iron","pickaxe");

    /** 深层铁矿簇。 */
    public static final RegistryObject<Block> CLUSTER_DEPTH_IRON_ORE =
            registerOre("cluster_depth_iron_ore", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.STONE)), Tab.BLOCK,
                    "iron","pickaxe");

    /** 深层钛矿簇。 */
    public static final RegistryObject<Block> CLUSTER_DEPTH_TITANIUM_ORE =
            registerOre("cluster_depth_titanium_ore", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.STONE)), Tab.BLOCK,
                    "iron","pickaxe");

    /** 深层钨矿簇。 */
    public static final RegistryObject<Block> CLUSTER_DEPTH_TUNGSTEN_ORE =
            registerOre("cluster_depth_tungsten_ore", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F,10.0F)
                    .sound(SoundType.STONE)), Tab.BLOCK,
                    "iron","pickaxe");

    /**
     * 为指定矿石注册对应的方块物品。
     *
     * @param name  矿石的注册名，将作为物品的注册名
     * @param block 目标方块
     * @param <T>   方块类型
     * @return 注册得到的方块物品
     */
    private static <T extends Block> RegistryObject<Item> registerOreBlockItems(String name, RegistryObject<T> block) {
        return modItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    /**
     * 注册一个矿石并完成配套工作：注册方块物品、归入创造模式物品栏、登记挖掘标签。
     *
     * @param name    矿石的注册名
     * @param block   矿石实例的构造器
     * @param tab     归属的创造模式物品栏
     * @param tagType 挖掘标签标识，可用取值见 {@link currkill.hbms_ntm_pp.tag.modTags}
     * @param <T>     方块类型
     * @return 注册得到的矿石方块
     */
    private static <T extends Block> RegistryObject<T> registerOre(String name, Supplier<T> block, Tab tab,String... tagType) {
        RegistryObject<T> blocks = BLOCKS.register(name, block);
        RegistryObject<Item> blockitem = registerOreBlockItems(name, blocks);
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
