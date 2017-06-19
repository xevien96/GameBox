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
            if(e.equals(KeyEvent.VK_W)){
                level.moveUp();
            }
            if(e.equals(KeyEvent.VK_S)){
                level.moveDown();
            }
            if(e.equals(KeyEvent.VK_A)){
                level.moveLeft();
            }
            if(e.equals(KeyEvent.VK_D)){
                level.moveRight();
            }
            if(e.equals(KeyEvent.VK_U)){
                level.undo();
            }
        }
    };

    public SokobanLevelFenster(DesktopFrame df, Level lvl) {
        super(lvl.toString(), true, true, true, true);
        myDesk = df;
        level = lvl;
        initp1();

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                super.internalFrameClosing(e);
                level.deleteObserver(p1);
            }
        });
    }

    private void initp1(){
        p1 = new SokobanViewer(level);
        cp.add(p1);
    }
}
