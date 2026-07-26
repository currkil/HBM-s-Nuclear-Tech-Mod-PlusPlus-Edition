package currkill.hbms_ntm_pp.generate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class modBlockModelsAndItemModelsGenerate {
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
