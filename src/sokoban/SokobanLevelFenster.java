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
        level = new Level(lvl);
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
                level.move('u');
            }
        });
        JButton down = new JButton("DOWN");
        down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.move('d');
            }
        });
        JButton left = new JButton("LEFT");
        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.move('l');
            }
        });
        JButton right = new JButton("RIGHT");
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.move('r');
            }
        });
        p2.add(up);
        p2.add(down);
        p2.add(left);
        p2.add(right);
        cp.add(p2);
    }
}
