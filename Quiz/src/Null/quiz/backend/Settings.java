package Null.quiz.backend;

import Null.quiz.Main;
import Null.quiz.helper.Helper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Settings {
    private final HashMap<String, Object> hm;

    public Settings(String location) {
        this.hm = Helper.fromJson(location);
    }

    public String getLanguage() {
        return (String)this.hm.get("language");
    }

    public void setLanguage(String s) {
        this.hm.replace("language", s);
    }

    public String getVersion() {
        return (String)this.hm.get("version");
    }

    public void connect() {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        String s = gson.toJson(this.hm);

        try {
            FileWriter fw = new FileWriter(Main.path + "\\quiz\\internal settings.JSON");
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
