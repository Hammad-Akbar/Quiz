package Null.quiz;

import Null.quiz.backend.Level;
import Null.quiz.backend.Settings;
import Null.quiz.exceptions.NotADirectoryException;
import Null.quiz.visualization.Visualization;

import java.io.File;
import java.util.HashMap;

import static Null.quiz.backend.Level.scanThrough;
import static Null.quiz.helper.Helper.fromJson;
import static java.lang.Runtime.getRuntime;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showInputDialog;

public class Main {
    public static final File path = new File("D:\\.Programming Work Space\\.Java\\Coder School\\Classworks\\Quiz");
    private static final Main m = new Main();
    private Visualization v;
    private Settings s;
    private File scanThroughDirectory;
    private Level[] levels;
    private volatile Thread t;
    private final int[] i = {0};

    private Main() {

    }

    public static void main(String[] args) {
        m.initialize();
    }

    private void initialize() {
        this.s = new Settings(Main.path + "\\quiz\\internal settings.JSON");
        HashMap<String, Object> hm = fromJson(path + "\\quiz\\" + this.s.getLanguage() + "\\a.JSON");
        this.scanThroughDirectory = new File(Main.path + "\\quiz\\" + this.s.getLanguage() + "\\levels");
        this.v = new Visualization(hm, hm.get("pn") + " - " + this.s.getVersion());

        this.v.initialize();

        Thread shutdownHook = new Thread(() -> this.s.connect());

        getRuntime().addShutdownHook(shutdownHook);

        this.updateLevelList();

        this.start();
    }

    public void setScanThroughDirectory(String directory) {
        this.scanThroughDirectory = new File(directory).getAbsoluteFile();
    }

    public void updateLevelList() {
        HashMap<Long, Level> levels = new HashMap<>();

        try {
            levels = scanThrough(this.scanThroughDirectory.toString());
        } catch (NotADirectoryException e) {
            String newDirectory = showInputDialog(null, "NotADirectoryException has occurred, please enter a new one", "Error", ERROR_MESSAGE);

            while (!new File(newDirectory).isDirectory ()) {
                newDirectory = showInputDialog(null, "NotADirectoryException has occurred, please enter a new one", "Error", ERROR_MESSAGE);

                try {
                    levels = scanThrough(newDirectory);
                } catch(NotADirectoryException ignored) {

                }
            }
        }

        this.levels = levels.values().toArray(new Level[0]);
    }

    public void restart() {
        this.t = null;
        this.start();
    }

    private void start() {
        this.t = new Thread(() -> {
            while(this.i[0] < this.levels.length) {
                this.v.moveOn = false;
                this.v.applyLevel(this.levels[this.i[0]]);

                while(!this.v.moveOn) {
                    System.out.print("");
                }

                this.i[0]++;
            }
        });

        this.t.start();
    }

    public static Main getMain() {
        return m;
    }

    public Settings getSettings() {
        return this.s;
    }

    public Visualization getVisualization() {
        return this.v;
    }
}
