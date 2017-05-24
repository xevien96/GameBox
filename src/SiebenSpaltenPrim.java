import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SiebenSpaltenPrim extends JPanel{

    public SiebenSpaltenPrim(){
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JButton schliessenButton = new JButton("Schließen");
        schliessenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        add(textArea, BorderLayout.CENTER);
    }
}
