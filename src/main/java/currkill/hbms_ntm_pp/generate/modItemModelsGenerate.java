package currkill.hbms_ntm_pp.generate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 物品模型 JSON 的生成器。
 * <p>
 * 生成结果写入 {@code src/main/resources/assets/hbms_ntm_pp/models/item/<物品名>.json}，
 * 贴图路径约定为 {@code hbms_ntm_pp:item/<物品名>}。
 * <p>
 * 注意：底层写出默认不覆盖已存在的文件，重复执行时需要 {@link modJsonWriter} 的覆盖模式。
 *
 * @author currkill
 */
public class modItemModelsGenerate {

    /**
     * 生成普通物品模型，父模型固定为 {@code item/generated}。
     *
     * @param itemName 物品名，同时作为模型文件名与贴图名
     * @throws Exception 写文件失败时抛出
     */
    public static void generate(String itemName) throws Exception{

        Map<String, Object> model = new HashMap<>();
        model.put("parent","item/generated");

        Map<String, String> textures = new HashMap<>();
        textures.put("layer0","hbms_ntm_pp:item/" + itemName);
        model.put("textures",textures);

        Path path = Paths.get("src/main/resources/assets/hbms_ntm_pp/models/item/" + itemName + ".json");
        boolean isDone = modJsonWriter.write(path, model);
        if(isDone) System.out.println("File written successfully: " + path);
        else System.out.println("Failed to write file: " + path);
    }

    /**
     * 生成物品模型，并指定父模型。
     *
     * @param itemName 物品名，同时作为模型文件名与贴图名
     * @param parent   父模型，例如 {@code item/handheld} 用于镐、斧等手持物品
     * @throws Exception 写文件失败时抛出
     */
    public static void generate(String itemName,String parent) throws Exception{

        Map<String, Object> model = new HashMap<>();
        model.put("parent",parent);

        Map<String, String> textures = new HashMap<>();
        textures.put("layer0","hbms_ntm_pp:item/" + itemName);
        model.put("textures",textures);

        Path path = Paths.get("src/main/resources/assets/hbms_ntm_pp/models/item/" + itemName + ".json");
        boolean isDone = modJsonWriter.write(path, model);
        if(isDone) System.out.println("File written successfully: " + path);
        else System.out.println("Failed to write file: " + path);
    }

}
