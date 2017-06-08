package regenbogen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */
public class Regenbogen extends JInternalFrame implements ActionListener {
    //Button wird erzeugt mit Label
    Button newRainbow = new Button("REGENBOGEN!!!");

    /**
     * Konstruktor für das Startfenster
     */
    public Regenbogen() {
        super("regenbogen", true, true, true, true);
        setSize( 200, 100);
        setLayout(new FlowLayout());
        add(newRainbow);
        newRainbow.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Start");
        setVisible(true);
    }

    //Erzeugt beim Klick auf "REGENBOGEN!!!" ein Regenbogenfenster
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if (source == newRainbow)
            new RegenbogenFenster();

    }

}