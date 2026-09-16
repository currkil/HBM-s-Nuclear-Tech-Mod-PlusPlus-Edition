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

/**
 * 客户端 Mixin，用于给「炸弹」物品栏换上自定义背景。
 * <p>
 * 通过重定向 {@code CreativeModeInventoryScreen#renderBg} 中第一次
 * {@code GuiGraphics#blit} 调用实现：当被选中的物品栏是 {@link modCreativeModeTab#NUKE_TAB} 时
 * 绘制 {@link #NUKE_TAB_BG}，否则沿用原版背景。
 *
 * @author currkill
 */
@Mixin(CreativeModeInventoryScreen.class)
public class nuke_tab {

    //private static final Logger LOGGER = LogUtils.getLogger();

    /** 「炸弹」物品栏的自定义背景贴图。 */
    @SuppressWarnings("removal")
    private static final ResourceLocation NUKE_TAB_BG = new ResourceLocation("hbms_ntm_pp", "textures/gui/nuke_tab_background.png");

    /** 当前被选中的物品栏，由 {@link CreativeModeInventoryScreen} 提供。 */
    @Shadow
    private static CreativeModeTab selectedTab;

    /**
     * 重定向物品栏背景的绘制调用。
     * <p>
     * 选中的是「炸弹」物品栏时绘制自定义背景，否则调用原版背景绘制逻辑。
     *
     * @param graphics 绘制上下文
     * @param texture  原版调用准备使用的背景贴图，非炸弹物品栏时会被原样透传
     * @param x        绘制位置的 X 坐标
     * @param y        绘制位置的 Y 坐标
     * @param w        绘制宽度
     * @param h        绘制高度
     * @param u        贴图取样起点的 U 坐标
     * @param v        贴图取样起点的 V 坐标
     */
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
        if(selectedKey != null && selectedKey.equals(nuketabKey)) {
            graphics.blit(NUKE_TAB_BG, x, y,0, 0, 256, 256, 256, 256);
        }else {
            graphics.blit(selectedTab.getBackgroundLocation(), x, y, w, h, u, v);
        }
    }
}
