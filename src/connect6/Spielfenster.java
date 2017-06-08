package connect6;

import hauptmenü.DesktopFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */

/**
 * Fenster, in dem das Spiel läuft.
 */
public class Spielfenster extends JInternalFrame implements ActionListener {
    private DesktopFrame myDesk;

    private int feldgröße;
    private int maximaleZüge;
    private int aktiverSpieler = 0;
    private int verbleibendeSteine = 1;
    private JButton[][] spielfeld; //Spielfeld als Matrix von Buttons
    private Spieler[] spieler;
    private ArrayList<Integer> spielzüge; //Dokumentiert alle gemachten Züge

    private JPanel p1, p2, p3;
    private Container cp;
    private JLabel anzeige;
    private JButton exitButton;
    private JButton undoButton;
    private JButton saveButton;

    private final JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir"));
    private FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Text Files", "txt");


    /**
     * Konstruktor für ein Spielfenster
     *
     * @param feldgröße Größe des Spielfelds
     * @param spieler1  Spieler1 mit Name + Farbe
     * @param spieler2  Spieler2 mit Name + Farbe
     * @param spielzüge bisher gemachte Züge (wichtig beim Laden einer Spieldatei)
     */
    public Spielfenster(int feldgröße, Spieler spieler1, Spieler spieler2, ArrayList<Integer> spielzüge, DesktopFrame df) {
        super("Connect6", true, true, true, true);
        myDesk = df;
        jFileChooser.addChoosableFileFilter(fileNameExtensionFilter);
        jFileChooser.setFileFilter(fileNameExtensionFilter);
        this.feldgröße = feldgröße;
        maximaleZüge = feldgröße * feldgröße;
        spielfeld = new JButton[feldgröße][feldgröße]; //Anlegen des Spielfelds
        spieler = new Spieler[]{spieler1, spieler2}; //Spieler werden im Array angelegt
        this.spielzüge = spielzüge;
        int buttonid = 0;

        /**
         * Buttons werden initialisiert mit interner ID (zeilenweise, von links oben nach rechts unten von 0 bis feldgröße * feldgröße -1
         * Buttons werden beim ActionListener registriert
         */
        for (int i = 0; i < spielfeld.length; i++) {
            for (int j = 0; j < spielfeld[i].length; j++) {
                JButton button = new MyButton(buttonid);
                buttonid++;
                button.setBackground(Color.WHITE);
                button.addActionListener(this);
                spielfeld[i][j] = button;
            }
        }

        initGUI();
        initp1();
        initp2();
        initp3();

        /**
         * Bisher gemachte Züge werden gesetzt (beim Laden)
         */
        for (Integer i : this.spielzüge) {
            steinSetzen(i / feldgröße, i % feldgröße);
        }
        myDesk.addChild(this, 30, 30);
    }

    /**
     * Erstellt ein Spielfenster aus einer gespeicherten Textdatei
     *
     * @param gameFile Datei, die die Spielinformationen enthält wird übergeben.
     */
    public static void createGameFromFile(File gameFile, DesktopFrame df) throws IOException {
        InputStream inputStream = new FileInputStream(gameFile);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader in = new BufferedReader(inputStreamReader);
        int feldGröße = Integer.parseInt(in.readLine().trim()); //Feldgröße aus Datei ist die erste Integer Zahl
        String spieler1Name = in.readLine(); //Name von Spieler1
        Color spieler1Farbe = new Color(Integer.parseInt(in.readLine())); //Farbe als absoluten RGB Wert auslesen
        String spieler2Name = in.readLine();
        Color spieler2Farbe = new Color(Integer.parseInt(in.readLine()));
        Spieler spieler1 = new Spieler(spieler1Name, spieler1Farbe);
        Spieler spieler2 = new Spieler(spieler2Name, spieler2Farbe);
        ArrayList<Integer> geladeneZüge = new ArrayList<>();
        String input;
        while ((input = in.readLine()) != null) {
            geladeneZüge.add(Integer.parseInt(input)); //Solange weitere Zeilen(Züge) vorhanden sind, werden sie der Arraylist hinzugefügt
        }
        new Spielfenster(feldGröße, spieler1, spieler2, geladeneZüge, df); //erstellt das Spielfenster
    }

    /**
     * Gui mit Größe und Titel
     */
    private void initGUI() {
        setSize(400, 400);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
    }

