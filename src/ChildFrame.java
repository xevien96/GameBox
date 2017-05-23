import javax.swing.*;
import java.awt.*;

public class ChildFrame extends JInternalFrame{
    DesktopFrame mydesk;

    public ChildFrame(DesktopFrame df) {
        super("Hauptmenue", true, false, true, true);
        Dimension buttonSize = new Dimension(150,30);
        mydesk = df;
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

        //Überschrift Hauptmenü
        JLabel ueberschrift = new JLabel("~GameBox~");
        ueberschrift.setFont(new Font("Arial", Font.BOLD, 40));
        ueberschrift.setAlignmentX(CENTER_ALIGNMENT);
        cp.add(ueberschrift);
        cp.add(Box.createVerticalGlue());

        //Panels für SpielButtons
        JPanel gameButtonPanel = new JPanel();
        gameButtonPanel.setLayout(new BoxLayout(gameButtonPanel, BoxLayout.X_AXIS));

        JPanel gameButtonPanellinks = new JPanel();
        gameButtonPanellinks.setLayout(new BoxLayout(gameButtonPanellinks, BoxLayout.Y_AXIS));

        JPanel gameButtonPanelrechts = new JPanel();
        gameButtonPanelrechts.setLayout(new BoxLayout(gameButtonPanelrechts, BoxLayout.Y_AXIS));

        gameButtonPanel.add(Box.createHorizontalGlue());
        gameButtonPanel.add(gameButtonPanellinks);
        gameButtonPanel.add(Box.createHorizontalGlue());
        gameButtonPanel.add(gameButtonPanelrechts);
        gameButtonPanel.add(Box.createHorizontalGlue());
        cp.add(gameButtonPanel);

        //Buttons für die einzelnen Spiele erstellen mit ActionListener
        //TODO ActionListener
        JButton siebenSpaltenButton = new JButton("Sieben Prim");
        siebenSpaltenButton.setPreferredSize(buttonSize);
        siebenSpaltenButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton regenbogenButton = new JButton("Regenbogen");
        regenbogenButton.setPreferredSize(buttonSize);
        regenbogenButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton drehSafeButton = new JButton("Drehsafe");
        drehSafeButton.setPreferredSize(buttonSize);
        drehSafeButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton dragSafeButton = new JButton("Dragsafe");
        dragSafeButton.setPreferredSize(buttonSize);
        dragSafeButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton connect6Button = new JButton("Connect6");
        connect6Button.setPreferredSize(buttonSize);
        connect6Button.setAlignmentX(CENTER_ALIGNMENT);

        JButton mvcExampleButton = new JButton("MVC example");
        mvcExampleButton.setPreferredSize(buttonSize);
        mvcExampleButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton gameOfLifeButton = new JButton("Game of Life");
        gameOfLifeButton.setPreferredSize(buttonSize);
        gameOfLifeButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton sokobanButton = new JButton("Sokoban");
        sokobanButton.setPreferredSize(buttonSize);
        sokobanButton.setAlignmentX(CENTER_ALIGNMENT);

        //Buttons den Panels hinzufügen
        gameButtonPanellinks.add(Box.createVerticalGlue());
        gameButtonPanellinks.add(siebenSpaltenButton);
        gameButtonPanellinks.add(Box.createVerticalGlue());
        gameButtonPanellinks.add(regenbogenButton);
        gameButtonPanellinks.add(Box.createVerticalGlue());
        gameButtonPanellinks.add(sokobanButton);
        gameButtonPanellinks.add(Box.createVerticalGlue());
        gameButtonPanellinks.add(drehSafeButton);
        gameButtonPanellinks.add(Box.createVerticalGlue());

        gameButtonPanelrechts.add(Box.createVerticalGlue());
        gameButtonPanelrechts.add(mvcExampleButton);
        gameButtonPanelrechts.add(Box.createVerticalGlue());
        gameButtonPanelrechts.add(gameOfLifeButton);
        gameButtonPanelrechts.add(Box.createVerticalGlue());
        gameButtonPanelrechts.add(connect6Button);
        gameButtonPanelrechts.add(Box.createVerticalGlue());
        gameButtonPanelrechts.add(dragSafeButton);
        gameButtonPanelrechts.add(Box.createVerticalGlue());

        JButton beendenButton = new JButton("Beenden");
        beendenButton.setPreferredSize(buttonSize);
        beendenButton.addActionListener(e -> System.exit(0));
        beendenButton.setAlignmentX(CENTER_ALIGNMENT);

        cp.add(Box.createVerticalGlue());
        cp.add(beendenButton);
        cp.add(Box.createVerticalGlue());
    }
}
