package drehsafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Johann Helbig, Marc Brandt, Albert Renz
 */
public class DrehSafe extends JInternalFrame implements ActionListener {
    private JButton[] buttons = new JButton[10];    //Array mit allen Buttons
    private Container cp;

    private int[] code = new int[]{3, 0, 0, 3, 2, 0, 1, 7};     //Code zum öffnen des Safes
    private int zustand = 0;    //Anzahl richtig eingegebener Stellen
    private int dreh = -1;      //Drehrichtung (-1 ist im Uhrzeigersinn, +1 ...)

    public DrehSafe() {
        setSize(480, 500);
        setTitle("DrehSafe");
        cp = this.getContentPane();
        cp.setLayout(new BorderLayout());   //Borderlayout um die Buttons am Rand zu platzieren
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("" + i);
            buttons[i].addActionListener(this);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
        }                                   // alle Buttons deklarieren
        initp1();
        initp2();
        initp3();
        initp4();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        // jede Sekunde wird der Text jedes Buttons um eins erhöht oder vermindert, wobei 0 - 1 = 9 und 9 + 1 = 0
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (JButton b : buttons) {
                    int label = Integer.parseInt(b.getText());
                    b.setText("" + (label + dreh + buttons.length) % buttons.length);
                }
            }
        }).start();
    }

    //Panel für die oberen Buttons
    private void initp1() {
        Panel p1 = new Panel();
        p1.setPreferredSize(new Dimension(465, 112));
        p1.setLayout(new GridLayout(1, 3));
        p1.add(buttons[0]);
        p1.add(buttons[1]);
        p1.add(buttons[2]);
        cp.add(p1, BorderLayout.NORTH);
    }

    //Panel für die rechten Buttons
    private void initp2() {
        Panel p2 = new Panel();
        p2.setPreferredSize(new Dimension(155, 224));
        p2.setLayout(new GridLayout(2, 1));
        p2.add(buttons[3]);
        p2.add(buttons[4]);
        cp.add(p2, BorderLayout.EAST);
    }

    //Panel für die unteren Buttons
    private void initp3() {
        Panel p3 = new Panel();
        p3.setPreferredSize(new Dimension(465, 112));
        p3.setLayout(new GridLayout(1, 3));
        p3.add(buttons[7]);
        p3.add(buttons[6]);
        p3.add(buttons[5]);
        cp.add(p3, BorderLayout.SOUTH);
    }

    //Panel fpr dei linken Buttons
    private void initp4() {
        Panel p4 = new Panel();
        p4.setPreferredSize(new Dimension(155, 224));
        p4.setLayout(new GridLayout(2, 1));
        p4.add(buttons[9]);
        p4.add(buttons[8]);
        cp.add(p4, BorderLayout.WEST);
    }

    /**
     * bei richtigem Button werden die Buttons grün
     * bei falschem Button werden die Buttons rot und die Drehrichtung ändert sich
     * bei komplett richtiger Codeeingabe schließt sich das Programm
     *
     * @param evt Button der das Event auslöst ausgelesen
     */
    public void actionPerformed(ActionEvent evt) {
        Color col = Color.white;
        int pressed = Integer.parseInt(evt.getActionCommand());
        if (pressed != code[zustand]) {
            col = Color.red;
            drehanders();
            zustand = 0;
        } else if (pressed == code[zustand]) {
            col = Color.green;
            zustand++;
        }

        for (JButton button : buttons) {
            button.setBackground(col);
        }

        if (zustand >= code.length)
            System.exit(0);
    }

    //ändert die Drehrichtung
    private void drehanders() {
        dreh = -dreh;
    }
}
