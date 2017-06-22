package sokoban;


import hauptmenü.DesktopFrame;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author Johann Helbig, Marc Brandt, Albert Renz
 */
public class SokobanLevelFenster extends JInternalFrame {
    private final JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "sokoban" + System.getProperty("file.separator") + "Levels" + System.getProperty("file.separator") + "Saved Games");
    private DesktopFrame myDesk;
    private Level level;
    private Container cp = getContentPane();
    private SokobanViewer p1;
    private JPanel p2;
    private Action moveUp = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            level.setPrevious(new Level(level));
            level.move('u');
            levelGelöst();
        }
    };

    private Action moveDown = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            level.setPrevious(new Level(level));
            level.move('d');
            levelGelöst();
        }
    };

    private Action moveLeft = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            level.setPrevious(new Level(level));
            level.move('l');
            levelGelöst();
        }
    };

    private Action moveRight = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            level.setPrevious(new Level(level));
            level.move('r');
            levelGelöst();
        }
    };

    private Action undoAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            level.undo();
        }
    };

    public SokobanLevelFenster(DesktopFrame df, Level lvl) {
        super(lvl.toString(), true, true, true, true);
        myDesk = df;
        level = new Level(lvl);
        setSize(500, 500);
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        initp1();
        initp2();

        initControls();

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                super.internalFrameClosing(e);
                level.deleteObserver(p1);
            }
        });
    }

    private void initControls() {
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("UP"), "moveUp");
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke('w'), "moveUp");
        getActionMap().put("moveUp", moveUp);
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke('s'), "moveDown");
        getActionMap().put("moveDown", moveDown);
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke('a'), "moveLeft");
        getActionMap().put("moveLeft", moveLeft);
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke('d'), "moveRight");
        getActionMap().put("moveRight", moveRight);
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("BACK_SPACE"), "undo");
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke('u'), "undo");
        getActionMap().put("undo", undoAction);
    }

    private void initp1() {
        p1 = new SokobanViewer(level);
        cp.add(p1);
    }

    private void initp2() {
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
                speichern();
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
        p20.add(new JLabel());
        p20.add(up);
        p20.add(new JLabel());
        p20.add(left);
        p20.add(undo);
        p20.add(right);
        p20.add(new JLabel());
        p20.add(down);
        p20.add(new JLabel());

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

    private void speichern() {
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File gameFile = new File(fc.getSelectedFile() + ".ser");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(gameFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(level);
                objectOutputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
