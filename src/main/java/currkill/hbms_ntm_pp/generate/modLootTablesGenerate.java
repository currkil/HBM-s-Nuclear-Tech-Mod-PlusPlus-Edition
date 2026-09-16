package currkill.hbms_ntm_pp.generate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 方块掉落表 JSON 的生成器。
 * <p>
 * 生成结果写入 {@code src/main/resources/data/hbms_ntm_pp/loot_tables/blocks/<方块名>.json}。
 * 掉落表固定为「掉落自身」，并附带 {@code survives_explosion} 条件（即被爆炸破坏时也正常掉落）。
 *
 * @author currkill
 */
public class modLootTablesGenerate {

    /**
     * 生成指定方块的掉落表。
     *
     * @param blockName    方块名，同时作为掉落表文件名与掉落物名
     * @param generateType 生成类型：{@code self} 表示普通方块，直接掉落自身；
     *                     {@code ore} 表示矿石，额外附加时运加成函数
     * @throws Exception 生成类型非法或写文件失败时抛出
     */
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
