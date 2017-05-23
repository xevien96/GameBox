import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.*;

import static java.awt.Component.CENTER_ALIGNMENT;

/**
 * Created by albertrenz on 23.05.17.
 */
public class ChildFrame extends JInternalFrame{
    DesktopFrame mydesk;

    public ChildFrame(DesktopFrame df) {
        super("Hauptmenue", true, false, true, true);
        mydesk = df;
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

        JLabel ueberschrift = new JLabel("~GameBox~");
        ueberschrift.setFont(new Font("Arial", Font.BOLD, 40));

        ueberschrift.setAlignmentX(CENTER_ALIGNMENT);
        cp.add(ueberschrift);
        cp.add(Box.createVerticalGlue());

        JButton beenden = new JButton("Beenden");
        beenden.setPreferredSize(new Dimension(100, 40));
        beenden.addActionListener(e -> System.exit(0));
        beenden.setAlignmentX(CENTER_ALIGNMENT);

        cp.add(Box.createVerticalGlue());
        cp.add(beenden);
        cp.add(Box.createVerticalGlue());
    }
}
