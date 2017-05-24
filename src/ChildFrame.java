import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChildFrame extends JInternalFrame{
    DesktopFrame mydesk;

    public ChildFrame(DesktopFrame df) {
        super("Hauptmenue", true, false, true, true);
        Dimension buttonSize = new Dimension(115,50);
        mydesk = df;
        setSize(300,300);
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
        siebenSpaltenButton.setMaximumSize(buttonSize);
        siebenSpaltenButton.setAlignmentX(CENTER_ALIGNMENT);
        siebenSpaltenButton.addActionListener(e -> new ChildFrame(mydesk, new SiebenSpaltenPrim(), "SiebenSpaltenPrim", 250, 400));

        JButton regenbogenButton = new JButton("Regenbogen");
        regenbogenButton.setMaximumSize(buttonSize);
        regenbogenButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton drehSafeButton = new JButton("Drehsafe");
        drehSafeButton.setMaximumSize(buttonSize);
        drehSafeButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton dragSafeButton = new JButton("Dragsafe");
        dragSafeButton.setMaximumSize(buttonSize);
        dragSafeButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton connect6Button = new JButton("Connect6");
        connect6Button.setMaximumSize(buttonSize);
        connect6Button.setAlignmentX(CENTER_ALIGNMENT);

        JButton mvcExampleButton = new JButton("MVC example");
        mvcExampleButton.setMaximumSize(buttonSize);
        mvcExampleButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton gameOfLifeButton = new JButton("Game of Life");
        gameOfLifeButton.setMaximumSize(buttonSize);
        gameOfLifeButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton sokobanButton = new JButton("Sokoban");
        sokobanButton.setMaximumSize(buttonSize);
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
        beendenButton.setMaximumSize(buttonSize);
        beendenButton.addActionListener(e -> System.exit(0));
        beendenButton.setAlignmentX(CENTER_ALIGNMENT);

        cp.add(Box.createVerticalGlue());
        cp.add(beendenButton);
        cp.add(Box.createVerticalGlue());
    }

    public ChildFrame(DesktopFrame df, JPanel content, String title, int width, int height){
        super(title, true, true, true, true);
        mydesk = df;
        setSize(width,height);
        Container cp = getContentPane();
        cp.add(content);
        mydesk.addChild(this, 20, 20);
    }
}
