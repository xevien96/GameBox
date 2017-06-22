package mvcviewer;

/**
 * @author Johann Helbig, Marc Brandt, Albert Renz
 */

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

//  Das ist ein View fuer textuelle Darstellung eines Quadratischen Polynoms
class TextQView extends JPanel implements Observer {    // Beobachter
    JTextField
            a = new JTextField(10),            // Textfelder fuer
            b = new JTextField(10),            // drei Koeffizienten
            c = new JTextField(10),            // ...
            d = new JTextField(10);
    JLabel
            al = new JLabel("Konstante", JLabel.RIGHT),    // Labels ...
            bl = new JLabel("Linearer Koeffizient", JLabel.RIGHT),
            cl = new JLabel("Quadratischer Koeffizient", JLabel.RIGHT),
            dl = new JLabel("Kubischer Koeffizient", JLabel.RIGHT);
    Qpolynom myPolynom;                // das Modell, ein Polynom

    TextQView(Qpolynom q) {                // Konstuktor
        myPolynom = q;                // merke Polynom
        setLayout(new GridLayout(4, 2, 5, 5));        // 3x2-Grid, 5-er Abstaende
        add(al);
        add(a);                // Labels und Textfelder
        add(bl);
        add(b);                //   hinzufuegen
        add(cl);
        add(c);                //   ...
        add(dl);
        add(d);
        a.setEditable(false);
        b.setEditable(false);            // Editierbarkeit
        c.setEditable(false);            //   der Textfelder
        d.setEditable(false);
    } // end Konstuktor

    public void update(Observable o, Object arg) {    // fuer Observer
        if (o == myPolynom) repaint();  // neu darstellen
    }

    public void paintComponent(Graphics g) {        // Component darstllen
        super.paintComponent(g);            // super aufrufen
        a.setText("" + myPolynom.getConstant());        // Textfelder neu schreiben
        b.setText("" + myPolynom.getLinear());        //    dabei get... Methoden
        c.setText("" + myPolynom.getQuadratic());    //    aus Modell benutzen
        d.setText("" + myPolynom.getKubik());
    }
} // end TextQView