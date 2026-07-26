package currkill.hbms_ntm_pp.item;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

import static currkill.hbms_ntm_pp.modCreativeModeTab.addItemToTab;

public class modOreItem {

    public static final DeferredRegister<Item> OREITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Hbms_ntm_pp.MODID);
    public static RegistryObject<Item> STEEL_INGOT = null;

    public static void init() {
        if (STEEL_INGOT != null) return;
        registerOreItem("steel", 1, 2, 3, 11, 20, 21, 22, 23);
    }

    public static void registerOreItem(String material, int... types) {
        for (int type : types) {
            String name = material + "_" + getTypeSuffix(type);
            RegistryObject<Item> item = OREITEMS.register(name, () -> createItem(type));
            String targetTab = getTabForType(type, "part");
            addItemToTab(item, targetTab);
            if(Objects.equals(material, "steel") && type==1) STEEL_INGOT=item;
        }
    }
    public static void registerOreItem(String material, String tab, int... types) {
        for (int type : types) {
            String name = material + "_" + getTypeSuffix(type);
            RegistryObject<Item> item = modItems.ITEMS.register(name, () -> createItem(type));
            String targetTab = getTabForType(type, tab);
            addItemToTab(item, targetTab);
            //if(Objects.equals(material, "steel") && type==1) STEEL_INGOT=item;
        }
    }

    private static String getTabForType(int type, String defaultTab) {
        return switch (type) {
            case 20, 21, 22, 23 -> "consumable";  // 工具
            // case 12 -> "weapon";   // 武器类型
            // case 13 -> "block";    // 方块类型
            default -> defaultTab;
        };
    }

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
            default -> "ERROR_item"; // 错误物品
        };
    }

    private static Item createItem(int type) {
        return switch (type) {
            case 1 -> // 锭 ingot / ingot
                    new Item(new Item.Properties());
            case 2 -> // 板 plate
                    new Item(new Item.Properties());
            case 3 -> // 粉 powder / dust
                    new Item(new Item.Properties());
            case 4 -> // 粒
                    new Item(new Item.Properties());
            case 5 -> // 电路板
                    new Item(new Item.Properties());
            case 6 -> // 线 wire fine / wireFine
                    new Item(new Item.Properties());
            case 7 -> // 铸造板 cast plate
                    new Item(new Item.Properties());
            case 8 -> // 焊接板 weld plate
                    new Item(new Item.Properties());
            case 9 -> // 壳 shell / shell
                    new Item(new Item.Properties());
            case 10 -> // 管 pipe / pipe
                    new Item(new Item.Properties());
            case 11 -> // 小撮粉 tiny powder / dustTiny
                    new Item(new Item.Properties());
            case 20 -> // 镐 pickaxe
                    new PickaxeItem(Tiers.DIAMOND, 1, -2.8F, new Item.Properties()
                            .stacksTo(1)
                            .durability(500));
            case 21 -> // 斧 axe
                    new AxeItem(Tiers.DIAMOND, 5, -3.0F, new Item.Properties()
                            .stacksTo(1)
                            .durability(500));
            case 22 -> // 锹 shovel
                    new ShovelItem(Tiers.DIAMOND, 1.5F, -3.0F, new Item.Properties()
                            .stacksTo(1)
                            .durability(500));
            case 23 -> // 锄 hoe
                    new HoeItem(Tiers.DIAMOND, -2, -1.0F, new Item.Properties()
                            .stacksTo(1)
                            .durability(500));
            default -> new Item(new Item.Properties()); // 错误物品
        };
    }

    //static {
    //    //registerOreItem("steel",1,2,3,5,6,7,8,9,10,20,21,22,23);
    //     registerOreItem("steel",1,2,3,11,20,21,22,23);
    //}

    public static void register(IEventBus eventBus) {
        OREITEMS.register(eventBus);
    }
}
