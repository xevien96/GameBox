package dragsafe;

import hauptmenü.DesktopFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Johann Helbig, Marc Brandt, Albert Renz
 */

public class DragSafe extends JInternalFrame implements MouseMotionListener, MouseListener {
    public DesktopFrame myDesk;
    private JButton[] buttons = new JButton[10];    //Array mit allen Buttons
    private Container cp;
    private boolean active = true;  //wenn false, werden Mousedraggedevents ignoriert

    private int[] code = new int[]{0, 7, 0, 4, 7, 1, 0, 2};     //Code zum öffnen des Safes
    private int zustand = 0;    //Anzahl richtig eingegebener Stellen
    private int dreh = -1;      //Drehrichtung (-1 ist im Uhrzeigersinn, +1 ...)
    long sleeptime = 2000;
    static int numberOfWindows = 0; //Anzahl der offenen Fenster(Levelzähler)
    int drehzaehler;                //Anzahl der durchgeführten Drehungen

    public DragSafe(DesktopFrame df) {
        myDesk = df;
        sleeptime = (long) (sleeptime / Math.pow(1.5, numberOfWindows)); //jedes Fenster ist 50% schneller als das vorherige Level
        numberOfWindows++;
        setSize(480, 500);
        setTitle("DragSafe");
        cp = this.getContentPane();
        cp.setLayout(new GridLayout(4, 3));   //Gridlayout um die Buttons am Rand zu platzieren
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("" + i);
            buttons[i].addMouseMotionListener(this);
            buttons[i].addMouseListener(this);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
        }
        cp.add(buttons[0]);
        cp.add(buttons[1]);
        cp.add(buttons[2]);
        cp.add(buttons[9]);
        cp.add(new Panel());
        cp.add(buttons[3]);
        cp.add(buttons[8]);
        cp.add(new Panel());
        cp.add(buttons[4]);
        cp.add(buttons[7]);
        cp.add(buttons[6]);
        cp.add(buttons[5]);
        // alle Buttons deklarieren
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Programm kann mit dem Schließenbutton komplett beendet werden
        setVisible(true);
        // jede Sekunde wird der Text jedes Buttons um eins erhöht oder vermindert, wobei 0 - 1 = 9 und 9 + 1 = 0
        //nach 13 Schritten ändert sich die Drehrichtung
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(sleeptime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (JButton b : buttons) {
                    int label = Integer.parseInt(b.getText());
                    b.setText("" + (label + dreh + buttons.length) % buttons.length);
                }
                if (drehzaehler++ >= 13) {
                    drehanders();
                    drehzaehler = drehzaehler % 13;
                }
            }
        }).start();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    //aktiviert MouseDraggedEvents nach dem loslassen der Maus wieder
    public void mouseReleased(MouseEvent evt) {
        this.active = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * bei richtigem Button werden die Buttons grün
     * bei falschem Button werden die Buttons rot und ein neues Fenster wird geöffnet
     * bei komplett richtiger Codeeingabe schließt sich das Fenster
     * wenn alle Fenster geschlossen sind wird das Programm beendet
     *
     * @param evt Button der das Event auslöst ausgelesen
     */
    public void mouseDragged(MouseEvent evt) {
        if (this.active) {
            Object source = evt.getSource();
            if (source instanceof JButton) {
                this.active = false;
                Color col = Color.white;
                int pressed = Integer.parseInt(((JButton) source).getActionCommand());
                if (pressed != code[zustand]) {
                    col = Color.red;
                    myDesk.addChild(new DragSafe(myDesk), 20, 20);
                    zustand = 0;
                } else if (pressed == code[zustand]) {
                    col = Color.green;
                    zustand++;
                }

                for (JButton button : buttons) {
                    button.setBackground(col);
                }

                if (zustand >= code.length) {
                    numberOfWindows--;
                    this.dispose();
                }
                if (numberOfWindows <= 0)
                    System.exit(0);
            }
        }
    }

    //ändert die Drehrichtung
    private void drehanders() {
        dreh = -dreh;
    }
}
