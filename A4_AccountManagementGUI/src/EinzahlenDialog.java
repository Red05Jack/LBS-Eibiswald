import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EinzahlenDialog extends JDialog {
    private JTextField betragField;
    private JButton okButton;

    public EinzahlenDialog(JFrame parent) {
        super(parent, "Einzahlen", true);
        setLayout(new GridLayout(2, 2));
        setSize(300, 100);

        add(new JLabel("Betrag:"));
        betragField = new JTextField();
        add(betragField);

        okButton = new JButton("OK");
        add(okButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String betrag = betragField.getText();
                JOptionPane.showMessageDialog(parent, betrag + " € eingezahlt");
                dispose();
            }
        });
    }
}

