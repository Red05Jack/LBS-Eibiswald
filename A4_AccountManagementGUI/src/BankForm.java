import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BankForm extends JFrame {
    private JPanel mainPanel;
    private JButton kontoWechselnButton;
    private JButton kontoErstellenButton;
    private JLabel kontoInhaberLabel;
    private JLabel kontoNummerLabel;
    private JLabel kontoStandLabel;
    private JButton einzahlenButton;
    private JButton auszahlenButton;
    private JButton historieButton;
    private JButton ueberweisenButton;

    // Liste der Konten
    private ArrayList<Konto> konten;
    private Konto aktuellesKonto;

    // Constructor for the form
    public BankForm() {
        setTitle("Bank Management System");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        // Beispielkonten hinzufügen
        konten = new ArrayList<>();
        konten.add(new Konto("Max Mustermann", "DE1234567890", 1000.00));
        konten.add(new Konto("Maria Müller", "DE0987654321", 2000.00));
        konten.add(new Konto("John Doe", "DE1122334455", 500.00));

        // Standardmäßig das erste Konto anzeigen
        aktuellesKonto = konten.get(0);
        kontoAnzeigen(aktuellesKonto);

        // ActionListener für Konto wechseln
        kontoWechselnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KontoWechselDialog dialog = new KontoWechselDialog(BankForm.this, konten);
                dialog.setVisible(true);
            }
        });

        // Weitere ActionListener für Einzahlen, Auszahlen, etc. können wie zuvor hinzugefügt werden
    }

    // Methode zur Anzeige der Kontodaten
    public void kontoAnzeigen(Konto konto) {
        kontoInhaberLabel.setText("Kontoinhaber: " + konto.getInhaber());
        kontoNummerLabel.setText("Kontonummer (IABN): " + konto.getIabn());
        kontoStandLabel.setText("Kontostand: " + String.format("%.2f", konto.getKontostand()) + " €");
    }

    // Diese Methode wird von KontoWechselDialog aufgerufen, um das Konto zu wechseln
    public void setAktuellesKonto(Konto konto) {
        this.aktuellesKonto = konto;
        kontoAnzeigen(konto);
    }

    public static void main(String[] args) {
        new BankForm();
    }
}
