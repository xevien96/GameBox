package gameoflife;

import hauptmenü.DesktopFrame;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */

public class ChildFrame extends JInternalFrame implements ActionListener {
    DesktopFrame mydesk;
    GameOfLifeModel model;
    GameOfLifeViewer view;

    public ChildFrame(DesktopFrame df) {
        super("Game Of Life", true, false, true, true);
        setSize(300, 300);
        mydesk = df;
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

        JLabel ueberschrift = new JLabel("~Game of Life~");
        ueberschrift.setFont(new Font("Arial", Font.BOLD, 40));

        ueberschrift.setAlignmentX(CENTER_ALIGNMENT);
        cp.add(ueberschrift);
        cp.add(Box.createVerticalGlue());

        JButton neuesSpiel = new JButton("Neues Spiel");
        neuesSpiel.setPreferredSize(new Dimension(100, 40));
        neuesSpiel.addActionListener(e -> {
            MyDialog dlg = new MyDialog(mydesk);
            dlg.setVisible(true);
        });

        JButton beenden = new JButton("Beenden");
        beenden.setPreferredSize(new Dimension(100, 40));
        beenden.addActionListener(e -> dispose());

        neuesSpiel.setAlignmentX(CENTER_ALIGNMENT);
        beenden.setAlignmentX(CENTER_ALIGNMENT);
        cp.add(neuesSpiel);
        cp.add(Box.createVerticalGlue());
        cp.add(beenden);
        cp.add(Box.createVerticalGlue());
    }

