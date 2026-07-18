package currkill.hbms_ntm_pp.mixin;

import currkill.hbms_ntm_pp.modCreativeModeTab;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

//更改nuke_tab的背景
@Mixin(CreativeModeInventoryScreen.class)
public class nuke_tab {
    //private static final Logger LOGGER = LogUtils.getLogger();
    @SuppressWarnings("removal")//哎呀老给我提示这个干啥
    private static final ResourceLocation NUKE_TAB_BG = new ResourceLocation("hbms_ntm_pp", "textures/gui/nuke_tab_background.png");

    @Shadow
    private static CreativeModeTab selectedTab;

    @Redirect(
            method = "renderBg",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V",
                    ordinal = 0
            )
    )
    private void redirectTabBackground(GuiGraphics graphics, ResourceLocation texture,int x, int y, int w, int h, int u, int v) {
        ResourceLocation selectedKey = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(selectedTab);
        ResourceLocation nuketabKey = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(modCreativeModeTab.NUKE_TAB.get());
        //我操了啊
        if(selectedKey != null && selectedKey.equals(nuketabKey)) {
            graphics.blit(NUKE_TAB_BG, x, y,0, 0, 256, 256, 256, 256);
        }else {
            graphics.blit(selectedTab.getBackgroundLocation(), x, y, w, h, u, v);
        }
        //为什么要这样子？？？？？？
    }
}
