package currkill.hbms_ntm_pp.generate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 方块模型、方块物品模型与方块状态 JSON 的生成器。
 * <p>
 * 一次调用会同时写出三个文件，均位于 {@code src/main/resources/assets/hbms_ntm_pp/} 下：
 * <ul>
 *   <li>{@code models/block/<方块名>.json} —— 方块模型，贴图键为 {@code all}</li>
 *   <li>{@code models/item/<方块名>.json} —— 方块物品模型，直接继承方块模型</li>
 *   <li>{@code blockstates/<方块名>.json} —— 方块状态，使用空 variant（即无状态方块）</li>
 * </ul>
 * 贴图路径约定为 {@code hbms_ntm_pp:block/<方块名>}。
 * <p>
 * 注意：底层写出默认不覆盖已存在的文件，重复执行时需要 {@link modJsonWriter} 的覆盖模式。
 *
 * @author currkill-deepseek
 */
public class modBlockModelsAndItemModelsGenerate {

    /**
     * 生成方块全套模型，方块模型的父模型固定为 {@code block/cube_all}。
     *
     * @param itemName 方块名，同时作为各文件名与贴图名
     * @throws Exception 写文件失败时抛出
     */
    public static void generate(String itemName) throws Exception{

        Map<String, Object> blockModel = new HashMap<>();
        blockModel.put("parent","block/cube_all");

        Map<String, Object> modelVariants = new HashMap<>();
        modelVariants.put("model","hbms_ntm_pp:block/"+itemName);
        Map<String, Object> variants = new HashMap<>();
        variants.put("", modelVariants);
        Map<String, Object> blockStates = new HashMap<>();
        blockStates.put("variants", variants);

        Map<String, String> textures = new HashMap<>();
        textures.put("all","hbms_ntm_pp:block/" + itemName);
        blockModel.put("textures",textures);

        Map<String, Object> itemModel = new HashMap<>();
        itemModel.put("parent","hbms_ntm_pp:block/"+itemName);

        Path path = Paths.get("src/main/resources/assets/hbms_ntm_pp/models/block/" + itemName + ".json");
        boolean isBlockDone = modJsonWriter.write(path, blockModel);
        path = Paths.get("src/main/resources/assets/hbms_ntm_pp/models/item/" + itemName + ".json");
        boolean isItemDone = modJsonWriter.write(path, itemModel);
        path = Paths.get("src/main/resources/assets/hbms_ntm_pp/blockstates/" + itemName + ".json");
        boolean isBlockStateDone = modJsonWriter.write(path, blockStates);
        if(isBlockDone && isItemDone && isBlockStateDone) System.out.println("File written successfully: " + path);
        else System.out.println("Failed to write file: " + path);

    }

    /**
     * 生成方块全套模型，并指定方块模型的父模型。
     *
     * @param itemName    方块名，同时作为各文件名与贴图名
     * @param blockparent 方块模型的父模型，例如 {@code block/cube_column}
     * @throws Exception 写文件失败时抛出
     */
    public static void generate(String itemName,String blockparent) throws Exception{

        Map<String, Object> blockModel = new HashMap<>();
        blockModel.put("parent",blockparent);

        Map<String, Object> modelVariants = new HashMap<>();
        modelVariants.put("model","hbms_ntm_pp:block/"+itemName);
        Map<String, Object> variants = new HashMap<>();
        variants.put("", modelVariants);
        Map<String, Object> blockStates = new HashMap<>();
        blockStates.put("variants", variants);

        Map<String, String> textures = new HashMap<>();
        textures.put("all","hbms_ntm_pp:block/" + itemName);
        blockModel.put("textures",textures);

        Map<String, Object> itemModel = new HashMap<>();
        itemModel.put("parent","hbms_ntm_pp:block/"+itemName);

        Path path = Paths.get("src/main/resources/assets/hbms_ntm_pp/models/block/" + itemName + ".json");
        boolean isBlockDone = modJsonWriter.write(path, blockModel);
        path = Paths.get("src/main/resources/assets/hbms_ntm_pp/models/item/" + itemName + ".json");
        boolean isItemDone = modJsonWriter.write(path, itemModel);
        path = Paths.get("src/main/resources/assets/hbms_ntm_pp/blockstates/" + itemName + ".json");
        boolean isBlockStateDone = modJsonWriter.write(path, blockStates);
        if(isBlockDone && isItemDone && isBlockStateDone) System.out.println("File written successfully: " + path);
        else System.out.println("Failed to write file: " + path);

    }
}
