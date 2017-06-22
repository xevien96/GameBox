package gameoflife;

import hauptmenü.DesktopFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */

public class MyDialog extends JDialog {
    DesktopFrame mydesk;

    /**
     * Konstruktor für einen Dialog
     *
     * @param mydesk DesktopFrame in welchem der Dialog erscheinen soll
     */
    public MyDialog(DesktopFrame mydesk) {
        super();
        this.mydesk = mydesk;
        setTitle("Neues Spiel");
        setModal(true);
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

        /**
         * Slider fuer die hoehe des Spielfeldes
         */
        JSlider hoehe = new JSlider(JSlider.HORIZONTAL, 40, 150, 40);
        hoehe.setMajorTickSpacing(10);
        hoehe.setMinorTickSpacing(5);
        hoehe.setSnapToTicks(true);
        hoehe.setPaintTicks(true);
        hoehe.setPaintLabels(true);
        hoehe.setBorder(new TitledBorder("Hoehe"));

        /**
         * Slider fuer die breite des Spielfeldes
         */
        JSlider breite = new JSlider(JSlider.HORIZONTAL, 40, 150, 40);
        breite.setMajorTickSpacing(10);
        breite.setMinorTickSpacing(5);
        breite.setSnapToTicks(true);
        breite.setPaintTicks(true);
        breite.setPaintLabels(true);
        breite.setBorder(new TitledBorder("Breite"));

        /**
         * Startbutton
         */
        JButton start = new JButton("Start");
        start.addActionListener(e -> {
            new ChildFrame(mydesk, new GameOfLifeModel(hoehe.getValue(), breite.getValue()), GameOfLifeViewer.OrientationT.NORDEN);
            dispose();
        });
        start.setAlignmentX(CENTER_ALIGNMENT);

        //Hinzufuegen in den Container
        cp.add(hoehe);
        cp.add(breite);
        cp.add(start);
    }
}
