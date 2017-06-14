package mvcviewer;

/**
 * @author Johann Helbig, Marc Brandt, Albert Renz
 */

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Das ist ein View fuer graphische Darstellung eines Kubischen Polynoms
 */
public class GraphQView extends JPanel implements Observer {
    Qpolynom myPolynom;
    int points = 2000;
    int minx;
    int maxx;
    Color color;

    GraphQView(Qpolynom q, int minx, int maxx, Color color) {
        myPolynom = q;
        this.color = color;
        this.minx = minx;
        this.maxx = maxx;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // in Superklasse aufrufen ...
        int maxWidth = getWidth(); // Weite bestimmen
        int maxHeight = getHeight(); // Hoehe Bestimmen
        double hstep = (double) maxWidth / (double) points;
        int x1;
        int x2;
        int y1;
        int y2;

        g.setColor(color);
        int[] pts = new int[points];
        for (int i = 0; i < points; i++) {
            double x = (((double) maxx - (double) minx) / (double) points) * i + minx;
            pts[i] = (int) (maxHeight / 2 - (myPolynom.getConstant() + x * myPolynom.getLinear() + Math.pow(x, 2) * myPolynom.getQuadratic() + Math.pow(x, 3) * myPolynom.getKubik()));
        }
        for (int i = 1; i < points; i++) {
            x1 = (int) ((i - 1) * hstep);
            x2 = (int) (i * hstep);
            y1 = pts[i - 1];
            y2 = pts[i]; //
            g.drawLine(x1, y1, x2, y2);
        }
    } // end paintComponent

    public void setMinx(int minx) {
        this.minx = minx;
    }

    public void setMaxx(int maxx) {
        this.maxx = maxx;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void update(Observable o, Object arg) {    // fuer Observer
        if (o == myPolynom) repaint();            // neu darstellen
    }


} // end GraphQView

