package currkill.hbms_ntm_pp.item;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
import currkill.hbms_ntm_pp.modCreativeModeTab.Tab;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

import static currkill.hbms_ntm_pp.modCreativeModeTab.addItemToTab;
import static currkill.hbms_ntm_pp.tag.modTags.addBlockToTag;

/**
 * 「材料 × 形态」物品的批量注册器。
 * <p>
 * 向 {@link #registerOreItem(String, OreItemType...)} 传入材料名和一组 {@link OreItemType}，
 * 即可一次性注册该材料的一整套物品，命名规则为 {@code <材料名>_<形态后缀>}。例如
 * {@code registerOreItem("steel", OreItemType.INGOT, OreItemType.PLATE)} 会注册出
 * {@code steel_ingot} 与 {@code steel_plate}。
 * <p>
 * 形态本身由 {@link OreItemType} 枚举描述，取代了原先 1=锭、2=板 之类的裸数字编号。
 *
 * @author currkill-deepseek
 */
public class modOreItem {

    /**
     * 材料的物品形态。
     * <p>
     * 每个常量携带该形态的物品名后缀，注册名即为 {@code <材料名>_<后缀>}。
     * <p>
     * 尚未登记后缀：粒（原编号 4）与电路板（原编号 5），待实现后再补上对应常量。
     *
     * @author currkill-deepseek
     */
    public enum OreItemType {

        /** 锭。 */
        INGOT("ingot"),

        /** 板。 */
        PLATE("plate"),

        /** 粉。 */
        POWDER("powder"),
        //case 4 -> "";
        //case 5 -> "";

        /** 细线。 */
        WIRE_FINE("wire_fine"),

        /** 铸造板。 */
        CAST_PLATE("cast_plate"),

        /** 焊接板。 */
        WELD_PLATE("weld_plate"),

        /** 壳。 */
        SHELL("shell"),

        /** 管。 */
        PIPE("pipe"),

        /** 小撮粉。 */
        TINY_POWDER("tiny_powder"),

        /** 镐。 */
        PICKAXE("pickaxe"),

        /** 斧。 */
        AXE("axe"),

        /** 锹。 */
        SHOVEL("shovel"),

        /** 锄。 */
        HOE("hoe");

        /** 物品名后缀，注册名格式为 {@code <材料名>_<后缀>}。 */
        private final String suffix;

        /**
         * 构造形态常量。
         *
         * @param suffix 该形态的物品名后缀
         */
        OreItemType(String suffix) {
            this.suffix = suffix;
        }

        /**
         * 获取该形态的物品名后缀。
         *
         * @return 物品名后缀，注册名格式为 {@code <材料名>_<后缀>}
         */
        public String getSuffix() {
            return suffix;
        }
    }

