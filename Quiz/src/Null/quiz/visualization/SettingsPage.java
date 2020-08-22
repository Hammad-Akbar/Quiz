package Null.quiz.visualization;

import Null.quiz.Main;
import Null.quiz.helper.Helper;

import javax.swing.*;
import java.awt.event.ActionListener;

import static Null.quiz.Main.*;

public class SettingsPage extends JFrame {
    private JButton languages;
    private ActionListener al;

    public SettingsPage(String name) {
        super(name);

        this.setSize(400, 350);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
    }

    public void initialize() {
        this.languages = new JButton(getMain().getSettings().getLanguage());
        this.languages.setActionCommand("switch_language");
        this.languages.setBounds(0, 0, 100, 25);
        this.initializeActionListener();
        this.languages.addActionListener(this.al);

        this.add(this.languages);
    }

    public void initializeActionListener() {
        this.al = actionEvent -> {
            switch(actionEvent.getActionCommand()) {
                case "switch_language":
                    switch(this.languages.getText()) {
                        case "English":
                            this.languages.setText("Chinese");
                            getMain().getSettings().setLanguage("Chinese");
                            getMain().setScanThroughDirectory(Main.path + "\\quiz\\Chinese\\levels");
                            getMain().getVisualization().switchDialogue(Helper.fromJson(Main.path + "\\quiz\\Chinese\\a.JSON"));
                            getMain().updateLevelList();
                            getMain().restart();
                            break;
                        case "Chinese":
                            this.languages.setText("English");
                            getMain().getSettings().setLanguage("English");
                            getMain().setScanThroughDirectory(Main.path + "\\quiz\\English\\levels");
                            getMain().getVisualization().switchDialogue(Helper.fromJson(Main.path + "\\quiz\\English\\a.JSON"));
                            getMain().updateLevelList();
                            getMain().restart();
                            break;
                    }
            }
        };
    }
}
