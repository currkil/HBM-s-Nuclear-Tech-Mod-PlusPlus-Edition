package currkill.hbms_ntm_pp.item;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
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
 * 向 {@link #registerOreItem(String, int...)} 传入材料名和一组形态编号，即可一次性注册该材料的
 * 一整套物品，命名规则为 {@code <材料名>_<形态后缀>}。例如
 * {@code registerOreItem("steel", 1, 2, 3)} 会注册出 {@code steel_ingot}、{@code steel_plate}、
 * {@code steel_powder}。
 * <p>
 * <b>形态编号对照表：</b>
 * <ul>
 *   <li>{@code 1} → {@code ingot}：锭</li>
 *   <li>{@code 2} → {@code plate}：板</li>
 *   <li>{@code 3} → {@code powder}：粉</li>
 *   <li>{@code 4}：粒（已在 {@link #createItem(int)} 中占位，但尚未在 {@link #getTypeSuffix(int)} 登记后缀）</li>
 *   <li>{@code 5}：电路板（同上，占位但未登记后缀）</li>
 *   <li>{@code 6} → {@code wire_fine}：细线</li>
 *   <li>{@code 7} → {@code cast_plate}：铸造板</li>
 *   <li>{@code 8} → {@code weld_plate}：焊接板</li>
 *   <li>{@code 9} → {@code shell}：壳</li>
 *   <li>{@code 10} → {@code pipe}：管</li>
 *   <li>{@code 11} → {@code tiny_powder}：小撮粉</li>
 *   <li>{@code 20} / {@code 21} / {@code 22} / {@code 23} → {@code pickaxe} / {@code axe} /
 *       {@code shovel} / {@code hoe}：镐、斧、锹、锄四件套工具</li>
 * </ul>
 * 未登记的编号会得到后缀 {@code ERROR_item}，并生成一个占位物品。
 *
 * @author currkill-deepseek
 */
public class modOreItem {

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
        registerOreItem("steel", 1, 2, 3, 11, 20, 21, 22, 23);
    }

    /**
     * 按形态编号批量注册指定材料的物品，注册到 {@link #OREITEMS}，并统一归入 {@code part} 物品栏
     * （工具类形态会被 {@link #getTabForType(int, String)} 改派到其他物品栏）。
     *
     * @param material 材料名，将作为物品名的前缀
     * @param types    要注册的形态编号，含义见类文档
     */
    public static void registerOreItem(String material, int... types) {
        for (int type : types) {
            String name = material + "_" + getTypeSuffix(type);
            RegistryObject<Item> item = OREITEMS.register(name, () -> createItem(type));
            String targetTab = getTabForType(type, "part");
            addItemToTab(item, targetTab);
            if(Objects.equals(material, "steel") && type==1) STEEL_INGOT=item;
        }
    }

    /**
     * 按形态编号批量注册指定材料的物品，注册到 {@link modItems#ITEMS}，并使用调用方指定物品栏。
     *
     * @param material 材料名，将作为物品名的前缀
     * @param tab      默认归属的创造模式物品栏标识
     * @param types    要注册的形态编号，含义见类文档
     */
    public static void registerOreItem(String material, String tab, int... types) {
        for (int type : types) {
            String name = material + "_" + getTypeSuffix(type);
            RegistryObject<Item> item = modItems.ITEMS.register(name, () -> createItem(type));
            String targetTab = getTabForType(type, tab);
            addItemToTab(item, targetTab);
            //if(Objects.equals(material, "steel") && type==1) STEEL_INGOT=item;
        }
    }

    /**
     * 根据形态编号决定物品应当归入的创造模式物品栏。
     * <p>
     * 目前只有工具类形态（{@code 20}~{@code 23}）会被改派到 {@code consumable}，其余一律使用默认栏位。
     *
     * @param type       形态编号
     * @param defaultTab 默认物品栏标识
     * @return 最终归属的物品栏标识
     */
    private static String getTabForType(int type, String defaultTab) {
        return switch (type) {
            case 20, 21, 22, 23 -> "consumable";
            // case 12 -> "weapon";   // 武器类型
            // case 13 -> "block";    // 方块类型
            default -> defaultTab;
        };
    }

    /**
     * 根据形态编号取得物品名后缀。
     *
     * @param type 形态编号，对照表见类文档
     * @return 对应的物品名后缀；未登记的编号返回 {@code ERROR_item}
     */
    private static String getTypeSuffix(int type) {
        return switch (type) {
            case 1 -> "ingot";
            case 2 -> "plate";
            case 3 -> "powder";
            //case 4 -> "";
            //case 5 -> "";
            case 6 -> "wire_fine";
            case 7 -> "cast_plate";
            case 8 -> "weld_plate";
            case 9 -> "shell";
            case 10 -> "pipe";
            case 11 -> "tiny_powder";
            case 20 -> "pickaxe";
            case 21 -> "axe";
            case 22 -> "shovel";
            case 23 -> "hoe";
            default -> "ERROR_item";
        };
    }

    /**
     * 根据形态编号创建对应的物品实例。
     * <p>
     * {@code 1}~{@code 11} 为普通材料物品；{@code 20}~{@code 23} 为钻石级工具（镐、斧、锹、锄），
     * 耐久统一为 500。形态编号的含义见类文档。
     *
     * @param type 形态编号
     * @return 对应的物品实例；未登记的编号返回一个普通占位物品
     */
    private static Item createItem(int type) {
        return switch (type) {
            case 1 ->
                    new Item(new Item.Properties());
            case 2 ->
                    new Item(new Item.Properties());
            case 3 ->
                    new Item(new Item.Properties());
            case 4 ->
                    new Item(new Item.Properties());
            case 5 ->
                    new Item(new Item.Properties());
            case 6 ->
                    new Item(new Item.Properties());
            case 7 ->
                    new Item(new Item.Properties());
            case 8 ->
                    new Item(new Item.Properties());
            case 9 ->
                    new Item(new Item.Properties());
            case 10 ->
                    new Item(new Item.Properties());
            case 11 ->
                    new Item(new Item.Properties());
            case 20 ->
                    new PickaxeItem(Tiers.DIAMOND, 1, -2.8F, new Item.Properties()
                            .stacksTo(1)
                            .durability(500));
            case 21 ->
                    new AxeItem(Tiers.DIAMOND, 5, -3.0F, new Item.Properties()
                            .stacksTo(1)
                            .durability(500));
            case 22 ->
                    new ShovelItem(Tiers.DIAMOND, 1.5F, -3.0F, new Item.Properties()
                            .stacksTo(1)
                            .durability(500));
            case 23 ->
                    new HoeItem(Tiers.DIAMOND, -2, -1.0F, new Item.Properties()
                            .stacksTo(1)
                            .durability(500));
            default -> new Item(new Item.Properties());
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
