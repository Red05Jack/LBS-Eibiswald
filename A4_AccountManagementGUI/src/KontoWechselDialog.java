import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class KontoWechselDialog extends JDialog {
    private JComboBox<Konto> kontoComboBox;
    private JButton oeffnenButton;

    public KontoWechselDialog(BankForm parent, List<Konto> konten) {
        super(parent, "Konto wechseln", true);
        setLayout(new BorderLayout());
        setSize(300, 150);

        kontoComboBox = new JComboBox<>(konten.toArray(new Konto[0]));
        oeffnenButton = new JButton("Öffnen");

        add(kontoComboBox, BorderLayout.CENTER);
        add(oeffnenButton, BorderLayout.SOUTH);

        // ActionListener zum Konto wechseln
        oeffnenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Konto ausgewaehltesKonto = (Konto) kontoComboBox.getSelectedItem();
                parent.setAktuellesKonto(ausgewaehltesKonto);
                dispose();
            }
        });
    }
}
