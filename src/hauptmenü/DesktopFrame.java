package hauptmenü;

import javax.swing.*;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */

public class DesktopFrame extends JApplet {
    private JDesktopPane desk;

    public DesktopFrame(){
        desk = new JDesktopPane();
        desk.setDesktopManager(new DefaultDesktopManager());
        setContentPane(desk);
    }

    public void addChild(JInternalFrame child, int x, int y){
        child.setLocation(x, y);
        child.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        desk.add(child);
        child.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        DesktopFrame desktop = new DesktopFrame();
        desktop.addChild(new Hauptmenue(desktop),10,10);
        Konsole.run(desktop,800,600);
    }
}
