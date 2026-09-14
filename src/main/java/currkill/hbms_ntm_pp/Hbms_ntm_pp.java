package currkill.hbms_ntm_pp;

import com.mojang.logging.LogUtils;
import currkill.hbms_ntm_pp.block.modBlocks;
import currkill.hbms_ntm_pp.item.modItems;
import currkill.hbms_ntm_pp.block.modOres;
import currkill.hbms_ntm_pp.item.modOreItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(Hbms_ntm_pp.MODID)
public class Hbms_ntm_pp {
    public static final String MODID = "hbms_ntm_pp";
    private static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")//差点忘了你了
    public Hbms_ntm_pp() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //调用总线
        modOres.register(modEventBus);
        modItems.register(modEventBus);
        modOreItem.register(modEventBus);
        modBlocks.register(modEventBus);
        modCreativeModeTab.register(modEventBus);

        modOreItem.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

}
