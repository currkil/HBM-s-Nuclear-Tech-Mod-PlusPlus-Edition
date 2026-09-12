package currkill.hbms_ntm_pp.generate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class modLootTablesGenerate {
    public static void generate(String blockName, String generateType) throws Exception {
        Map<String, Object> lootTable = new LinkedHashMap<>();
        lootTable.put("type","minecraft:block");

        List<Map<String, Object>> pools = new ArrayList<>();
        Map<String, Object> pool = new LinkedHashMap<>();
        pool.put("rolls",1);

        List<Map<String, Object>> entries = new ArrayList<>();
        Map<String, Object> entry = new LinkedHashMap<>();
        entry.put("type","minecraft:item");
        entry.put("name","hbms_ntm_pp:"+blockName);

        if(!"self".equals(generateType) && !"ore".equals(generateType))
            throw new Exception("Error!Failed to find Generate Type:"+generateType);
        if ("ore".equals(generateType)) {
            List<Map<String, Object>> functions = new ArrayList<>();
            Map<String, Object> fortune = new LinkedHashMap<>();
            fortune.put("function", "minecraft:apply_bonus");
            fortune.put("enchantment", "minecraft:fortune");
            fortune.put("formula", "minecraft:ore_drops");
            functions.add(fortune);
            entry.put("functions", functions);
        }

        entries.add(entry);
        pool.put("entries", entries);

        List<Map<String, Object>> conditions = new ArrayList<>();
        Map<String, Object> condition = new LinkedHashMap<>();
        condition.put("condition", "minecraft:survives_explosion");
        conditions.add(condition);
        pool.put("conditions", conditions);

        pools.add(pool);
        lootTable.put("pools", pools);

        Path path = Paths.get("src/main/resources/data/hbms_ntm_pp/loot_tables/blocks/" + blockName + ".json");
        boolean isDone = modJsonWriter.write(path, lootTable);
        if(isDone) System.out.println("File written successfully: " + path);
        else System.out.println("Failed to write file: " + path);
    }
}
