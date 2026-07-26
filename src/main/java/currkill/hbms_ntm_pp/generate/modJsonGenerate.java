package currkill.hbms_ntm_pp.generate;

public class modJsonGenerate {
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

            System.out.println("Data generation completed!");
        } catch (Exception e) {
            System.out.println("Data generation failed!");
            e.printStackTrace();  // 打印完整错误信息
        }
    }
}
