package currkill.hbms_ntm_pp.item.foodItems;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

import static net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE;

/**
 * HBM 自制开瓶器的物品类。
 * <p>
 * 除了作为饮用核子可乐所需的工具，它本身也是一件武器：主手持有时会提供额外的攻击伤害，
 * 命中目标时还有概率附加负面效果。
 *
 * @author currkill-deepseek
 */
public class modOpenerItem extends Item {

    /** 判定是否触发负面效果、以及抽取具体效果所用的随机数发生器。 */
    private static final Random RANDOM = new Random();

    /** 本物品在主手时提供的额外攻击伤害。 */
    private final float attackDamage;

    /** 本物品在主手时使用的属性修饰符集合。 */
    private final ImmutableMultimap<Attribute, AttributeModifier> deafaultModifiers;

    /**
     * 构造开瓶器物品。
     *
     * @param pProperties  物品属性
     * @param attackDamage 主手持有时提供的额外攻击伤害
     */
    public modOpenerItem(Properties pProperties,float attackDamage) {
        super(pProperties);
        this.attackDamage = attackDamage;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(ATTACK_DAMAGE,
                new AttributeModifier(BASE_ATTACK_DAMAGE_UUID,"Weapon modifier",
                        this.attackDamage, AttributeModifier.Operation.ADDITION));
        this.deafaultModifiers = builder.build();
    }

    /**
     * 获取本物品在指定装备槽位下提供的属性修饰符。
     *
     * @param slot  装备槽位
     * @param stack 物品堆叠
     * @return 主手时返回本物品的攻击伤害修饰符，其余槽位沿用父类实现
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.deafaultModifiers;
        }
        return super.getAttributeModifiers(slot, stack);
    }

    /**
     * 命中敌人时触发。
     * <p>
     * 先在攻击者所在位置播放铁砧音效；若攻击者是玩家，则有七分之一的概率给目标附加一个随机负面效果，
     * 候选效果及其时长如下：
     * <ul>
     *   <li>{@link MobEffects#BLINDNESS} 失明，5 分钟</li>
     *   <li>{@link MobEffects#MOVEMENT_SLOWDOWN} 缓慢 III，5 分钟</li>
     *   <li>{@link MobEffects#DIG_SLOWDOWN} 挖掘疲劳 III，5 分钟</li>
     *   <li>{@link MobEffects#CONFUSION} 反胃，1 分钟</li>
     * </ul>
     *
     * @param pStack    本物品的堆叠
     * @param pTarget   被攻击的目标
     * @param pAttacker 攻击者
     * @return 父类实现的结果
     */
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
                int duration = 5 * 60 * 20;
                MobEffectInstance effect = switch (effectIndex) {
                    case 0 -> new MobEffectInstance(MobEffects.BLINDNESS, duration, 0);
                    case 1 -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, 2);
                    case 2 -> new MobEffectInstance(MobEffects.DIG_SLOWDOWN, duration, 2);
                    case 3 -> new MobEffectInstance(MobEffects.CONFUSION, 60 * 20, 0);
                    default -> null;
                };

                if (effect != null) {
                    pTarget.addEffect(effect);
                }
            }
        }

        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    /**
     * 本物品不可损坏，因此不会消耗耐久。
     *
     * @param stack 物品堆叠
     * @return 恒为 {@code false}
     */
    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