    public ChildFrame(DesktopFrame df, GameOfLifeModel model, GameOfLifeViewer.OrientationT orientation) {
        super("Game of Life", true, true, true, false);
        setSize(300, 300);
        mydesk = df;
        this.model = model;
        Container cp = getContentPane();
        view = new GameOfLifeViewer(model, orientation);
        cp.add(view);


        /**
         * Erstellen des Menu Bar mit seinen Reitern
         */
        JMenu[] menus = {new JMenu("Modus"), new JMenu("Geschwindigkeit"), new JMenu("Fensterausrichtung"), new JMenu("Figuren")};

        JMenuItem[] modus = {new JMenuItem("Laufen"), new JMenuItem("Setzen"), new JMenuItem("Malen"), new JMenuItem("Next Gen"), new JMenuItem("ClearAll")};
        for (int i = 0; i < modus.length; i++) {
            menus[0].add(modus[i]);
            modus[i].addActionListener(this);
            if (i == 2) {
                menus[0].addSeparator();
            }
        }

        JMenuItem[] geschwindigkeit = {new JMenuItem("Schneller"), new JMenuItem("Langsamer"), new JMenuItem("Reset")};
        for (int i = 0; i < geschwindigkeit.length; i++) {
            menus[1].add(geschwindigkeit[i]);
            geschwindigkeit[i].addActionListener(this);
            if (i == 1) {
                menus[1].addSeparator();
            }
        }

        JMenuItem[] ausrichtungen = {new JMenuItem("Norden"), new JMenuItem("Osten"), new JMenuItem("Sueden"), new JMenuItem("Westen"), new JMenuItem("Kopie erstellen")};
        for (int i = 0; i < ausrichtungen.length; i++) {
            menus[2].add(ausrichtungen[i]);
            ausrichtungen[i].addActionListener(this);
            if (i == 3)
                menus[2].addSeparator();
        }

        JMenuItem[] figuren = {new JMenuItem("Quadrat"), new JMenuItem("Kreis"), new JMenuItem("Oval"), new JMenuItem("Blinker"), new JMenuItem("Uhr"), new JMenuItem("Glider"), new JMenuItem("Glider Gun")};
        for (int i = 0; i < figuren.length; i++) {
            menus[3].add(figuren[i]);
            figuren[i].addActionListener(this);
            if (i == 2) {
                menus[3].addSeparator();
            }
        }

        JMenuBar menuBar = new JMenuBar();
        for (int x = 0; x < menus.length; x++) {
            menuBar.add(menus[x]);
        }
        setJMenuBar(menuBar);
        df.addChild(this, 20, 20);

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                super.internalFrameClosing(e);
                model.deleteObserver(view);
            }
        });
    }

    /**
     * actionPerformed Methode zur Unterscheidung der verschiedenen Actions
     *
     * @param evt
     */
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if (source instanceof JMenuItem) {
            switch (((JMenuItem) source).getText()) {

                //Modi
                case "Laufen":
                    view.setMalenIsActive(false);
                    if (!model.isLäuft()) {
                        new Thread(() -> {
                            model.setLäuft(true);
                            while (model.isLäuft() && model.countObservers() != 0) {
                                model.generateNextGen();
                                try {
                                    Thread.sleep(model.getSleeptime());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                    break;
                case "Setzen":
                    view.setMalenIsActive(false);
                    model.setLäuft(false);
                    break;
                case "Malen":
                    view.setMalenIsActive(true);
                    model.setLäuft(false);
                    break;
                case "Next Gen":
                    model.generateNextGen();
                    break;
                case "ClearAll":
                    model.clearAll();
                    break;


                //Geschwindigkeit
                case "Schneller":
                    model.lowerSleeptime();
                    break;
                case "Langsamer":
                    model.higherSleeptime();
                    break;
                case "Reset":
                    model.setSleeptime(250);
                    break;


                //Fensterausrichtung
                case "Norden":
                    new ChildFrame(mydesk, model, GameOfLifeViewer.OrientationT.NORDEN);
                    break;
                case "Osten":
                    new ChildFrame(mydesk, model, GameOfLifeViewer.OrientationT.OSTEN);
                    break;
                case "Sueden":
                    new ChildFrame(mydesk, model, GameOfLifeViewer.OrientationT.SUEDEN);
                    break;
                case "Westen":
                    new ChildFrame(mydesk, model, GameOfLifeViewer.OrientationT.WESTEN);
                    break;
                case "Kopie erstellen":
                    new ChildFrame(mydesk, new GameOfLifeModel(model), view.getOrientation());
                    break;


                //Figuren
                case "Quadrat":
                    model.clearAll();
                    model.setAlive(0, 0);
                    model.setAlive(0, 1);
                    model.setAlive(1, 0);
                    model.setAlive(1, 1);
                    break;
                case "Kreis":
                    model.clearAll();
                    model.setAlive(0, 1);
                    model.setAlive(1, 0);
                    model.setAlive(1, 2);
                    model.setAlive(2, 1);
                    break;
                case "Oval":
                    model.clearAll();
                    model.setAlive(0, 1);
                    model.setAlive(1, 0);
                    model.setAlive(2, 0);
                    model.setAlive(1, 2);
                    model.setAlive(2, 2);
                    model.setAlive(3, 1);
                    break;
                case "Blinker":
                    model.clearAll();
                    model.setAlive(0, 1);
                    model.setAlive(1, 1);
                    model.setAlive(2, 1);
                    break;
                case "Uhr":
                    model.clearAll();
                    model.setAlive(0, 1);
                    model.setAlive(1, 1);
                    model.setAlive(2, 0);
                    model.setAlive(2, 0);
                    model.setAlive(2, 2);
                    model.setAlive(3, 2);
                    model.setAlive(1, 3);
                    break;
                case "Glider":
                    model.clearAll();
                    model.setAlive(0, 1);
                    model.setAlive(1, 2);
                    model.setAlive(2, 0);
                    model.setAlive(2, 1);
                    model.setAlive(2, 2);
                    break;
                case "Glider Gun":
                    model.clearAll();
                    model.setAlive(2, 24);
                    model.setAlive(2, 25);
                    model.setAlive(3, 24);
                    model.setAlive(3, 26);
                    model.setAlive(4, 12);
                    model.setAlive(4, 25);
                    model.setAlive(4, 26);
                    model.setAlive(4, 27);
                    model.setAlive(5, 9);
                    model.setAlive(5, 10);
                    model.setAlive(5, 11);
                    model.setAlive(5, 12);
                    model.setAlive(5, 16);
                    model.setAlive(5, 17);
                    model.setAlive(5, 26);
                    model.setAlive(5, 27);
                    model.setAlive(5, 28);
                    model.setAlive(5, 35);
                    model.setAlive(5, 36);
                    model.setAlive(6, 8);
                    model.setAlive(6, 9);
                    model.setAlive(6, 10);
                    model.setAlive(6, 11);
                    model.setAlive(6, 15);
                    model.setAlive(6, 16);
                    model.setAlive(6, 25);
                    model.setAlive(6, 26);
                    model.setAlive(6, 27);
                    model.setAlive(6, 35);
                    model.setAlive(6, 36);
                    model.setAlive(7, 1);
                    model.setAlive(7, 2);
                    model.setAlive(7, 8);
                    model.setAlive(7, 11);
                    model.setAlive(7, 15);
                    model.setAlive(7, 16);
                    model.setAlive(7, 18);
                    model.setAlive(7, 20);
                    model.setAlive(7, 21);
                    model.setAlive(7, 24);
                    model.setAlive(7, 26);
                    model.setAlive(8, 1);
                    model.setAlive(8, 2);
                    model.setAlive(8, 8);
                    model.setAlive(8, 9);
                    model.setAlive(8, 10);
                    model.setAlive(8, 11);
                    model.setAlive(8, 16);
                    model.setAlive(8, 18);
                    model.setAlive(8, 20);
                    model.setAlive(8, 21);
                    model.setAlive(8, 24);
                    model.setAlive(8, 25);
                    model.setAlive(9, 9);
                    model.setAlive(9, 10);
                    model.setAlive(9, 11);
                    model.setAlive(9, 12);
                    model.setAlive(9, 16);
                    model.setAlive(9, 18);
                    model.setAlive(9, 19);
                    model.setAlive(10, 12);
                    break;
            }
        }
    }
}
