package Null.quiz.visualization;

import Null.quiz.backend.Level;
import Null.quiz.exceptions.NoMatchAnswerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Visualization extends JFrame {
    private final JPanel answerChoices;
    private final JPanel jp1;
    private final JPanel jp2;
    private final JButton jba;
    private final JButton jbb;
    private final JButton jbc;
    private final JButton jbd;
    private final JButton settings;
    private final JLabel jl;
    private final JLabel jla;
    private final JLabel jlb;
    private final JLabel jlc;
    private final JLabel jld;
    private final JLabel jls;
    private final JLabel jlp;
    private ActionListener buttonClicked;
    public boolean moveOn;
    public int streak, points;
    private Level l;
    private final SettingsPage sp;
    private HashMap<String, Object> hm;

    public Visualization(HashMap<String, Object> hm, String title) {
        super(title);

        this.hm = hm;

        this.streak = 0;
        this.points = 0;

        this.moveOn = false;
        this.setSize(1000, 618);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);


        this.answerChoices = new JPanel();
        int jpx = 600;
        int jpy = 300;
        this.answerChoices.setBounds((this.getWidth() - jpx) / 2, (this.getHeight() - jpy) / 2 + 50, jpx, jpy);
        GridLayout gl = new GridLayout(4, 1);
        gl.setVgap(0);
        gl.setHgap(0);
        this.answerChoices.setLayout(gl);

        this.jp2 = new JPanel();
        int jp2x = 75;
        int jp2y = 300;
        this.jp2.setBounds(75, (this.getHeight() - jp2y) / 2 + 50, jp2x, jp2y);
        GridLayout gl1 = new GridLayout(4, 1);
        gl1.setVgap(0);
        gl1.setHgap(0);
        this.jp2.setLayout(gl1);
        this.jp2.setBackground(Color.BLACK);

        this.jba = new JButton((String) this.hm.get("ca"));
        this.jbb = new JButton((String) this.hm.get("cb"));
        this.jbc = new JButton((String) this.hm.get("cc"));
        this.jbd = new JButton((String) this.hm.get("cd"));

        this.initializeActionListener();

        this.jba.addActionListener(this.buttonClicked);
        this.jbb.addActionListener(this.buttonClicked);
        this.jbc.addActionListener(this.buttonClicked);
        this.jbd.addActionListener(this.buttonClicked);

        this.settings = new JButton("  ⚙️");
        this.settings.setActionCommand("settings");
        this.settings.addActionListener(this.buttonClicked);
        this.settings.setBounds(900, 0, 75, 25);

        this.jl = new JLabel("Question");
        this.jla = new JLabel("Answer 0");
        this.jlb = new JLabel("Answer 1");
        this.jlc = new JLabel("Answer 2");
        this.jld = new JLabel("Answer 3");
        this.jlp = new JLabel(this.hm.get("p") + ": 0");
        this.jls = new JLabel(this.hm.get("s") + ": 0");

        this.jp1 = new JPanel();
        this.jp1.setLayout(new GridLayout(2, 1));
        this.jp1.setBounds(0, 0, 100, 35);

        this.answerChoices.add(this.jla);
        this.answerChoices.add(this.jlb);
        this.answerChoices.add(this.jlc);
        this.answerChoices.add(this.jld);

        this.jp1.add(this.jlp);
        this.jp1.add(this.jls);

        this.jp2.add(this.jba);
        this.jp2.add(this.jbb);
        this.jp2.add(this.jbc);
        this.jp2.add(this.jbd);

        this.sp = new SettingsPage("Settings");
        this.sp.initialize();

        this.jl.setBounds((this.getWidth() - 550) / 2, (this.getHeight() - 350) / 2 - 50, 550, 20);
    }

    public void initialize() {
        this.add(this.jp1);
        this.add(this.answerChoices);
        this.add(this.jp2);
        this.add(this.jl);
        this.add(this.settings);

        this.setVisible(true);
    }

    private void initializeActionListener() {
        this.jba.setActionCommand("a");
        this.jbb.setActionCommand("b");
        this.jbc.setActionCommand("c");
        this.jbd.setActionCommand("d");

        this.buttonClicked = actionEvent -> {
            switch(actionEvent.getActionCommand()) {
                case "a":
                    if(l.a.equals(l.answer)) {
                        this.points++;
                        this.streak++;
                    } else {
                        this.streak = 0;
                    }

                    this.update();

                    this.moveOn = true;

                    break;
                case "b":
                    if(l.b.equals(l.answer)) {
                        this.points++;
                        this.streak++;
                    } else {
                        this.streak = 0;
                    }

                    this.update();

                    this.moveOn = true;

                    break;
                case "c":
                    if(l.c.equals(l.answer)) {
                        this.points++;
                        this.streak++;
                    } else {
                        this.streak = 0;
                    }

                    this.update();

                    this.moveOn = true;

                    break;
                case "d":
                    if(l.d.equals(l.answer)) {
                        this.points++;
                        this.streak++;
                    } else {
                        streak = 0;
                    }

                    this.update();

                    this.moveOn = true;

                    break;
                case "settings":
                    this.sp.setVisible(true);
                    break;
                default:
                    try {
                        throw new NoMatchAnswerException();
                    } catch (NoMatchAnswerException e) {
                        e.printStackTrace();
                    }
            }
        };
    }

    public void applyLevel(Level l) {
        this.l = l;
        this.jl.setText(l.question);
        this.jla.setText(l.a);
        this.jlb.setText(l.b);
        this.jlc.setText(l.c);
        this.jld.setText(l.d);

        System.out.println(l.question);
        System.out.println(l.a);
        System.out.println(l.b);
        System.out.println(l.c);
        System.out.println(l.d);
    }

    public void update() {
        this.jlp.setText(this.hm.get("p") + ": " + this.points);
        this.jls.setText(this.hm.get("s") + ": " + this.streak);
    }

    public void switchDialogue(HashMap<String, Object> hm) {
        this.hm = hm;

        this.jba.setText((String)hm.get("ca"));
        this.jbb.setText((String)hm.get("cb"));
        this.jbc.setText((String)hm.get("cc"));
        this.jbd.setText((String)hm.get("cd"));

        this.jlp.setText(this.hm.get("p") + ": " + this.points);
        this.jls.setText(this.hm.get("s") + ": " + this.streak);
    }
}
