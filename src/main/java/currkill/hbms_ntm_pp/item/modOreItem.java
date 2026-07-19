package currkill.hbms_ntm_pp.item;

import net.minecraft.world.item.*;
import net.minecraftforge.registries.RegistryObject;

import static currkill.hbms_ntm_pp.modCreativeModeTab.addItemToTab;

public class modOreItem {
    public static void registerOreItem(String material, int... types) {
        for (int type : types) {
            String name = material + "_" + getTypeSuffix(type);
            RegistryObject<Item> item = modItems.ITEMS.register(name, () -> createItem(type));
            String targetTab = getTabForType(type, "part");
            addItemToTab(item, targetTab);
        }
    }
    public static void registerOreItem(String material, String tab, int... types) {
        for (int type : types) {
            String name = material + "_" + getTypeSuffix(type);
            RegistryObject<Item> item = modItems.ITEMS.register(name, () -> createItem(type));
            String targetTab = getTabForType(type, tab);
            addItemToTab(item, targetTab);
        }
    }

    private static String getTabForType(int type, String defaultTab) {
        return switch (type) {
            case 11 -> "consumable";  // 工具
            // case 12 -> "weapon";   // 武器类型
            // case 13 -> "block";    // 方块类型
            default -> defaultTab;
        };
    }

    private static String getTypeSuffix(int type) {
        return switch (type) {
            case 1 -> "plate";
            case 2 -> "powder";
            //case 3 -> "";
            //case 4 -> "";
            case 5 -> "wire_fine";
            case 6 -> "cast_plate";
            case 7 -> "weld_plate";
            case 8 -> "shell";
            case 9 -> "pipe";
            case 10 -> "tiny_powder";
            //case 11 -> "";
            default -> "ERROR_item";
        };
    }

    private static Item createItem(int type) {
        return switch (type) {
            case 1 -> // 板 plate
                    new Item(new Item.Properties());
            case 2 -> // 粉 powder / dust
                    new Item(new Item.Properties());
            case 3 -> // 粒
                    new Item(new Item.Properties());
            case 4 -> // 电路板
                    new Item(new Item.Properties());
            case 5 -> // 线 wire fine / wireFine
                    new Item(new Item.Properties());
            case 6 -> // 铸造板 cast plate
                    new Item(new Item.Properties());
            case 7 -> // 焊接板 weld plate
                    new Item(new Item.Properties());
            case 8 -> // 壳 shell / shell
                    new Item(new Item.Properties());
            case 9 -> // 管 pipe / pipe
                    new Item(new Item.Properties());
            case 10 -> // 小撮粉 tiny powder / dustTiny
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

    static {
        registerOreItem("steel",1,2,5,6,7,8,9,10,20,21,22,23);
    }
}
