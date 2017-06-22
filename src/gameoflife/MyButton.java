package gameoflife;

import javax.swing.*;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */

public class MyButton extends JButton {
    private int zeile;
    private int spalte;

    /**
     * Buttons für die Einzelnen Zellen des GameOfLife
     *
     * @param zeile  Zeilen des GameOfLife
     * @param spalte Spalten des GameOfLife
     */
    public MyButton(int zeile, int spalte) {
        super();
        this.zeile = zeile;
        this.spalte = spalte;
    }

    public int getZeile() {
        return zeile;
    }

    public int getSpalte() {
        return spalte;
    }
}
