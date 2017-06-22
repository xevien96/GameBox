package hauptmenü;

import javax.swing.*;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */

public class DesktopFrame extends JApplet {
    private JDesktopPane desk;

    public DesktopFrame() {
        desk = new JDesktopPane();
        desk.setDesktopManager(new DefaultDesktopManager());
        setContentPane(desk);
    }

    /**
     * Main Methode für die komplette GameBox welche das DesktopFrame als Hauptfenster öffnet.
     * Diesem Hauptfenster wird dann ein Child von Hauptmenü hinzugefügt
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        DesktopFrame desktop = new DesktopFrame();
        desktop.addChild(new Hauptmenue(desktop), 10, 10);
        Konsole.run(desktop, 800, 800);
    }

    /**
     * Methode zum Hinzufügen von Neuen ChildFrames
     * @param child Programm welches dann als Child hinzugefügt werden soll
     * @param x x Position des neuen ChildFrames
     * @param y y Position des neuen ChildFrames
     */
    public void addChild(JInternalFrame child, int x, int y) {
        child.setLocation(x, y);
        child.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        desk.add(child);
        child.setVisible(true);
    }
}
