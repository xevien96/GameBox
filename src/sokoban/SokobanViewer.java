package sokoban;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Johann on 17/06/19.
 */
public class SokobanViewer extends JPanel implements Observer {
    private JButton[][] spielfeld;
    private Level level;
    private Color freeColor = Color.WHITE, wallColor = Color.BLACK, playerColor = Color.BLUE, boxColor = Color.YELLOW, targetColor = Color.RED, boxOnTargetColor = Color.ORANGE, playerOnTargetColor = Color.MAGENTA;

    public SokobanViewer(Level lvl) {
        level = lvl;
        level.addObserver(this);
        setLayout(new GridLayout(level.getZeilenAnzahl(), level.getMaxSpalte()));
        spielfeld = new JButton[level.getZeilenAnzahl()][level.getMaxSpalte()];
        for (JButton[] ba : spielfeld) {
            for (JButton b : ba) {
                b = new JButton();
                b.setBackground(freeColor);
                add(b);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < level.getZeilenAnzahl(); i++) {
            for (int j = 0; j < level.getMaxSpalte(); j++) {
                switch (level.getLevel(i, j)) {
                    case ' ':
                        spielfeld[i][j].setBackground(freeColor);
                        break;
                    case '#':
                        spielfeld[i][j].setBackground(wallColor);
                        break;
                    case '@':
                        spielfeld[i][j].setBackground(playerColor);
                        break;
                    case '$':
                        spielfeld[i][j].setBackground(boxColor);
                        break;
                    case '.':
                        spielfeld[i][j].setBackground(targetColor);
                        break;
                    case '*':
                        spielfeld[i][j].setBackground(boxOnTargetColor);
                        break;
                    case '+':
                        spielfeld[i][j].setBackground(playerOnTargetColor);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == level) {
            repaint();
        }
    }
}
