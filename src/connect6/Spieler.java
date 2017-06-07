package connect6;

import java.awt.*;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */

/**
 * Die Klasse Spieler setzt für einen neun Spieler einen Namen und eine Farbe fest.
 */
public class Spieler {
    private String name;
    private Color farbe;

    public Spieler(String name, Color farbe) {
        this.name = name;
        this.farbe = farbe;
    }

    public String getName() {
        return name;
    }

    public Color getFarbe() {
        return farbe;
    }

}
