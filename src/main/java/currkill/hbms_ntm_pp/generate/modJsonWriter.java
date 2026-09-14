package currkill.hbms_ntm_pp.generate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * JSON 文件的写出工具。
 * <p>
 * 统一使用「带缩进、关闭 HTML 转义」的 Gson 实例序列化对象，并在写入前自动创建父目录。
 * 是 {@link modItemModelsGenerate}、{@link modBlockModelsAndItemModelsGenerate} 与
 * {@link modLootTablesGenerate} 共同的底层写出入口。
 *
 * @author currkill-deepseek
 */
public class modJsonWriter {

    /** 生成 JSON 文本所用的 Gson 实例。 */
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    /**
     * 写出 JSON 文件，已存在的文件不会被覆盖。
     *
     * @param path 目标文件路径
     * @param data 要被序列化的对象
     * @return 写入成功返回 {@code true}；因文件已存在而跳过时返回 {@code false}
     * @throws Exception 创建目录或写文件失败时抛出
     */
    public static boolean write(Path path, Object data) throws Exception{
        System.out.println("From modJsonWriter:If you want to override file,you need open Override mode.Else it will failed");
        System.out.println("Failed by file!");
        return write(path, data, false);
    }

    /**
     * 写出 JSON 文件，可选择是否覆盖已存在的文件。
     *
     * @param path       目标文件路径
     * @param data       要被序列化的对象
     * @param isOverride 为 {@code true} 时先删除再重写已存在的文件
     * @return 写入成功返回 {@code true}；文件已存在且不允许覆盖时返回 {@code false}
     * @throws Exception 创建目录或写文件失败时抛出
     */
    public static boolean write(Path path, Object data, boolean isOverride) throws Exception{
        if(!isOverride && Files.exists(path)) return false;
        if(isOverride && Files.exists(path)) Files.delete(path);
        Files.createDirectories(path.getParent());
        String json = gson.toJson(data);

        Files.write(path, json.getBytes());
        return true;
    }
}