    /**
     * Anzeige für aktiven Spieler mit Anzahl verbleibender Steine
     */
    private void initp1() {
        p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        anzeige = new JLabel(spieler[aktiverSpieler].getName() + " ist am Zug. Noch " + verbleibendeSteine + " Stein(e) verbleibend.");
        p1.add(anzeige);
        cp.add(p1, BorderLayout.NORTH);
    }

    /**
     * Buttons werden dem GridLayout hinzugefügt
     */
    private void initp2() {
        p2 = new JPanel();
        p2.setLayout(new GridLayout(feldgröße, feldgröße));
        p2.setBackground(Color.WHITE);
        for (JButton[] b : spielfeld)
            for (JButton c : b)
                p2.add(c);
        cp.add(p2, BorderLayout.CENTER);
    }

    /**
     * Beenden, Rückgängig und Speichern Buttons werden initialisiert und in dem ActionListener registriert
     */
    private void initp3() {
        p3 = new JPanel();
        p3.setLayout(new FlowLayout());
        p3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        exitButton = new JButton("Beenden");
        exitButton.addActionListener(e -> dispose());
        undoButton = new JButton("Rückgängig");
        undoButton.addActionListener(e -> undo());
        undoButton.setEnabled(false);
        saveButton = new JButton("Speichern");
        saveButton.addActionListener(e -> save());

        p3.add(undoButton, FlowLayout.LEFT);
        p3.add(saveButton);
        p3.add(exitButton);
        cp.add(p3, BorderLayout.SOUTH);
    }

