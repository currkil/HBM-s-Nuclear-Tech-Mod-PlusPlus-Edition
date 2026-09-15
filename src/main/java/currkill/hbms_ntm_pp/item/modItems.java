package currkill.hbms_ntm_pp.item;

import currkill.hbms_ntm_pp.item.foodItems.modColaItem;
import currkill.hbms_ntm_pp.item.foodItems.modOpenerItem;
import currkill.hbms_ntm_pp.modCreativeModeTab.Tab;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import static currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters.REGISTRY;

/**
 * 常规物品的注册类。
 * <p>
 * 只负责注册不属于「材料 × 形态」体系的零散物品；钢锭、钢板这类按材料批量生成的物品由
 * {@link modOreItem} 统一处理。
 * <p>
 * 所有注册动作都由 {@link currkill.hbms_ntm_pp.registryBuilder.modRegistryBuilder} 的流式构造器完成，
 * 物品栏归属直接用 {@link Tab} 在链式调用里指定。
 *
 * @author currkill-deepseek
 */
public class modItems {

    //资源和零件 Resources and Parts
    //public static final RegistryObject<Item> STEEL_INGOT =
    //        ITEMS.register("steel_ingot", ()-> new Item(new Item.Properties()));//钢锭Steel,ingotSteel

    /** 钢钻头，属于机器类零件，不可堆叠。 */
    public static final RegistryObject<Item> DRILLBIT_STEEL =
            REGISTRY.item("drillbit_steel")
                    .stacksTo(1)
                    .tab(Tab.CONTROL)
                    .register();

    /** 机器模板文件夹，用于存放机器模板配置。 */
    public static final RegistryObject<Item> TEMPLATE_FOLDER =
            REGISTRY.item("template_folder")
                    .tab(Tab.TEMPLATE)
                    .register();

    /** 点火器（胖子），核弹起爆所需的引爆装置。 */
    public static final RegistryObject<Item> MAN_IGNITER =
            REGISTRY.item("booms/man_igniter")
                    .tab(Tab.NUKE)
                    .register();

    /** 异虫腺体，导弹与卫星相关材料。 */
    public static final RegistryObject<Item> GLYPHID_GLAND_EMPTY =
            REGISTRY.item("glyphid_gland_empty")
                    .tab(Tab.MISSILE)
                    .register();

    /** 核子可乐，需配合开瓶器饮用，详见 {@link modColaItem}。 */
    public static final RegistryObject<Item> BOTTLE_NUKA =
            REGISTRY.item("bottle_nuka")
                    .factory(modColaItem::new)
                    .food(new FoodProperties.Builder()
                            .nutrition(0)
                            .saturationMod(0f)
                            .alwaysEat()
                            .build())
                    .tab(Tab.CONSUMABLE)
                    .register();

    /** HBM 自制开瓶器，兼具近战能力，详见 {@link modOpenerItem}。 */
    public static final RegistryObject<Item> BOTTLE_OPENER =
            REGISTRY.item("bottle_opener")
                    .factory(p -> new modOpenerItem(p, 4.5F))
                    .tab(Tab.CONSUMABLE)
                    .register();

    /** 空可乐瓶，饮用核子可乐后返还。 */
    public static final RegistryObject<Item> BOTTLE_EMPTY =
            REGISTRY.item("bottle_empty")
                    .tab(Tab.CONSUMABLE)
                    .register();

    /** 核子可乐瓶盖，饮用核子可乐后返还。 */
    public static final RegistryObject<Item> CAP_NUKA =
            REGISTRY.item("cap_nuka")
                    .tab(Tab.CONSUMABLE)
                    .register();

    /**
     * 触发本类的静态初始化，使类中的流式构造器执行注册。
     * <p>
     * 由 {@link currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters#register_all} 调用，
     * 无需在别处重复调用。
     */
    public static void load() {
    }
}
