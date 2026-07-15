package currkill.hbms_ntm_pp.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import java.util.Random;

public class modOpenerItem extends SwordItem { // 1. 继承 Item
    private static final Random RANDOM = new Random();
    public modOpenerItem(Tier tier, int attackDamage, float attackSpeed, Properties pProperties) {
        super(tier, attackDamage, attackSpeed, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        Level level = pAttacker.level();

        if (!level.isClientSide) {
            level.playSound(null,
                    pAttacker.getX(), pAttacker.getY(), pAttacker.getZ(),
                    SoundEvents.ANVIL_LAND,
                    SoundSource.PLAYERS,
                    1.0f,
                    1.0f
            );
        }
        if (pAttacker instanceof Player) {
            if (RANDOM.nextInt(7) == 0) {
                int effectIndex = RANDOM.nextInt(4);
                int duration = 5 * 60 * 20; // 5分钟
                MobEffectInstance effect = null;

                switch (effectIndex) {
                    case 0:
                        effect = new MobEffectInstance(MobEffects.BLINDNESS, duration, 0);
                        break;
                    case 1:
                        effect = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, 2);
                        break;
                    case 2:
                        effect = new MobEffectInstance(MobEffects.DIG_SLOWDOWN, duration, 2);
                        break;
                    case 3:
                        effect = new MobEffectInstance(MobEffects.CONFUSION, 60 * 20, 0);
                        break;
                }

                if (effect != null) {
                    pTarget.addEffect(effect);
                }
            }
        }

        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}