    /** 材料类物品的延迟注册器。 */
    public static final DeferredRegister<Item> OREITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Hbms_ntm_pp.MODID);

    /** 钢锭。除了作为普通材料，还被用作「资源和零件」创造模式物品栏的图标。 */
    public static RegistryObject<Item> STEEL_INGOT = null;

    /**
     * 注册当前已启用的材料物品。
     * <p>
     * 目前只注册钢（{@code steel}）这一种材料。本方法是幂等的：若 {@link #STEEL_INGOT} 已存在
     * 则直接返回，避免重复注册。
     */
    public static void init() {
        if (STEEL_INGOT != null) return;
        registerOreItem("steel",
                OreItemType.INGOT, OreItemType.PLATE, OreItemType.POWDER, OreItemType.TINY_POWDER,
                OreItemType.PICKAXE, OreItemType.AXE, OreItemType.SHOVEL, OreItemType.HOE);
    }

    /**
     * 批量注册指定材料的物品，注册到 {@link #OREITEMS}，并统一归入
     * {@link Tab#PART} 物品栏（工具类形态会被 {@link #getTabForType(OreItemType, Tab)} 改派）。
     *
     * @param material 材料名，将作为物品名的前缀
     * @param types    要注册的形态
     */
    public static void registerOreItem(String material, OreItemType... types) {
        for (OreItemType type : types) {
            String name = material + "_" + type.getSuffix();
            RegistryObject<Item> item = OREITEMS.register(name, () -> createItem(type));
            Tab targetTab = getTabForType(type, Tab.PART);
            addItemToTab(item, targetTab);
            if(Objects.equals(material, "steel") && type == OreItemType.INGOT) STEEL_INGOT=item;
        }
    }

    /**
     * 批量注册指定材料的物品，注册到 {@link modItems#ITEMS}，并使用调用方指定的默认物品栏。
     *
     * @param material 材料名，将作为物品名的前缀
     * @param tab      默认归属的创造模式物品栏
     * @param types    要注册的形态
     */
    public static void registerOreItem(String material, Tab tab, OreItemType... types) {
        for (OreItemType type : types) {
            String name = material + "_" + type.getSuffix();
            RegistryObject<Item> item = modItems.ITEMS.register(name, () -> createItem(type));
            Tab targetTab = getTabForType(type, tab);
            addItemToTab(item, targetTab);
            //if(Objects.equals(material, "steel") && type==1) STEEL_INGOT=item;
        }
    }

    /**
     * 根据形态决定物品应当归入的创造模式物品栏。
     * <p>
     * 目前只有工具类形态（镐、斧、锹、锄）会被改派到 {@link Tab#CONSUMABLE}，其余一律使用默认物品栏。
     * <p>
     * 备注：注释掉的两行属于重构前的数字编号方案（原编号 12 为武器、13 为方块），
     * 待这两种形态以枚举常量形式实现后再放开。
     *
     * @param type       形态
     * @param defaultTab 默认物品栏
     * @return 最终归属的物品栏
     */
    private static Tab getTabForType(OreItemType type, Tab defaultTab) {
        return switch (type) {
            case PICKAXE, AXE, SHOVEL, HOE -> Tab.CONSUMABLE;
            // case 12 -> "weapon";   // 武器类型
            // case 13 -> "block";    // 方块类型
            default -> defaultTab;
        };
    }

    /**
     * 根据形态创建对应的物品实例。
     * <p>
     * 材料类形态（锭、板、粉、细线、铸造板、焊接板、壳、管、小撮粉）为普通物品；
     * 工具类形态（镐、斧、锹、锄）为钻石级工具，耐久统一为 500。
     *
     * @param type 形态
     * @return 对应的物品实例
     */
    private static Item createItem(OreItemType type) {
        return switch (type) {
            case INGOT, PLATE, POWDER, WIRE_FINE, CAST_PLATE, WELD_PLATE, SHELL, PIPE, TINY_POWDER ->
                    new Item(new Item.Properties());
            case PICKAXE ->
                    new PickaxeItem(Tiers.DIAMOND, 1, -2.8F, new Item.Properties()
                            .stacksTo(1)
                            .durability(500));
            case AXE ->
                    new AxeItem(Tiers.DIAMOND, 5, -3.0F, new Item.Properties()
                            .stacksTo(1)
                            .durability(500));
            case SHOVEL ->
                    new ShovelItem(Tiers.DIAMOND, 1.5F, -3.0F, new Item.Properties()
                            .stacksTo(1)
                            .durability(500));
            case HOE ->
                    new HoeItem(Tiers.DIAMOND, -2, -1.0F, new Item.Properties()
                            .stacksTo(1)
                            .durability(500));
        };
    }

    //static {
    //    //registerOreItem("steel",1,2,3,5,6,7,8,9,10,20,21,22,23);
    //     registerOreItem("steel",1,2,3,11,20,21,22,23);
    //}

    /**
     * 把本类持有的物品注册器挂到模组事件总线上。
     *
     * @param eventBus 模组事件总线
     */
    public static void register(IEventBus eventBus) {
        OREITEMS.register(eventBus);
    }
}
