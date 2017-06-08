package connect6;

import javax.swing.*;
import java.awt.*;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */

/**
 * Erstellt einen Button mit der zusätzlichen Eigenschaft, dass sie intern nummeriert sind.
 * Dies benutzen wir um beispielsweise die Position eines gesetzten Steins zu ermitteln.
 */
public class MyButton extends JButton {
    private int id;

    public MyButton(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
