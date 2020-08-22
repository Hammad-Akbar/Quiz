package Null.quiz.backend;

import Null.quiz.exceptions.NoMatchAnswerException;
import Null.quiz.exceptions.NotADirectoryException;
import Null.quiz.helper.Helper;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Level {
    public final String question, a, b, c, d;
    public final String answer;
    private final long levelID;

    public Level(String levelPath) {
        HashMap<?, ?> level = null;

        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(levelPath));
            level = gson.fromJson(reader, HashMap.class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<String, Object> levelReplacement;
        assert level != null;
        levelReplacement = Helper.toEditableJsonHashMap(level);

        String[] keys = levelReplacement.keySet().toArray(new String[0]);
        for(String key : keys) {
            Object tempValue = levelReplacement.get(key);
            levelReplacement.remove(key);
            levelReplacement.put(key.replaceAll(" ", "").toLowerCase(), tempValue);
        }

        this.question = (String)levelReplacement.get("question");
        this.a = (String)levelReplacement.get("a");
        this.b = (String)levelReplacement.get("b");
        this.c = (String)levelReplacement.get("c");
        this.d = (String)levelReplacement.get("d");
        this.levelID = (long)(double)levelReplacement.get("level_id");

        String answer = (String)levelReplacement.get("answer");

        if(answer.startsWith("(") && answer.endsWith(")")) {
            answer = new StringBuilder(answer).deleteCharAt(0).deleteCharAt(answer.length() - 2).toString();

            switch(answer) {
                case "a":
                    this.answer = a;
                    return;
                case "b":
                    this.answer = b;
                    return;
                case "c":
                    this.answer = c;
                    return;
                case "d":
                    this.answer = d;
                    return;
                default:
                    try {
                        throw new NoMatchAnswerException();
                    } catch (NoMatchAnswerException e) {
                        e.printStackTrace();
                    }
            }
        }

        this.answer = (String)levelReplacement.get("answer");
    }

    public static HashMap<Long, Level> scanThrough(String directory) throws NotADirectoryException {
        File d = new File(directory);

        if(!d.isDirectory()) {
            throw new NotADirectoryException();
        }

        File[] files = d.listFiles();

        if(files == null) {
            throw new NullPointerException();
        }

        HashMap<Long, Level> levels = new HashMap<>();

        for(File f : files) {
            if(f.toString().toUpperCase().endsWith(".JSON")) {
                Level l;
                try {
                    l = new Level(f.toString());
                    levels.put(l.levelID, l);
                } catch(NullPointerException ignored) {}
            }
        }

        return levels;
    }
}
