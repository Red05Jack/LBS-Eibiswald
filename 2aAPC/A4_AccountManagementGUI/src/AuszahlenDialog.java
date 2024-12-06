
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuszahlenDialog extends JDialog {
    private JTextField betragField;
    private JButton okButton;

    public AuszahlenDialog(JFrame parent) {
        super(parent, "Auszahlen", true);
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
                JOptionPane.showMessageDialog(parent, betrag + " € ausgezahlt");
                dispose();
            }
        });
    }

    // Getter for betragField
    public JTextField getBetragField() {
        return betragField;
    }
}
