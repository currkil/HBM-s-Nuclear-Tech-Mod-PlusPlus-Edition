package currkill.hbms_ntm_pp;

import currkill.hbms_ntm_pp.block.modBlocks;
import currkill.hbms_ntm_pp.item.modItems;
import currkill.hbms_ntm_pp.item.modOreItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 创造模式物品栏的注册类，同时负责物品的分派。
 * <p>
 * 每个物品栏都对应一个静态 {@link List}，其它注册类通过
 * {@link #addItemToTab(RegistryObject, Tab)} 用 {@link Tab} 指定归属，
 * 物品栏在 {@code displayItems} 中再把 List 的内容整体输出。
 * <p>
 * 物品栏之间的显示顺序由 {@code withTabsBefore} 依次串联决定：
 * 资源和零件 → 机器项目和燃料 → 模板 → 矿石和方块 → 机器 → 炸弹 → 导弹和卫星 → 武器和炮塔 → 食物和装备。
 *
 * @author currkill-deepseek
 */
public class modCreativeModeTab {

    /** 创造模式物品栏的延迟注册器。 */
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Hbms_ntm_pp.MODID);

    /**
     * 把一组物品依次放入物品栏的输出中。
     *
     * @param output      物品栏的显示输出
     * @param itemObjects 待输出的物品注册对象
     */
    private static void disPlayAll(CreativeModeTab.Output output, List<RegistryObject<Item>> itemObjects) {
        for(RegistryObject<Item> Object : itemObjects) {
            output.accept(Object.get());
        }
    }

    /** 「资源和零件」物品栏的物品集合。 */
    public static final List<RegistryObject<Item>> PART_ITEMS = new ArrayList<>();

    /** 「机器项目和燃料」物品栏的物品集合。 */
    public static final List<RegistryObject<Item>> CONTROL_ITEMS = new ArrayList<>();

    /** 「模板」物品栏的物品集合。 */
    public static final List<RegistryObject<Item>> TEMPLATE_ITEMS = new ArrayList<>();

    /** 「矿石和方块」物品栏的物品集合。 */
    public static final List<RegistryObject<Item>> BLOCK_ITEMS = new ArrayList<>();

    /** 「机器」物品栏的物品集合。 */
    public static final List<RegistryObject<Item>> MACHINE_ITEMS = new ArrayList<>();

    /** 「炸弹」物品栏的物品集合。 */
    public static final List<RegistryObject<Item>> NUKE_ITEMS = new ArrayList<>();

    /** 「导弹和卫星」物品栏的物品集合。 */
    public static final List<RegistryObject<Item>> MISSILE_ITEMS = new ArrayList<>();

    /** 「武器和炮塔」物品栏的物品集合。 */
    public static final List<RegistryObject<Item>> WEAPON_ITEMS = new ArrayList<>();

    /** 「食物和装备」物品栏的物品集合。 */
    public static final List<RegistryObject<Item>> CONSUMABLE_ITEMS = new ArrayList<>();

    /**
     * 创造模式物品栏的标识。
     * <p>
     * 每个常量对应一个物品栏及其背后的物品集合。注册物品时用它指定归属，
     * 取代了原先易于写错的字符串标识。
     *
     * @author currkill-deepseek
     */
    public enum Tab {

        /** 「资源和零件」物品栏。 */
        PART,

        /** 「机器项目和燃料」物品栏。 */
        CONTROL,

        /** 「模板」物品栏。 */
        TEMPLATE,

        /** 「矿石和方块」物品栏。 */
        BLOCK,

        /** 「机器」物品栏。 */
        MACHINE,

        /** 「炸弹」物品栏。 */
        NUKE,

        /** 「导弹和卫星」物品栏。 */
        MISSILE,

        /** 「武器和炮塔」物品栏。 */
        WEAPON,

        /** 「食物和装备」物品栏。 */
        CONSUMABLE
    }

    /**
     * 把物品投入指定物品栏的物品集合。
     *
     * @param Object 待分派的物品注册对象
     * @param tab    目标物品栏
     */
    public static void addItemToTab(RegistryObject<Item> Object, Tab tab) {
        switch (tab) {
            case PART -> PART_ITEMS.add(Object);
            case CONTROL -> CONTROL_ITEMS.add(Object);
            case TEMPLATE -> TEMPLATE_ITEMS.add(Object);
            case BLOCK -> BLOCK_ITEMS.add(Object);
            case MACHINE -> MACHINE_ITEMS.add(Object);
            case NUKE -> NUKE_ITEMS.add(Object);
            case MISSILE -> MISSILE_ITEMS.add(Object);
            case WEAPON -> WEAPON_ITEMS.add(Object);
            case CONSUMABLE -> CONSUMABLE_ITEMS.add(Object);
        }
    }

    /** 「资源和零件」物品栏，图标为钢锭。 */
    public static final RegistryObject<CreativeModeTab> PARTS_TAB =
            CREATIVE_MODE_TABS.register("parts_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modOreItem.STEEL_INGOT.get()))
                    .title(Component.translatable("itemGroup.parts_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        disPlayAll(pOutput, PART_ITEMS);
                    })
                    .build());

    /** 「机器项目和燃料」物品栏，图标为钢钻头。 */
    public static final RegistryObject<CreativeModeTab> CONTROL_TAB =
            CREATIVE_MODE_TABS.register("control_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.DRILLBIT_STEEL.get()))
                    .title(Component.translatable("itemGroup.control_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        disPlayAll(pOutput, CONTROL_ITEMS);
                    }).withTabsBefore(PARTS_TAB.getKey())
                    .build());

    /** 「模板」物品栏，图标为机器模板文件夹，带搜索栏。 */
    public static final RegistryObject<CreativeModeTab> TEMPLATE_TAB =
            CREATIVE_MODE_TABS.register("template_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.TEMPLATE_FOLDER.get()))
                    .title(Component.translatable("itemGroup.template_tab"))
                    .withSearchBar()
                    .displayItems((pParameters, pOutput) -> {
                        disPlayAll(pOutput, TEMPLATE_ITEMS);
                    }).withTabsBefore(CONTROL_TAB.getKey())
                    .build());

    /** 「矿石和方块」物品栏，图标为钢块。 */
    public static final RegistryObject<CreativeModeTab> BLOCK_TAB =
            CREATIVE_MODE_TABS.register("block_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modBlocks.STEEL_BLOCK.get()))
                    .title(Component.translatable("itemGroup.block_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        disPlayAll(pOutput, BLOCK_ITEMS);
                    }).withTabsBefore(TEMPLATE_TAB.getKey())
                    .build());

    /** 「机器」物品栏。图标尚未确定，待首个机器实现后补上。 */
    public static final RegistryObject<CreativeModeTab> MACHINE_TAB =
            CREATIVE_MODE_TABS.register("machine_tab", () -> CreativeModeTab.builder()
                    //.icon(() -> new ItemStack(modItems.STEEL_INGOT.get()))
                    .title(Component.translatable("itemGroup.machine_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        disPlayAll(pOutput, MACHINE_ITEMS);
                    }).withTabsBefore(BLOCK_TAB.getKey())
                    .build());

    /** 「炸弹」物品栏，图标为点火器（胖子），背景贴图由 Mixin 单独替换。 */
    public static final RegistryObject<CreativeModeTab> NUKE_TAB =
            CREATIVE_MODE_TABS.register("nuke_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.MAN_IGNITER.get()))
                    .title(Component.translatable("itemGroup.nuke_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        disPlayAll(pOutput, NUKE_ITEMS);
                    }).withTabsBefore(MACHINE_TAB.getKey())
                    .build());

    /** 「导弹和卫星」物品栏，图标暂用异虫腺体。 */
    public static final RegistryObject<CreativeModeTab> MISSILE_TAB =
            CREATIVE_MODE_TABS.register("missile_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.GLYPHID_GLAND_EMPTY.get()))
                    .title(Component.translatable("itemGroup.missile_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        disPlayAll(pOutput, MISSILE_ITEMS);
                    }).withTabsBefore(NUKE_TAB.getKey())
                    .build());

    /** 「武器和炮塔」物品栏，图标暂用异虫腺体。 */
    public static final RegistryObject<CreativeModeTab> WEAPON_TAB =
            CREATIVE_MODE_TABS.register("weapon_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.GLYPHID_GLAND_EMPTY.get()))
                    .title(Component.translatable("itemGroup.weapon_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        disPlayAll(pOutput, WEAPON_ITEMS);
                    }).withTabsBefore(MISSILE_TAB.getKey())
                    .build());

    /** 「食物和装备」物品栏，图标为核子可乐。 */
    public static final RegistryObject<CreativeModeTab> CONSUMABLE_TAB =
            CREATIVE_MODE_TABS.register("consumable_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.BOTTLE_NUKA.get()))
                    .title(Component.translatable("itemGroup.consumable_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        disPlayAll(pOutput, CONSUMABLE_ITEMS);
                    }).withTabsBefore(WEAPON_TAB.getKey())
                    .build());


    /**
     * 把本类持有的物品栏注册器挂到模组事件总线上。
     *
     * @param eventBus 模组事件总线
     */
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
