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
        if(Files.exists(path)) {
            return false;
        }
        Files.createDirectories(path.getParent());
        String json = gson.toJson(data);

        Files.write(path, json.getBytes());
        return true;
    }
}
