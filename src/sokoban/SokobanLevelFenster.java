package sokoban;


import hauptmenü.DesktopFrame;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SokobanLevelFenster extends JInternalFrame {
    private DesktopFrame myDesk;
    private Level level;

    private Container cp = getContentPane();
    private SokobanViewer p1;

    private KeyListener k1 = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                level.moveUp();
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                level.moveDown();
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                level.moveLeft();
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                level.moveRight();
            }
            if (e.getKeyCode() == KeyEvent.VK_U) {
                level.undo();
            }
        }
    };

    public SokobanLevelFenster(DesktopFrame df, Level lvl) {
        super(lvl.toString(), true, true, true, true);
        myDesk = df;
        level = lvl;
        setSize(400, 400);
        initp1();

        addKeyListener(k1);

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
}
