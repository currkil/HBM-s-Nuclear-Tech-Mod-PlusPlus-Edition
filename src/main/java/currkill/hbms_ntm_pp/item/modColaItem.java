package currkill.hbms_ntm_pp.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

//处理可乐相关事件，使用开瓶器与效果相关
public class modColaItem  extends Item {
    public modColaItem(Properties pProperties) {
        super(pProperties);
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntity){
        if(pEntity instanceof Player player) {
            boolean hasOpener = player.getInventory().contains(new ItemStack(modItems.BOTTLE_OPENER.get()));

            if(!hasOpener) {
                player.displayClientMessage(Component.translatable("message.no_opener"), true);
                return pStack;
            }

            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200*20, 1));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200*20, 1));
            //预留辐射，后期制作

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
