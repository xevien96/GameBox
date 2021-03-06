package sokoban;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Johann Helbig, Marc Brandt, Albert Renz
 */
public class SokobanViewer extends JPanel implements Observer {
    private JButton[][] spielfeld;
    private Level level;
    private Color freeColor = Color.WHITE, wallColor = Color.BLACK, playerColor = new Color(0,126,255), boxColor = Color.YELLOW, targetColor = Color.RED, boxOnTargetColor = Color.ORANGE, playerOnTargetColor = Color.MAGENTA;

    /**
     * Konstruktor für den SokobanViewer
     *
     * @param lvl Level welches erstellt werden soll
     */
    public SokobanViewer(Level lvl) {
        level = lvl;
        level.addObserver(this);
        setLayout(new GridLayout(level.getZeilenAnzahl(), level.getMaxSpalte()));
        spielfeld = new JButton[level.getZeilenAnzahl()][level.getMaxSpalte()];
        for (int i = 0; i < level.getZeilenAnzahl(); i++) {
            for (int j = 0; j < level.getMaxSpalte(); j++) {
                spielfeld[i][j] = new JButton();
                spielfeld[i][j].setBackground(freeColor);
                add(spielfeld[i][j]);
            }
        }
    }

    /**
     * Methode zum Zeichnen der einzelnen Buttons des Levels
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < level.getZeilenAnzahl(); i++) {
            for (int j = 0; j < level.getMaxSpalte(); j++) {
                switch (level.getLevel(i, j)) {
                    case ' ':
                        spielfeld[i][j].setBackground(freeColor);
                        spielfeld[i][j].setText("");
                        break;
                    case '#':
                        spielfeld[i][j].setBackground(wallColor);
                        break;
                    case '@':
                        spielfeld[i][j].setBackground(playerColor);
                        spielfeld[i][j].setText("Du");
                        break;
                    case '$':
                        spielfeld[i][j].setBackground(boxColor);
                        spielfeld[i][j].setText("$$$");
                        break;
                    case '.':
                        spielfeld[i][j].setBackground(targetColor);
                        spielfeld[i][j].setText("Ziel");
                        break;
                    case '*':
                        spielfeld[i][j].setBackground(boxOnTargetColor);
                        spielfeld[i][j].setText("$*$");
                        break;
                    case '+':
                        spielfeld[i][j].setBackground(playerOnTargetColor);
                        spielfeld[i][j].setText("Du");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Update Methode zum Benachrichtigen des Observers
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == level) {
            repaint();
        }
    }
}
