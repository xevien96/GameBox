package regenbogen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */

class RegenbogenFenster extends JInternalFrame {
    //Array mit den Regenbogenfarben für alle Instanzen
    static Color[] colors = new Color[]{Color.red, Color.orange, Color.yellow, Color.green, Color.cyan, Color.blue, Color.magenta};
    int color = 0;

    /**
     * Erstellt das Regenbogenfenster an einer semi-random Position
     * Erstellt und startet den Thread zum ändern der Hintergrundfarbe des Fensters
     */
    RegenbogenFenster() {
        super("Regenbogen!!!", true, true, true, true);

        Random r = new Random();
        int x = r.nextInt(500);
        int y = r.nextInt(500);
        setBounds(x + 100, y, 500, 500);

        setBackground(colors[color]);
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    color++;
                    color = color % 7;
                    setBackground(colors[color]);
                }
            }
        }.start();
    }
}
