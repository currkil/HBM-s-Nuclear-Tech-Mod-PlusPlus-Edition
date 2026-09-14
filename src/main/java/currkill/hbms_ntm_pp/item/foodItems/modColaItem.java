package currkill.hbms_ntm_pp.item.foodItems;

import currkill.hbms_ntm_pp.item.modItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * 核子可乐的物品类，负责处理饮用相关的逻辑。
 * <p>
 * 饮用时必须持有 {@link modItems#BOTTLE_OPENER}，否则只会给出提示且不消耗可乐。
 * 成功饮用后会给予移动速度与急迫效果，并返还空瓶与瓶盖。
 *
 * @author currkill-deepseek
 */
public class modColaItem  extends Item {

    /**
     * 构造核子可乐物品。
     *
     * @param pProperties 物品属性
     */
    public modColaItem(Properties pProperties) {
        super(pProperties);
    }

    /**
     * 玩家喝完核子可乐时触发。
     * <p>
     * 未持有开瓶器时向玩家发送提示，并保留原物品堆叠；持有开瓶器时给予 200 秒的移动速度 II
     * 与急迫 II，随后把空瓶与瓶盖塞进玩家背包，背包放不下则直接掉落到世界中。
     * <p>
     * 待办：核子可乐的辐射效果尚未实现，留待后续补上。
     *
     * @param pStack  被饮用的物品堆叠
     * @param pLevel  所在世界
     * @param pEntity 饮用者
     * @return 剩余物品堆叠；正常饮用完毕后返回 {@link ItemStack#EMPTY}
     */
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntity){
        if(pEntity instanceof Player player) {
            boolean hasOpener = player.getInventory().contains(new ItemStack(modItems.BOTTLE_OPENER.get()));

            if(!hasOpener) {
                player.displayClientMessage(Component.translatable("message.no_opener"), true);
                return pStack;
            }

            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200*20, 1));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200*20, 1));

            if(!player.getInventory().add(new ItemStack(modItems.BOTTLE_EMPTY.get()))){
                player.drop(new ItemStack(modItems.BOTTLE_EMPTY.get()),false);
            }
            if(!player.getInventory().add(new ItemStack(modItems.CAP_NUKA.get()))){
                player.drop(new ItemStack(modItems.CAP_NUKA.get()),false);
            }
        }
        return ItemStack.EMPTY;
    }

}
