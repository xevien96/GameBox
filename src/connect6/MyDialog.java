package connect6;

import javax.swing.*;
import java.awt.*;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */

/**
 * Öffnet ein Dialogfenster über dem "Parentfenster", welches beim klicken auf den OK-Button sich und das Parentfenster schließt.
 * Der mitgegebene String wird im Dialogfenster ausgegeben.
 */
public class MyDialog extends JDialog {
    public MyDialog(JInternalFrame parent, String text) {
        setTitle("Connect6");
        setModal(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        cp.add(new JLabel(text));
        JButton ok = new JButton("OK");
        ok.addActionListener(e -> {
            parent.dispose();
            dispose();
        });
        cp.add(ok);
        setSize(175, 100);
        setLocationRelativeTo(parent);
    }
}
