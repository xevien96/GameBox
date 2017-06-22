package mvcviewer;

/**
 * @author Johann Helbig, Marc Brandt, Albert Renz
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MVCexample extends JInternalFrame {        // Das GUI-Programm
    JSlider sa, sb, sc, sd;                // Controller
    JPanel pa;
    GraphQView view2;
    GraphQView view3;

    private Color[] colors1 = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.BLACK, Color.ORANGE};
    private Color[] colors2 = new Color[]{Color.MAGENTA, Color.CYAN, Color.YELLOW, Color.DARK_GRAY, Color.PINK};
    private int color1 = 0;
    private int color2 = 0;

    public MVCexample() {
        super("MVC example", false, true, false, true);
        setSize(500, 700);
        init();
    }

    public void init() {
        Container cp = getContentPane();            // Fenster-Container
        cp.setLayout(new GridLayout(8, 1, 10, 10));        // 5x1-Grid, 10-er Abstaende

        final Qpolynom p = new Qpolynom(1, 2, 3, 4);        // das Modell

        sa = new JSlider(SwingConstants.HORIZONTAL, -10, 10, 1);    // Erzeugung
        sb = new JSlider(SwingConstants.HORIZONTAL, -10, 10, 2);    //   der Controller
        sc = new JSlider(SwingConstants.HORIZONTAL, -10, 10, 3);  //
        sd = new JSlider(SwingConstants.HORIZONTAL, -10, 10, 4);

        initpa();

        sa.setMajorTickSpacing(10);            // Parameter
        sa.setMinorTickSpacing(1);
        sa.setSnapToTicks(true);
        sb.setMajorTickSpacing(10);
        sb.setMinorTickSpacing(1);
        sb.setSnapToTicks(true);
        sc.setMajorTickSpacing(10);
        sc.setMinorTickSpacing(1);
        sc.setSnapToTicks(true);
        sd.setMajorTickSpacing(10);
        sd.setMinorTickSpacing(1);
        sd.setSnapToTicks(true);
        sa.setPaintTicks(true);
        sb.setPaintTicks(true);
        sc.setPaintTicks(true);
        sd.setPaintTicks(true);

        sa.setPaintLabels(true);                // Parameter
        sb.setPaintLabels(true);
        sc.setPaintLabels(true);
        sd.setPaintLabels(true);

        sa.setPreferredSize(new Dimension(400, 70));
        sb.setPreferredSize(new Dimension(400, 70));
        sc.setPreferredSize(new Dimension(400, 70));
        sd.setPreferredSize(new Dimension(400, 70));

        sa.setBorder(new TitledBorder("Konstante"));            // Border fuer
        sb.setBorder(new TitledBorder("Linearer Koeffizient"));    //    Schiebe-
        sc.setBorder(new TitledBorder("Quadratischer Koeffizient"));    //    Regler
        sd.setBorder(new TitledBorder("Kubischer Koeffizient"));

        // Listener, i.Kl.
        sa.addChangeListener(evt -> {
            JSlider source = (JSlider) evt.getSource();
            if (!source.getValueIsAdjusting()) {
                p.setConstant(source.getValue());    // set... benutzen
            }
        });

        // Listener, i.Kl.
        sb.addChangeListener(evt -> {
            JSlider source = (JSlider) evt.getSource();
            if (!source.getValueIsAdjusting()) {
                p.setLinear(source.getValue());    // set... benutzen
            }
        });
        // Listener, i.Kl.
        sc.addChangeListener(evt -> {
            JSlider source = (JSlider) evt.getSource();
            if (!source.getValueIsAdjusting()) {
                p.setQuadratic(source.getValue());    // set... benutzen
            }
        });
        // Listener, i.Kl.
        sd.addChangeListener(evt -> {
            JSlider source = (JSlider) evt.getSource();
            if (!source.getValueIsAdjusting()) {
                p.setKubik(source.getValue());    // set... benutzen
            }
        });

        TextQView view1 = new TextQView(p);            // 1. View
        view2 = new GraphQView(p, -5, 5, Color.red);            // nach Uebung
        view3 = new GraphQView(p, -15, 15, Color.magenta);            // 2. View

        p.addObserver(view1);            // Views als Observer registrieren
        p.addObserver(view2);
        p.addObserver(view3);

        cp.add(view1);                // Views zum Fenster hinzufuegen
        cp.add(view2);
        cp.add(view3);


        cp.add(pa);
        cp.add(sa);                // Controller hinzufuegen
        cp.add(sb);
        cp.add(sc);
        cp.add(sd);

    } // end init

    /**
     * Initialisieren der einzelnen Variablen des Panels
     */
    public void initpa() {
        pa = new JPanel();
        pa.setLayout(new GridLayout(1, 2));
        JTextField v2minx = new JTextField("-5", 3);
        JTextField v2maxx = new JTextField("5", 3);
        JTextField v3minx = new JTextField("-15", 3);
        JTextField v3maxx = new JTextField("15", 3);

        JButton v2farbe = new JButton();
        JButton v3farbe = new JButton();
        v2farbe.setPreferredSize(new Dimension(30, 30));
        v3farbe.setPreferredSize(new Dimension(30, 30));

        v2farbe.setBackground(colors1[color1]);
        v3farbe.setBackground(colors2[color2]);

        JPanel pa1 = new JPanel();
        JPanel pa2 = new JPanel();

        pa1.setBorder(new TitledBorder("Viewer 2"));
        pa2.setBorder(new TitledBorder("Viewer 3"));

        v2minx.addActionListener(e -> {
            view2.setMinx(Integer.parseInt(v2minx.getText().trim()));
            repaint();
        });
        v2maxx.addActionListener(e -> {
            view2.setMaxx(Integer.parseInt(v2maxx.getText().trim()));
            repaint();
        });

        v2farbe.addActionListener(e -> {
            v2farbe.setBackground(colors1[++color1 % colors1.length]);
            view2.setColor(v2farbe.getBackground());
            repaint();
        });

        v3minx.addActionListener(e -> {
            view3.setMinx(Integer.parseInt(v3minx.getText().trim()));
            repaint();
        });
        v3maxx.addActionListener(e -> {
            view3.setMaxx(Integer.parseInt(v3maxx.getText().trim()));
            repaint();
        });

        v3farbe.addActionListener(e -> {
            v3farbe.setBackground(colors2[++color2 % colors2.length]);
            view3.setColor(v3farbe.getBackground());
            repaint();
        });

        pa1.add(v2minx);
        pa1.add(v2maxx);
        pa1.add(v2farbe);
        pa2.add(v3minx);
        pa2.add(v3maxx);
        pa2.add(v3farbe);

        pa.add(pa1);
        pa.add(pa2);
    }
} // end MVCexample