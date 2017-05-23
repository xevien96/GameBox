import javax.swing.*;
import java.awt.*;

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

        JButton[] buttons = {};

        JButton beenden = new JButton("Beenden");
        beenden.setPreferredSize(new Dimension(100, 40));
        beenden.addActionListener(e -> System.exit(0));
        beenden.setAlignmentX(CENTER_ALIGNMENT);

        cp.add(Box.createVerticalGlue());
        cp.add(beenden);
        cp.add(Box.createVerticalGlue());
    }
}
