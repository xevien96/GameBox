package regenbogen;

import hauptmenü.DesktopFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */
public class Regenbogen extends JInternalFrame implements ActionListener {
    //Button wird erzeugt mit Label
    JButton newRainbow = new JButton("REGENBOGEN!!!");
    DesktopFrame myDesk;

    /**
     * Konstruktor für das sokobanStart
     */
    public Regenbogen(DesktopFrame df) {
        super("regenbogen", true, true, true, true);
        myDesk = df;
        setSize(200, 100);
        setLayout(new FlowLayout());
        add(newRainbow);
        newRainbow.addActionListener(this);
        setTitle("Start");
    }

    //Erzeugt beim Klick auf "REGENBOGEN!!!" ein Regenbogenfenster
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if (source == newRainbow)
            myDesk.addChild(new RegenbogenFenster(), 30, 30);
    }

}