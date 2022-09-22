package tk.siurasowo.dcmchat.utils;

import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class AdvancementUtil {

    @SneakyThrows
    public static String getValue(String path, String key) {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(path));
        JSONObject jsonObject = (JSONObject)obj;
        return (String) jsonObject.get(key);
    }

}
