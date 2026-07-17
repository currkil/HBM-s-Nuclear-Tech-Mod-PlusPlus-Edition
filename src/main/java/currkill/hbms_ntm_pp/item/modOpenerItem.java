package currkill.hbms_ntm_pp.item;

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

//开瓶器攻击相关
public class modOpenerItem extends Item {
    private static final Random RANDOM = new Random();
    private final float attackDamage;
    private final ImmutableMultimap<Attribute, AttributeModifier> deafaultModifiers;
    public modOpenerItem(Properties pProperties,float attackDamage) {
        super(pProperties);
        this.attackDamage = attackDamage;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(ATTACK_DAMAGE,
                new AttributeModifier(BASE_ATTACK_DAMAGE_UUID,"Weapon modifier",
                        this.attackDamage, AttributeModifier.Operation.ADDITION));
        this.deafaultModifiers = builder.build();
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.deafaultModifiers;
        }
        return super.getAttributeModifiers(slot, stack);
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
    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}