    /**
     * Speichert das Laufende Spiel in einer Text Datei
     */
    private void save() {
        int returnVal = jFileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File gameFile = new File(jFileChooser.getSelectedFile() + ".txt");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(gameFile);
                this.printGame(fileOutputStream);
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Methode um den letzten Zug rückgängig zu machen
     */
    private void undo() {
        Integer lastButton = spielzüge.get(spielzüge.size() - 1); //liest den letzten Zug aus
        int zeile = lastButton / feldgröße;
        int spalte = lastButton % feldgröße;
        spielfeld[zeile][spalte].setBackground(Color.WHITE); //letzter gesetzter Stein wird wieder weiß gefärbt
        spielzüge.remove(spielzüge.size() - 1); //letzer Spielzug wird aus dem Array der Spielzüge gelöscht
        if (spielzüge.size() < 1) {
            undoButton.setEnabled(false); // falls keine Züge vorhanden sind, kann der Button nicht benutzt werden
        }
        if (verbleibendeSteine == 1) {
            verbleibendeSteine++;
        } else if (verbleibendeSteine == 2) {
            verbleibendeSteine = 1;
            spielerWechseln();
        }
        anzeige.setText(spieler[aktiverSpieler].getName() + " ist am Zug. Noch " + verbleibendeSteine + " Stein(e) verbleibend.");
    }

    /**
     * Methode um Steine zu setzen
     *
     * @param zeile  Zeile des gesetzten Steins
     * @param spalte Spalte des gesetzten Steins
     */
    private void steinSetzen(int zeile, int spalte) {
        spielfeld[zeile][spalte].setBackground(spieler[aktiverSpieler].getFarbe()); //Farbe des ausgewählten Felds wird auf die Spielerfarbe gefärbt
        verbleibendeSteine--;
        undoButton.setEnabled(true);
        if (verbleibendeSteine <= 0) { //Spielerwechsel wenn anderer Spieler dran ist
            spielerWechseln();
            verbleibendeSteine = 2;
        }
        anzeige.setText(spieler[aktiverSpieler].getName() + " ist am Zug. Noch " + verbleibendeSteine + " Stein(e) verbleibend.");
        testeGewinner(zeile, spalte);
        if (spielzüge.size() >= maximaleZüge) { // es wird getestet ob das Spielfeld voll ist
            JOptionPane.showInternalMessageDialog(this, "Das Spielfeld ist voll", "Spiel beendet", JOptionPane.INFORMATION_MESSAGE);
            undoButton.setEnabled(false);
            saveButton.setEnabled(false);
            for (JButton[] b : spielfeld) {
                for (JButton button : b) {
                    button.setEnabled(false);
                }
            }
            anzeige.setText("Das Spielfeld ist voll");
        }
    }

    /**
     * Testet alle Gewinnmöglichkeiten
     */
    private void testeGewinner(int zeile, int spalte) {
        Color farbeGesetzt = spielfeld[zeile][spalte].getBackground();
        if (testeSpalte(zeile, spalte, farbeGesetzt) || testeZeile(zeile, spalte, farbeGesetzt) || testeHauptdiagonale(zeile, spalte, farbeGesetzt) || testeNebendiagonale(zeile, spalte, farbeGesetzt)) {
            JOptionPane.showInternalMessageDialog(this, spieler[aktiverSpieler].getName() + " hat gewonnen!", "Spiel beendet", JOptionPane.INFORMATION_MESSAGE);
            undoButton.setEnabled(false);
            saveButton.setEnabled(false);
            for (JButton[] b : spielfeld) {
                for (JButton button : b) {
                    button.setEnabled(false);
                }
            }
            anzeige.setText(spieler[aktiverSpieler].getName() + " hat gewonnen!");
        }
    }

    /**
     * testet nach Spalte in der der Stein gesetzt wurde
     *
     * @return ob die Gewinnbedingung erfüllt wurde
     */
    private boolean testeSpalte(int zeile, int spalte, Color farbeGesetzt) {
        while (zeile - 1 >= 0 && spielfeld[zeile - 1][spalte].getBackground() == farbeGesetzt) {
            zeile--;
        }
        int zähler = 1;
        while (zeile + 1 < feldgröße && spielfeld[zeile + 1][spalte].getBackground() == farbeGesetzt) {
            zähler++;
            zeile++;
        }
        return zähler > 5;
    }

    /**
     * testet nach Zeile in der der Stein gesetzt wurde
     *
     * @return ob die Gewinnbedingung erfüllt wurde
     */
    private boolean testeZeile(int zeile, int spalte, Color farbeGesetzt) {
        while (spalte - 1 >= 0 && spielfeld[zeile][spalte - 1].getBackground() == farbeGesetzt) {
            spalte--;
        }
        int zähler = 1;
        while (spalte + 1 < feldgröße && spielfeld[zeile][spalte + 1].getBackground() == farbeGesetzt) {
            zähler++;
            spalte++;
        }
        return zähler > 5;
    }

    /**
     * testet Diagonale von links oben nach rechts unten
     *
     * @return ob die Gewinnbedingung erfüllt wurde
     */
    private boolean testeHauptdiagonale(int zeile, int spalte, Color farbeGesetzt) {
        while (zeile - 1 >= 0 && spalte - 1 >= 0 && spielfeld[zeile - 1][spalte - 1].getBackground() == farbeGesetzt) {
            zeile--;
            spalte--;
        }
        int zähler = 1;
        while (zeile + 1 < feldgröße && spalte + 1 < feldgröße && spielfeld[zeile + 1][spalte + 1].getBackground() == farbeGesetzt) {
            zähler++;
            zeile++;
            spalte++;
        }
        return zähler > 5;
    }

    /**
     * testet Diagonale von rechts oben nach links unten
     *
     * @return ob die Gewinnbedingung erfüllt wurde
     */
    private boolean testeNebendiagonale(int zeile, int spalte, Color farbeGesetzt) {
        while (zeile - 1 >= 0 && spalte + 1 < feldgröße && spielfeld[zeile - 1][spalte + 1].getBackground() == farbeGesetzt) {
            zeile--;
            spalte++;
        }
        int zähler = 1;
        while (zeile + 1 < feldgröße && spalte - 1 >= 0 && spielfeld[zeile + 1][spalte - 1].getBackground() == farbeGesetzt) {
            zähler++;
            zeile++;
            spalte--;
        }
        return zähler > 5;
    }

    /**
     * wechselt den aktiven Spieler
     */
    private void spielerWechseln() {
        aktiverSpieler = Math.abs(aktiverSpieler - 1);
    }

    /**
     * Schreibt alle notwendigen Informationen in den outputStream
     *
     * @param outputStream Der Stream in dem das Spiel gespeichert/geschrieben wird
     */
    private void printGame(OutputStream outputStream) {
        PrintStream writer = new PrintStream(outputStream, true);
        writer.println(feldgröße);
        writer.println(spieler[0].getName());
        writer.println(spieler[0].getFarbe().getRGB());
        writer.println(spieler[1].getName());
        writer.println(spieler[1].getFarbe().getRGB());
        for (Integer i : spielzüge)
            writer.println(i);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof MyButton) { //erlaubt das Casten auf MyButton
            if (((MyButton) source).getBackground() == Color.WHITE) { //testet ob das Feld frei ist
                spielzüge.add(((MyButton) source).getId()); //fügt ID den gesetzten Zügen hinzu
                steinSetzen(((MyButton) source).getId() / feldgröße, ((MyButton) source).getId() % feldgröße); // setzt den Stein
            }
        }
    }
}
