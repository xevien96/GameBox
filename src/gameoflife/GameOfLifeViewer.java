package gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */

public class GameOfLifeViewer extends JPanel implements Observer, ActionListener {
    private OrientationT orientation;
    private GameOfLifeModel myGame;                         //Model fuer das View
    private Color deadCellColor = Color.WHITE;
    private Color lifeCellColor = Color.BLUE;
    private MyButton[][] cells;
    private boolean malenIsActive = false;
    private JPopupMenu popup = new JPopupMenu();
    private MouseListener ml = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() instanceof MyButton && malenIsActive) {
                myGame.setAlive(((MyButton) e.getSource()).getZeile(), ((MyButton) e.getSource()).getSpalte());
            }
        }
    };

    /**
     * Konstuktur eines Views
     *
     * @param model       Model des neuen Views
     * @param orientation Orientierung des neuen Views
     */
    public GameOfLifeViewer(GameOfLifeModel model, OrientationT orientation) {
        myGame = model;
        myGame.addObserver(this);
        this.orientation = orientation;
        MyJMenuItem[] farbelife = {new MyJMenuItem("Blau"), new MyJMenuItem("Rot")};
        JMenu lebenFarbe = new JMenu("Lebendige Zellen Farbe");
        for (MyJMenuItem my : farbelife) {
            lebenFarbe.add(my);
            my.addActionListener(e -> {
                setLifeCellColor(((MyJMenuItem) e.getSource()).getFarbe());
                repaint();
            });
        }
        popup.add(lebenFarbe);
        MyJMenuItem[] farbedead = {new MyJMenuItem("Weiss"), new MyJMenuItem("Schwarz")};
        JMenu toteFarbe = new JMenu("Tote Zellen Farbe");
        for (MyJMenuItem my : farbedead) {
            toteFarbe.add(my);
            my.addActionListener(e -> {
                setDeadCellColor(((MyJMenuItem) e.getSource()).getFarbe());
                repaint();
            });
        }
        popup.add(toteFarbe);
        cells = new MyButton[myGame.getRows()][myGame.getColumns()];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new MyButton(i, j);
                cells[i][j].addActionListener(this);
                cells[i][j].addMouseListener(ml);
            }
        }


        //Buttons je nach Orientierung in unterschiedlicher Reihenfolge dem GridLayout hinzufuegen
        switch (orientation) {
            case NORDEN:
                setLayout(new GridLayout(myGame.getRows(), myGame.getColumns()));
                for (int i = 0; i < myGame.getRows(); i++)
                    for (int j = 0; j < myGame.getColumns(); j++) {
                        add(cells[i][j]);
                    }
                break;
            case SUEDEN:
                setLayout(new GridLayout(myGame.getRows(), myGame.getColumns()));
                for (int i = myGame.getRows() - 1; i >= 0; i--)
                    for (int j = myGame.getColumns() - 1; j >= 0; j--) {
                        add(cells[i][j]);
                    }
                break;
            case OSTEN:
                setLayout(new GridLayout(myGame.getColumns(), myGame.getRows()));
                for (int j = 0; j < myGame.getColumns(); j++)
                    for (int i = myGame.getRows() - 1; i >= 0; i--) {
                        add(cells[i][j]);
                    }
                break;
            case WESTEN:
                setLayout(new GridLayout(myGame.getColumns(), myGame.getRows()));
                for (int j = myGame.getColumns() - 1; j >= 0; j--)
                    for (int i = 0; i < myGame.getRows(); i++) {
                        add(cells[i][j]);
                    }
                break;
        }
    }

    /**
     * Ändert den Modus in dem man die Buttons umfärben kann
     *
     * @param malenIsActive setzt diesen Modus aktiv oder inaktiv
     */
    public void setMalenIsActive(boolean malenIsActive) {
        this.malenIsActive = malenIsActive;
    }

    /**
     * Verändert die Farbe toter Zellen
     *
     * @param deadCellColor Farbe fuer die toten Zellen
     */
    public void setDeadCellColor(Color deadCellColor) {
        this.deadCellColor = deadCellColor;
    }

    /**
     * Verändert die Farbe lebendiger Zellen
     *
     * @param lifeCellColor Farbe fuer lebendige Zellen
     */
    public void setLifeCellColor(Color lifeCellColor) {
        this.lifeCellColor = lifeCellColor;
    }

    public OrientationT getOrientation() {
        return orientation;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < myGame.getRows(); i++) {
            for (int j = 0; j < myGame.getColumns(); j++) {
                if (myGame.getCell(i, j)) {
                    cells[i][j].setBackground(lifeCellColor);
                } else {
                    cells[i][j].setBackground(deadCellColor);
                }
            }
        }
    }

    public void update(Observable o, Object arg) {
        if (o == myGame) repaint();
    }

    /**
     * Zelle die hinter dem Button steht in ihrem Zustand ändern
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof MyButton) {
            myGame.changeCell(((MyButton) source).getZeile(), ((MyButton) source).getSpalte());
        }
    }

    public enum OrientationT {NORDEN, OSTEN, SUEDEN, WESTEN}  //Standardfenster ist NORD, weitere dann entsprechend gedreht
}

