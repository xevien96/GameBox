package gameoflife;

import javax.swing.*;
import java.awt.*;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */
public class MyJMenuItem extends JMenuItem {
    private Color farbe;

    /**
     * JMenuItem um die Farbe der Zellen zu ändern.
     *
     * @param farbeS Text welcher in den JMenuItems steht.
     */
    public MyJMenuItem(String farbeS) {
        setText(farbeS);
        switch (farbeS) {
            case "Blau":
                farbe = Color.BLUE;
                break;
            case "Rot":
                farbe = Color.RED;
                break;
            case "Weiss":
                farbe = Color.WHITE;
                break;
            case "Schwarz":
                farbe = Color.BLACK;
                break;
        }
    }

    public Color getFarbe() {
        return farbe;
    }
}
