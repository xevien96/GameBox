package sokoban;


import hauptmenü.DesktopFrame;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SokobanLevelFenster extends JInternalFrame {
    private DesktopFrame myDesk;
    private Level level;

    private Container cp = getContentPane();
    private SokobanViewer p1;
    private JPanel p2;

    public SokobanLevelFenster(DesktopFrame df, Level lvl) {
        super(lvl.toString(), true, true, true, true);
        myDesk = df;
        level = lvl;
        setSize(400, 400);
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
        JButton up = new JButton("UP");
        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.moveUp();
                System.out.println(level.getMoves());
            }
        });
        JButton down = new JButton("DOWN");
        down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.moveDown();
                System.out.println(level.getMoves());
            }
        });
        JButton left = new JButton("LEFT");
        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.moveLeft();
                System.out.println(level.getMoves());
            }
        });
        JButton right = new JButton("RIGHT");
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.moveRight();
                System.out.println(level.getMoves());
            }
        });
        JButton undo = new JButton("UNDO");
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.undo();
                System.out.println(level.getMoves());
            }
        });
        p2.add(up);
        p2.add(down);
        p2.add(left);
        p2.add(right);
        p2.add(undo);
        cp.add(p2);
    }
}
