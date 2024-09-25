import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KontoErstellenDialog extends JDialog {
    private JTextField nameField;
    private JTextField iabnField;
    private JButton erstellenButton;

    public KontoErstellenDialog(JFrame parent) {
        super(parent, "Konto erstellen", true);
        setLayout(new GridLayout(3, 2));
        setSize(300, 150);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("IABN:"));
        iabnField = new JTextField();
        add(iabnField);

        erstellenButton = new JButton("Erstellen");
        add(erstellenButton);

        erstellenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String iabn = iabnField.getText();
                JOptionPane.showMessageDialog(parent, "Konto erstellt: " + name + " (" + iabn + ")");
                dispose();
            }
        });
    }
}
