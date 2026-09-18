package currkill.hbms_ntm_pp.block.machine;

import currkill.hbms_ntm_pp.modCreativeModeTab.Tab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import static currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters.BLOCK_ENTITY;
import static currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters.REGISTRY;

/**
 * 机器类方块及其方块实体的注册类。
 * <p>
 * 方块本身走通用的流式构造器，方块实体类型则注册到
 * {@link currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters#BLOCK_ENTITY}。
 * </p>
 *
 * @author currkill-deepseek
 */
public class modMachines {

    /** 创造模式HE发电机。 */
    public static final RegistryObject<Block> CREATIVE_ENERGY_GENERATOR =
            REGISTRY.block("creative_energy_generator")
                    .factory(CreativeEnergyGeneratorBlock::new)
                    .soundType(SoundType.METAL)
                    .strength(5.0F, 50.0F)
                    .simpleItem()
                    .tab(Tab.MACHINE)
                    .register();

    /** 创造模式HE能量表。 */
    public static final RegistryObject<Block> CREATIVE_ENERGY_METER =
            REGISTRY.block("creative_energy_meter")
                    .factory(CreativeEnergyMeterBlock::new)
                    .soundType(SoundType.METAL)
                    .strength(5.0F, 50.0F)
                    .simpleItem()
                    .tab(Tab.MACHINE)
                    .register();

    /** 创造模式HE发电机的方块实体类型。 */
    public static final RegistryObject<BlockEntityType<CreativeEnergyGeneratorBlockEntity>> CREATIVE_ENERGY_GENERATOR_BE =
            BLOCK_ENTITY.register("creative_energy_generator",
                    () -> BlockEntityType.Builder.of(CreativeEnergyGeneratorBlockEntity::new,
                            CREATIVE_ENERGY_GENERATOR.get()).build(null));

    /** 创造模式HE能量表的方块实体类型。 */
    public static final RegistryObject<BlockEntityType<CreativeEnergyMeterBlockEntity>> CREATIVE_ENERGY_METER_BE =
            BLOCK_ENTITY.register("creative_energy_meter",
                    () -> BlockEntityType.Builder.of(CreativeEnergyMeterBlockEntity::new,
                            CREATIVE_ENERGY_METER.get()).build(null));

    /**
     * 触发本类的静态初始化，使类中的流式构造器执行注册。
     * <p>
     * 由 {@link currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters#register_all} 调用，
     * 无需在别处重复调用。
     */
    public static void load() {
    }
}
