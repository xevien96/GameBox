package siebenSpaltenPrim;

import javax.swing.*;
import java.awt.*;

public class SiebenSpaltenPrim extends JInternalFrame {
    private JTextArea textArea;
    private JScrollPane scrollPane;

    /**
     * Konstruktor für SiebenSpaltenPrim Hauptfenster
     */
    public SiebenSpaltenPrim() {
        super("SiebenSpaltenPrim", true, true, true, true);
        setSize(590, 400);  //Größe des Fenster
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        scrollPane = new JScrollPane(); //Scrollbar
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);    //Horizontale Scrollbar

        textArea = new JTextArea(); //TextArea,wo die Primzahlen angezeigt werden
        textArea.setEditable(false);
        scrollPane.getViewport().add(textArea);

        JPanel buttonPanel = new JPanel();  //Panel für Buttons
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton startButton = new JButton("Starten");   //Start Button
        startButton.addActionListener(e -> starten());

        JButton schliessenButton = new JButton("Schließen");    //Schließen Button
        schliessenButton.addActionListener(e -> dispose());

        //Adden der Buttons
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(schliessenButton);
        buttonPanel.add(Box.createHorizontalGlue());

        cp.add(scrollPane, BorderLayout.CENTER);
        cp.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Methode welche die Primzahlen in das textArea schreibt und die Scrollbar dabei mitzieht und vergrößert
     *
     * @param text
     */
    public void write(String text) {
        textArea.append(text);
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }

    /**
     * Methode welche den Thread für die Primzahlen startet
     */
    public void starten() {
        new Thread(() -> {
            Primer first = new Primer(2, "", this);
            Primer second = new Primer(2, "\t", this);
            Primer third = new Primer(2, "\t\t", this);
            Primer fourth = new Primer(2, "\t\t\t", this);
            Primer fifth = new Primer(2, "\t\t\t\t", this);
            Primer sixth = new Primer(2, "\t\t\t\t\t", this);
            Primer seventh = new Primer(2, "\t\t\t\t\t\t", this);
            for (int i = 3; i <= 6000; ++i) {
                first.send(i);  //senden
                second.send(i);     //der
                third.send(i);          //einzelnen
                fourth.send(i);             //insgesamt
                fifth.send(i);                  //Sieben
                sixth.send(i);                      //Spalten
                seventh.send(i);                        //der Primzahlen
            }
            //senden einer 0 in allen Spalten
            first.send(0);
            second.send(0);
            third.send(0);
            fourth.send(0);
            fifth.send(0);
            sixth.send(0);
            seventh.send(0);
            write("Main beendet");
        }).start();
    }
}

class Primer extends Thread implements Runnable {
    private int p;
    private Primer next;
    private String prefix;
    private SiebenSpaltenPrim myFrame;
    private int buffer = -1;

    /**
     * Konstruktor für eine Primzahl
     * @param prime
     * @param prefix
     * @param inf
     */
    Primer(int prime, String prefix, SiebenSpaltenPrim inf) {
        super("Primer-" + prime);
        myFrame = inf;
        p = prime;
        this.prefix = prefix;
        this.start();
    }

    /**
     * Method um eine Primzahl durchzuzählen
     */
    public void run() {
        myFrame.write(prefix + p + "\n");
        while (true) {
            int n = recieve();
            if (n == 0) {
                if (next != null)
                    next.send(n);
                break;
            }
            if (n % p != 0) {
                if (next != null)
                    next.send(n);
                else
                    next = new Primer(n, prefix, myFrame);
            }
        }
    }

    /**
     * Methode zum Senden der Zahlen
     * @param i integer
     */
    synchronized void send(int i) {
        try {
            while (buffer >= 0)
                wait();
            buffer = i;
            notify();
        } catch (InterruptedException e) {
        }
    }

    /**
     * Methode zum Erhalten der Zahlen
     * @return Zahlen vom Typ int
     */
    synchronized int recieve() {
        int result = 0;
        try {
            while ((result = buffer) < 0)
                wait();
            buffer = -1;
            notify();
        } catch (InterruptedException e) {
        }
        return result;
    }
}
