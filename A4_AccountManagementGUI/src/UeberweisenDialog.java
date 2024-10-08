
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UeberweisenDialog extends JDialog {
    private JTextField zielKontoField;
    private JTextField betragField;
    private JButton ueberweisenButton;

    public UeberweisenDialog(JFrame parent) {
        super(parent, "Überweisen", true);
        setLayout(new GridLayout(3, 2));
        setSize(300, 150);

        add(new JLabel("Zielkonto:"));
        zielKontoField = new JTextField();
        add(zielKontoField);

        add(new JLabel("Betrag:"));
        betragField = new JTextField();
        add(betragField);

        ueberweisenButton = new JButton("Überweisen");
        add(ueberweisenButton);

        ueberweisenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String zielKonto = zielKontoField.getText();
                String betrag = betragField.getText();
                JOptionPane.showMessageDialog(parent, betrag + " € an " + zielKonto + " überwiesen");
                dispose();
            }
        });
    }

    // Getter for zielKontoField
    public JTextField getZielKontoField() {
        return zielKontoField;
    }

    // Getter for betragField
    public JTextField getBetragField() {
        return betragField;
    }
}
