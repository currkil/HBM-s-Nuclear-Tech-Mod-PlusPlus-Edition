package currkill.hbms_ntm_pp.generate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.file.Files;
import java.nio.file.Path;

public class modJsonWriter {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    public static boolean write(Path path, Object data) throws Exception{
        System.out.println("From modJsonWriter:If you want to override file,you need open Override mode.Else it will failed");
        System.out.println("Failed by file!");
        return write(path, data, false);
    }
    public static boolean write(Path path, Object data, boolean isOverride) throws Exception{
        if(!isOverride && Files.exists(path)) return false;
        if(isOverride && Files.exists(path)) Files.delete(path);
        Files.createDirectories(path.getParent());
        String json = gson.toJson(data);

        Files.write(path, json.getBytes());
        return true;
    }
}
