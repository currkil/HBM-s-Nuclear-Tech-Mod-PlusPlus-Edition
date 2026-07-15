package currkill.hbms_ntm_pp.item;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class modItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Hbms_ntm_pp.MODID);

    //资源和零件 Resources and Parts
    public static final RegistryObject<Item> STEEL_INGOT =
            ITEMS.register("steel_ingot", ()-> new Item(new Item.Properties()));//钢锭Steel,ingotSteel
    //机器项目和燃料 Machine Items and Fuel
    public static final RegistryObject<Item> DRILLBIT_STEEL =
            ITEMS.register("drillbit_steel", ()-> new Item(new Item.Properties().stacksTo(1)));//钢钻头
    //模板 Template
    public static final RegistryObject<Item> TEMPLATE_FOLDER =
            ITEMS.register("template_folder", ()-> new Item(new Item.Properties()));//机器模板文件夹
    //炸弹 Bombs
    public static final RegistryObject<Item> MAN_IGNITER =
            ITEMS.register("booms/man_igniter", ()-> new Item(new Item.Properties()));//点火器(胖子)
    //食物和装备 Consumables and Gears
    public static final RegistryObject<Item> BOTTLE_NUKA =
            ITEMS.register("bottle_nuka", ()-> new modColaItem(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(0)
                    .saturationMod(0f)
                    .alwaysEat()
                    .build())));//核子可乐
    public static final RegistryObject<Item> BOTTLE_OPENER =
            ITEMS.register("bottle_opener", ()-> new modOpenerItem(
                    Tiers.IRON,
                    1,
                    -2.4F,
                    new Item.Properties().stacksTo(1)));//hbm自制开瓶器
    public static final RegistryObject<Item> BOTTLE_EMPTY =
            ITEMS.register("bottle_empty", ()-> new Item(new Item.Properties()));//空可乐瓶
    public static final RegistryObject<Item> CAP_NUKA =
            ITEMS.register("cap_nuka", ()-> new Item(new Item.Properties()));//核子可乐瓶盖

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
