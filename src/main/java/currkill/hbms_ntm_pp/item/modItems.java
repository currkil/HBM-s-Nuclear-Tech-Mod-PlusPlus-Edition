package currkill.hbms_ntm_pp.item;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
import currkill.hbms_ntm_pp.item.foodItems.modColaItem;
import currkill.hbms_ntm_pp.item.foodItems.modOpenerItem;
import currkill.hbms_ntm_pp.modCreativeModeTab.Tab;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static currkill.hbms_ntm_pp.modCreativeModeTab.addItemToTab;

/**
 * 常规物品的注册类。
 * <p>
 * 只负责注册不属于「材料 × 形态」体系的零散物品；钢锭、钢板这类按材料批量生成的物品由
 * {@link modOreItem} 统一处理。
 * <p>
 * 物品注册完成后，由类末尾的静态初始化块通过
 * {@link currkill.hbms_ntm_pp.modCreativeModeTab#addItemToTab} 把它们分派到对应的创造模式物品栏。
 *
 * @author currkill-deepseek
 */
public class modItems {

    /** 常规物品的延迟注册器。 */
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Hbms_ntm_pp.MODID);

    //资源和零件 Resources and Parts
    //public static final RegistryObject<Item> STEEL_INGOT =
    //        ITEMS.register("steel_ingot", ()-> new Item(new Item.Properties()));//钢锭Steel,ingotSteel

    /** 钢钻头，属于机器类零件，不可堆叠。 */
    public static final RegistryObject<Item> DRILLBIT_STEEL =
            ITEMS.register("drillbit_steel", ()-> new Item(new Item.Properties().stacksTo(1)));

    /** 机器模板文件夹，用于存放机器模板配置。 */
    public static final RegistryObject<Item> TEMPLATE_FOLDER =
            ITEMS.register("template_folder", ()-> new Item(new Item.Properties()));

    /** 点火器（胖子），核弹起爆所需的引爆装置。 */
    public static final RegistryObject<Item> MAN_IGNITER =
            ITEMS.register("booms/man_igniter", ()-> new Item(new Item.Properties()));

    /** 异虫腺体，导弹与卫星相关材料。 */
    public static final RegistryObject<Item> GLYPHID_GLAND_EMPTY =
            ITEMS.register("glyphid_gland_empty", ()-> new Item(new Item.Properties()));

    /** 核子可乐，需配合开瓶器饮用，详见 {@link modColaItem}。 */
    public static final RegistryObject<Item> BOTTLE_NUKA =
            ITEMS.register("bottle_nuka", ()-> new modColaItem(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(0)
                    .saturationMod(0f)
                    .alwaysEat()
                    .build())));

    /** HBM 自制开瓶器，兼具近战能力，详见 {@link modOpenerItem}。 */
    public static final RegistryObject<Item> BOTTLE_OPENER =
            ITEMS.register("bottle_opener", ()-> new modOpenerItem(new Item.Properties(),4.5F));

    /** 空可乐瓶，饮用核子可乐后返还。 */
    public static final RegistryObject<Item> BOTTLE_EMPTY =
            ITEMS.register("bottle_empty", ()-> new Item(new Item.Properties()));

    /** 核子可乐瓶盖，饮用核子可乐后返还。 */
    public static final RegistryObject<Item> CAP_NUKA =
            ITEMS.register("cap_nuka", ()-> new Item(new Item.Properties()));

    static {
        //addItemToTab(STEEL_INGOT,"part");
        addItemToTab(DRILLBIT_STEEL, Tab.CONTROL);
        addItemToTab(TEMPLATE_FOLDER, Tab.TEMPLATE);
        addItemToTab(MAN_IGNITER, Tab.NUKE);
        addItemToTab(GLYPHID_GLAND_EMPTY, Tab.MISSILE);
        addItemToTab(BOTTLE_EMPTY, Tab.CONSUMABLE);
        addItemToTab(BOTTLE_NUKA, Tab.CONSUMABLE);
        addItemToTab(BOTTLE_OPENER, Tab.CONSUMABLE);
        addItemToTab(CAP_NUKA, Tab.CONSUMABLE);
    }

    /**
     * 把本类持有的物品注册器挂到模组事件总线上。
     *
     * @param eventBus 模组事件总线
     */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
