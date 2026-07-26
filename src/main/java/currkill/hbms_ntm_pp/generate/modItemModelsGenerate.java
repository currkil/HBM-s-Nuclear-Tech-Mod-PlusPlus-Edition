package currkill.hbms_ntm_pp.generate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class modItemModelsGenerate {
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
