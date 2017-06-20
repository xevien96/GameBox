package sokoban;


import hauptmenü.DesktopFrame;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Johann Helbig, Marc Brandt, Albert Renz
 */
public class SokobanLevelFenster extends JInternalFrame {
    private DesktopFrame myDesk;
    private Level level;

    private Container cp = getContentPane();
    private SokobanViewer p1;
    private JPanel p2;

    public SokobanLevelFenster(DesktopFrame df, Level lvl) {
        super(lvl.toString(), true, true, true, true);
        myDesk = df;
        level = new Level(lvl);
        setSize(500, 500);
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        initp1();
        initp2();

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                super.internalFrameClosing(e);
                level.deleteObserver(p1);
            }
        });
    }

    private void initp1() {
        p1 = new SokobanViewer(level);
        cp.add(p1);
    }

    private void initp2(){
        p2 = new JPanel();
        p2.setLayout(new GridLayout(1, 2));
        JButton up = new JButton("UP");
        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.setPrevious(new Level(level));
                level.move('u');
                levelGelöst();
            }
        });
        JButton down = new JButton("DOWN");
        down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.setPrevious(new Level(level));
                level.move('d');
                levelGelöst();
            }
        });
        JButton left = new JButton("LEFT");
        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.setPrevious(new Level(level));
                level.move('l');
                levelGelöst();
            }
        });
        JButton right = new JButton("RIGHT");
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.setPrevious(new Level(level));
                level.move('r');
                levelGelöst();
            }
        });
        JButton undo = new JButton("UNDO");
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.undo();
            }
        });
        //TODO speichern funktion
        JButton speichern = new JButton("Save game");
        speichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //level.speichern();
            }
        });
        speichern.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton beenden = new JButton("End game");
        beenden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        beenden.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel p20 = new JPanel(new GridLayout(3, 3));
        p20.add(new JButton());
        p20.add(up);
        p20.add(new JButton());
        p20.add(left);
        p20.add(undo);
        p20.add(right);
        p20.add(new JButton());
        p20.add(down);
        p20.add(new JButton());

        JPanel p21 = new JPanel();
        p21.setLayout(new BoxLayout(p21, BoxLayout.Y_AXIS));
        p21.add(Box.createVerticalGlue());
        p21.add(speichern);
        p21.add(Box.createVerticalGlue());
        p21.add(beenden);
        p21.add(Box.createVerticalGlue());

        p2.add(p20);
        p2.add(p21);
        cp.add(p2);
    }

    private void levelGelöst() {
        if (level.isSolved()) {
            JOptionPane.showInternalMessageDialog(this, "Level gelöst", level.toString(), JOptionPane.INFORMATION_MESSAGE);
            if (level.getLevelNr() >= SokobanStart.getWorldVector(level.getWorldName()).size()) {
                dispose();
            } else {
                myDesk.addChild(new SokobanLevelFenster(myDesk, SokobanStart.getWorldVector(level.getWorldName()).get(level.getLevelNr())), 30, 30);
                dispose();
            }
        }
    }
}
