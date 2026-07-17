package currkill.hbms_ntm_pp;

import currkill.hbms_ntm_pp.block.modBlocks;
import currkill.hbms_ntm_pp.item.modItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

//创建创造模式物品栏
public class modCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Hbms_ntm_pp.MODID);

    //资源和零件 Resources and Parts
    public static final RegistryObject<CreativeModeTab> PARTS_TAB =
            CREATIVE_MODE_TABS.register("parts_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.STEEL_INGOT.get()))
                    .title(Component.translatable("itemGroup.parts_tab"))
                    .displayItems((pParameters, pOutput) -> {//物品列表
                        pOutput.accept(modItems.STEEL_INGOT.get());
                    })
                    .build());
    //机器项目和燃料 Machine Items and Fuel
    public static final RegistryObject<CreativeModeTab> CONTROL_TAB =
            CREATIVE_MODE_TABS.register("control_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.DRILLBIT_STEEL.get()))
                    .title(Component.translatable("itemGroup.control_tab"))
                    .displayItems((pParameters, pOutput) -> {//物品列表
                        pOutput.accept(modItems.DRILLBIT_STEEL.get());
                    }).withTabsBefore(PARTS_TAB.getKey())
                    .build());
    //模板 Template
    public static final RegistryObject<CreativeModeTab> TEMPLATE_TAB =
            CREATIVE_MODE_TABS.register("template_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.TEMPLATE_FOLDER.get()))
                    .title(Component.translatable("itemGroup.template_tab"))
                    .displayItems((pParameters, pOutput) -> {//物品列表
                        pOutput.accept(modItems.TEMPLATE_FOLDER.get());
                    }).withTabsBefore(CONTROL_TAB.getKey())
                    .build());
    //矿石和方块 Ores and Blocks
    public static final RegistryObject<CreativeModeTab> BLOCK_TAB =
            CREATIVE_MODE_TABS.register("block_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modBlocks.STEEL_BLOCK.get()))
                    .title(Component.translatable("itemGroup.block_tab"))
                    .displayItems((pParameters, pOutput) -> {//物品列表
                        pOutput.accept(modBlocks.STEEL_BLOCK.get());
                    }).withTabsBefore(TEMPLATE_TAB.getKey())
                    .build());
    //机器 Machines
    public static final RegistryObject<CreativeModeTab> MACHINE_TAB =
            CREATIVE_MODE_TABS.register("machine_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.STEEL_INGOT.get()))
                    .title(Component.translatable("itemGroup.machine_tab"))
                    .displayItems((pParameters, pOutput) -> {//物品列表
                        //pOutput.accept(modItems.STEEL_INGOT.get());
                    }).withTabsBefore(BLOCK_TAB.getKey())
                    .build());
    //炸弹 Bombs
    public static final RegistryObject<CreativeModeTab> NUKE_TAB =
            CREATIVE_MODE_TABS.register("nuke_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.MAN_IGNITER.get()))
                    .title(Component.translatable("itemGroup.nuke_tab"))
                    .displayItems((pParameters, pOutput) -> {//物品列表
                        pOutput.accept(modItems.MAN_IGNITER.get());
                    }).withTabsBefore(MACHINE_TAB.getKey())
                    .build());
    //导弹和卫星 Missiles and Satellites
    public static final RegistryObject<CreativeModeTab> MISSILE_TAB =
            CREATIVE_MODE_TABS.register("missile_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.STEEL_INGOT.get()))
                    .title(Component.translatable("itemGroup.missile_tab"))
                    .displayItems((pParameters, pOutput) -> {//物品列表
                        //pOutput.accept(modItems.STEEL_INGOT.get());
                    }).withTabsBefore(NUKE_TAB.getKey())
                    .build());
    //武器和炮塔 Weapons and Turrets
    public static final RegistryObject<CreativeModeTab> WEAPON_TAB =
            CREATIVE_MODE_TABS.register("weapon_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.STEEL_INGOT.get()))
                    .title(Component.translatable("itemGroup.weapon_tab"))
                    .displayItems((pParameters, pOutput) -> {//物品列表
                        //pOutput.accept(modItems.STEEL_INGOT.get());
                    }).withTabsBefore(MISSILE_TAB.getKey())
                    .build());
    //食物和装备 Consumables and Gears
    public static final RegistryObject<CreativeModeTab> CONSUMABLE_TAB =
            CREATIVE_MODE_TABS.register("consumable_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(modItems.BOTTLE_NUKA.get()))
                    .title(Component.translatable("itemGroup.consumable_tab"))
                    .displayItems((pParameters, pOutput) -> {//物品列表
                        pOutput.accept(modItems.BOTTLE_NUKA.get());
                        pOutput.accept(modItems.BOTTLE_EMPTY.get());
                        pOutput.accept(modItems.BOTTLE_OPENER.get());
                        pOutput.accept(modItems.CAP_NUKA.get());
                    }).withTabsBefore(WEAPON_TAB.getKey())
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
