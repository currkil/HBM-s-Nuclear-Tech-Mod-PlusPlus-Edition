package currkill.hbms_ntm_pp.generate;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

/**
 * Forge 数据生成的入口类。
 * <p>
 * 监听 {@link GatherDataEvent}，把本模组的数据生成器注册进 {@link DataGenerator}。
 * 目前只注册了服务端侧的 {@link forgeTagGenerate}（方块标签）。
 * <p>
 * 本类由 {@code @Mod.EventBusSubscriber} 自动挂到模组事件总线，无需手动注册。
 *
 * @author currkill
 */
@Mod.EventBusSubscriber(modid = Hbms_ntm_pp.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class forgeJsonGenerate {

    /**
     * 在数据生成阶段注册本模组的各个数据生成器。
     *
     * @param event 数据生成事件，用于取得生成器、输出目录与注册表查找上下文
     */
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(
                event.includeServer(),
                new forgeTagGenerate(packOutput, lookupProvider, existingFileHelper)
        );
    }
}
