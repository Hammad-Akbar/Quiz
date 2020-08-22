package Null.quiz.helper;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Helper {
    public static HashMap<String, Object> toEditableJsonHashMap(HashMap<?, ?> hm) {
        Object[] keys = hm.keySet().toArray(new Object[0]);
        Object[] values = hm.values().toArray(new Object[0]);
        HashMap<String, Object> hm1 = new HashMap<>();

        for(int i = 0; i < keys.length; i++) {
            hm1.put((String)keys[i], values[i]);
        }

        return hm1;
    }

    @Deprecated
    public static String fromJson(File path) {
        Gson gson = new Gson();
        String s = "";

        try {
            return gson.fromJson(Files.newBufferedReader(Paths.get(path.toString())), String.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s;
    }

    public static HashMap<String, Object> fromJson(String path) {
        HashMap<String, Object> instance = new HashMap<>();

        try {
            Gson gson = new Gson();
            BufferedReader br = Files.newBufferedReader(Paths.get(path));
            HashMap<?, ?> map = gson.fromJson(br, HashMap.class);
            br.close();
            instance = toEditableJsonHashMap(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
