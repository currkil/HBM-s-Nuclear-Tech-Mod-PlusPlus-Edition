package currkill.hbms_ntm_pp.generate;

public class modJsonGenerate {
    public static void main(String[] args) throws Exception{
        try {
            System.out.println("开始数据生成...");
            modItemModelsGenerate.generate("TEST_ITEM");
            System.out.println("生成完成！");
        } catch (Exception e) {
            e.printStackTrace();  // 打印完整错误信息
        }
    }
}
