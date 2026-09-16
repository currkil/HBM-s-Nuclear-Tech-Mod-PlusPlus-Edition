package currkill.hbms_ntm_pp.generate;

/**
 * 资源与数据生成的手动入口类。
 * <p>
 * 由 Gradle 任务 {@code generateAssets} 执行，用于批量生成物品模型、方块模型／方块状态以及掉落表。
 * 它和 Forge 官方的数据生成（{@link forgeJsonGenerate}）是两条独立的链路：本类直接用 Gson
 * 把 JSON 手写到 {@code src/main/resources}，不经过数据包管线。
 * <p>
 * 注意：底层写出默认不覆盖已存在的文件，重复执行不会更新已有文件。
 *
 * @author currkill
 */
public class modJsonGenerate {

    /**
     * 程序入口，依次生成各类资源文件。
     *
     * @param args 命令行参数，此处未使用
     * @throws Exception 生成过程中抛出且未被内部捕获的异常
     */
    public static void main(String[] args) throws Exception{
        try {
            System.out.println("Starting data generation...");

            modItemModelsGenerate.generate("steel_pickaxe","item/handheld");
            modItemModelsGenerate.generate("steel_axe","item/handheld");
            modItemModelsGenerate.generate("steel_shovel","item/handheld");
            modItemModelsGenerate.generate("steel_hoe","item/handheld");

            modBlockModelsAndItemModelsGenerate.generate("cluster_copper_ore");
            modBlockModelsAndItemModelsGenerate.generate("cluster_depth_iron_ore");
            modBlockModelsAndItemModelsGenerate.generate("cluster_depth_titanium_ore");
            modBlockModelsAndItemModelsGenerate.generate("cluster_depth_tungsten_ore");
            modBlockModelsAndItemModelsGenerate.generate("cluster_iron_ore");
            modBlockModelsAndItemModelsGenerate.generate("cluster_titanium_ore");

            modLootTablesGenerate.generate("steel_block","self");
            modLootTablesGenerate.generate("struct_launcher","self");
            //modLootTableGenerate.generate("cluster_copper_ore","ore");
            //modLootTableGenerate.generate("cluster_depth_iron_ore","ore");
            //modLootTableGenerate.generate("cluster_depth_titanium_ore","ore");
            //modLootTableGenerate.generate("cluster_depth_tungsten_ore","ore");
            modLootTablesGenerate.generate("lead_ore","ore");
            modLootTablesGenerate.generate("titanium_ore","ore");
            modLootTablesGenerate.generate("tungsten_ore","ore");

            System.out.println("Data generation completed!");
        } catch (Exception e) {
            System.out.println("Data generation failed!");
            e.printStackTrace();
        } finally {
            System.out.println("Data generation ended!");
        }
    }
}
