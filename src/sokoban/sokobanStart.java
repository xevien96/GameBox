package sokoban;

import hauptmenü.DesktopFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Vector;

/**
 * @author Johann Helbig, Marc Brandt, Albert Renz
 */
public class sokobanStart extends JInternalFrame {
    public static Vector<Level> minicosmos;
    public static Vector<Level> nabokosmos;
    public static Vector<Level> yoshiomurase;

    static {
        minicosmos = LevelMaker.makeLevelsFromFile(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "sokoban" + System.getProperty("file.separator") + "Levels" + System.getProperty("file.separator") + "Worlds" + System.getProperty("file.separator") + "minicosmos.txt"));
        nabokosmos = LevelMaker.makeLevelsFromFile(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "sokoban" + System.getProperty("file.separator") + "Levels" + System.getProperty("file.separator") + "Worlds" + System.getProperty("file.separator") + "nabokosmos.txt"));
        yoshiomurase = LevelMaker.makeLevelsFromFile(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "sokoban" + System.getProperty("file.separator") + "Levels" + System.getProperty("file.separator") + "Worlds" + System.getProperty("file.separator") + "yoshiomurase.txt"));
    }

    private DesktopFrame myDesk;

    private JLabel ueberschrift;
    private JLabel weltlabel;
    private JLabel levellabel;

    private JPanel p1, p2, p3;
    private Container cp;
    private JButton exitButton;
    private JButton loadButton;
    private JButton startGame;
    private JRadioButton world1;
    private JRadioButton world2;
    private JRadioButton world3;
    private JComboBox level;


    public sokobanStart(DesktopFrame df) {
        super("Startmenü", false, true, false, true);
        myDesk = df;

        initGui();
        initp1();
        initp2();
        initp3();
    }

    /**
     * Initialisiert das Sokoban Startfenster mit einem Titel und einer Größe
     * Als Layout dient ein BorderLayout
     */
    private void initGui() {
        setSize(350, 350);
        cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
    }

    /**
     * Überschrift
     */
    private void initp1() {
        p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        ueberschrift = new JLabel("Sokoban");
        ueberschrift.setFont(new Font("Arial", Font.BOLD, 40));
        p1.add(ueberschrift);
        cp.add(p1, BorderLayout.NORTH);
    }

    private void initp2() {
        p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));

        JPanel p20 = new JPanel(); // Panel für RadioButtons der Weltauswahl
        p20.setLayout(new BoxLayout(p20, BoxLayout.Y_AXIS));
        weltlabel = new JLabel("Weltauswahl:");
        world1 = new JRadioButton("Minicosmos");
        world2 = new JRadioButton("Nabokosmos");
        world3 = new JRadioButton("Yoshiomurase");
        ButtonGroup welten = new ButtonGroup();
        world1.setSelected(true);
        welten.add(world1);
        welten.add(world2);
        welten.add(world3);

        p20.add(weltlabel);
        p20.add(world1);
        p20.add(world2);
        p20.add(world3);

        JPanel p21 = new JPanel(); // Panel für Checkbox der Levelauswahl
        p21.setLayout(new BoxLayout(p21, BoxLayout.Y_AXIS));
        levellabel = new JLabel("Levelauswahl:");
        level = new JComboBox(minicosmos);
        level.setMaximumSize(new Dimension(200, 35));


        p21.add(levellabel);
        p21.add(level);

        p2.add(Box.createHorizontalGlue());
        p2.add(p20);
        p2.add(Box.createHorizontalGlue());
        p2.add(p21);
        p2.add(Box.createHorizontalGlue());
        cp.add(p2, BorderLayout.CENTER);
    }

    /**
     * Panel für drei Buttons (neues Spiel, Spiel laden, Beenden)
     */
    private void initp3() {
        p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
        p3.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));

        exitButton = new JButton("Beenden"); //Beenden
        exitButton.addActionListener(e -> dispose());

        startGame = new JButton("Neues Spiel"); //Neues Spiel
        //startGame.addActionListener(e -> ());

        loadButton = new JButton("Spiel laden"); //Spiel laden aus Textdatei
        //loadButton.addActionListener(e -> ());

        p3.add(startGame);
        p3.add(Box.createHorizontalGlue()); //Abstand zwischen buttons
        p3.add(loadButton);
        p3.add(Box.createHorizontalGlue()); //Abstand zwischen buttons
        p3.add(exitButton);
        cp.add(p3, BorderLayout.PAGE_END);
    }
}
