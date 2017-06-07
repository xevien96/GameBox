package connect6;

import hauptmenü.DesktopFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */

/**
 * Hauptmenü für das Spiel.
 */
public class Startfenster extends JInternalFrame {
    private DesktopFrame myDesk;

    private Color[] colors1 = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.BLACK, Color.ORANGE, new Color(115, 22, 165)}; //Farbenarray für Spieler1
    private int color1 = 0;
    private Color[] colors2 = new Color[]{Color.MAGENTA, Color.CYAN, Color.YELLOW, Color.DARK_GRAY, new Color(115, 23, 165), Color.PINK}; //Farbenarray für Spieler2
    private int color2 = 0;
    private JLabel ueberschrift;

    private JPanel p1, p2, p3;
    private Container cp;
    private JButton exitButton;
    private JButton loadButton;
    private JButton startGame;
    private JLabel feldlabel;
    private JLabel spieler1Label;
    private JLabel spieler2Label;
    private SpinnerModel countModel = new SpinnerNumberModel(6, 6, 20, 1); // Spinner mit Werten von 6 bis 20
    private JSpinner feldgröße;
    private JTextField spieler1Name;
    private JTextField spieler2Name;
    private JButton spieler1Farbe;
    private JButton spieler2Farbe;

    private final JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir")); // getProperty setzt das Verzeichnis auf das ausführende Verzeichnis
    private FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Text Files", "txt"); // Filter für txt Dateien

    public Startfenster(DesktopFrame df) {
        super("Startmenü", false, true, false, true);
        myDesk = df;
        jFileChooser.addChoosableFileFilter(fileNameExtensionFilter); //Filter zum FileChooser hinzufügen
        jFileChooser.setFileFilter(fileNameExtensionFilter); //Standard Filter auf txt Dateien setzen
        initGui();
        initp1();
        initp2();
        initp3();
    }

    /**
     * Initialisiert das Startfenster mit einem Titel und einer Größe
     * Als Layout dient ein BorderLayout
     */
    private void initGui() {
        setSize(380, 280);
        cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
    }

    /**
     * Überschrift
     */
    private void initp1() {
        p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        ueberschrift = new JLabel("Connect 6");
        ueberschrift.setFont(new Font("Arial", Font.BOLD, 40));
        p1.add(ueberschrift);
        cp.add(p1, BorderLayout.NORTH);
    }

    /**
     * Panel für Feldgröße und Spielerauswahl
     */
    private void initp2() {
        p2 = new JPanel();
        p2.setLayout(new FlowLayout());

        JPanel p20 = new JPanel(); //Panel für Feldgröße mit Spinner
        p20.setLayout(new FlowLayout());
        feldlabel = new JLabel("Feldgröße");
        feldgröße = new JSpinner(countModel);

        p20.add(feldlabel);
        p20.add(feldgröße);

        JPanel p21 = new JPanel(); //Panel für Spieler1 mit Name und Farbe
        p21.setLayout(new FlowLayout());
        spieler1Label = new JLabel("Spieler 1");
        spieler1Name = new JTextField(15);
        spieler1Name.setText("Spieler 1");
        spieler1Farbe = new JButton();
        spieler1Farbe.setPreferredSize(new Dimension(30, 30));
        spieler1Farbe.setBackground(colors1[color1]);
        spieler1Farbe.addActionListener(e -> spieler1Farbe.setBackground(colors1[++color1 % colors1.length])); //wechselt die Farbe im Farbarray

        p21.add(spieler1Label);
        p21.add(spieler1Name);
        p21.add(spieler1Farbe);

        JPanel p22 = new JPanel(); //Panel für Spieler2 mit Name und Farbe
        p22.setLayout(new FlowLayout());
        spieler2Label = new JLabel("Spieler 2");
        spieler2Name = new JTextField(15);
        spieler2Name.setText("Spieler 2");
        spieler2Farbe = new JButton();
        spieler2Farbe.setPreferredSize(new Dimension(30, 30));
        spieler2Farbe.setBackground(colors2[color2]);
        spieler2Farbe.addActionListener(e -> spieler2Farbe.setBackground(colors2[++color2 % colors2.length])); //wechselt die Farbe im Farbarray

        p22.add(spieler2Label);
        p22.add(spieler2Name);
        p22.add(spieler2Farbe);

        p2.add(p20);
        p2.add(p21);
        p2.add(p22);
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
        startGame.addActionListener(e -> startGame());

        loadButton = new JButton("Spiel laden"); //Spiel laden aus Textdatei
        loadButton.addActionListener(e -> loadGame());

        p3.add(startGame);
        p3.add(Box.createHorizontalGlue()); //Abstand zwischen buttons
        p3.add(loadButton);
        p3.add(Box.createHorizontalGlue()); //Abstand zwischen buttons
        p3.add(exitButton);
        cp.add(p3, BorderLayout.PAGE_END);
    }


    /**
     * Startet Spiel mit den gewählten Werten
     */
    private void startGame() {
        int größe = (Integer) feldgröße.getValue(); //Feldgröße aus Spinner auslesen
        if (spieler1Name.getText().trim().equals("")) //Default Name, falls String leer
            spieler1Name.setText("Spieler1");
        if (spieler2Name.getText().trim().equals(""))
            spieler2Name.setText("Spieler2");
        Spieler spieler1 = new Spieler(spieler1Name.getText(), spieler1Farbe.getBackground()); //initialisiert Spieler mit Name und Farbe
        Spieler spieler2 = new Spieler(spieler2Name.getText(), spieler2Farbe.getBackground());
        new Spielfenster(größe, spieler1, spieler2, new ArrayList<>(), myDesk);
        dispose();
    }

    /**
     * Lädt Spieldaten aus Textdatei
     */
    private void loadGame() {
        int returnVal = jFileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File gameFile = jFileChooser.getSelectedFile();
            try {
                Spielfenster.createGameFromFile(gameFile, myDesk);
                dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
