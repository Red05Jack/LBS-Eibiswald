
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

    // Liste zur Speicherung der Historie
    private ArrayList<String> transaktionsHistorie;

    // Constructor for the form
    public BankForm() {
        // Beispielkonten hinzufügen
        konten = new ArrayList<>();
        konten.add(new Konto("Max Mustermann", "0", 0.0));

        // Standardmäßig das erste Konto anzeigen
        aktuellesKonto = konten.get(0);
        transaktionsHistorie = new ArrayList<>();
        kontoAnzeigen(aktuellesKonto);

        // Konto wechseln
        kontoWechselnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KontoWechselDialog dialog = new KontoWechselDialog(BankForm.this, konten);
                dialog.setVisible(true);
            }
        });

        // Konto erstellen
        kontoErstellenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KontoErstellenDialog erstellenDialog = new KontoErstellenDialog(BankForm.this);
                erstellenDialog.setVisible(true);

                // Get the newly created account details
                erstellenDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        String neuerInhaber = erstellenDialog.getNameField().getText();
                        String neueIabn = erstellenDialog.getIabnField().getText();

                        if (!neuerInhaber.isEmpty() && !neueIabn.isEmpty()) {
                            // Add new account to the list
                            Konto neuesKonto = new Konto(neuerInhaber, neueIabn, 0.0);
                            konten.add(neuesKonto);
                            setAktuellesKonto(neuesKonto); // Set the newly created account as the current account
                        }
                    }
                });
            }
        });

        // Einzahlen
        einzahlenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EinzahlenDialog einzahlenDialog = new EinzahlenDialog(BankForm.this);
                einzahlenDialog.setVisible(true);
                double betrag = Double.parseDouble(einzahlenDialog.getBetragField().getText());
                aktuellesKonto.setKontostand(aktuellesKonto.getKontostand() + betrag);
                transaktionsHistorie.add("Einzahlung: " + betrag + "€");
                kontoAnzeigen(aktuellesKonto);
            }
        });

        // Auszahlen
        auszahlenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AuszahlenDialog auszahlenDialog = new AuszahlenDialog(BankForm.this);
                auszahlenDialog.setVisible(true);
                double betrag = Double.parseDouble(auszahlenDialog.getBetragField().getText());
                if (betrag <= aktuellesKonto.getKontostand()) {
                    aktuellesKonto.setKontostand(aktuellesKonto.getKontostand() - betrag);
                    transaktionsHistorie.add("Auszahlung: " + betrag + "€");
                    kontoAnzeigen(aktuellesKonto);
                } else {
                    JOptionPane.showMessageDialog(BankForm.this, "Nicht genug Guthaben!");
                }
            }
        });

        // Historie anzeigen
        historieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistorieDialog historieDialog = new HistorieDialog(BankForm.this, transaktionsHistorie);
                historieDialog.setVisible(true);
            }
        });

        // Überweisen
        ueberweisenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UeberweisenDialog ueberweisenDialog = new UeberweisenDialog(BankForm.this);
                ueberweisenDialog.setVisible(true);
                String zielKontoIABN = ueberweisenDialog.getZielKontoField().getText();
                double betrag = Double.parseDouble(ueberweisenDialog.getBetragField().getText());

                Konto zielKonto = null;
                for (Konto k : konten) {
                    if (k.getIabn().equals(zielKontoIABN)) {
                        zielKonto = k;
                        break;
                    }
                }

                if (zielKonto != null && betrag <= aktuellesKonto.getKontostand()) {
                    aktuellesKonto.setKontostand(aktuellesKonto.getKontostand() - betrag);
                    zielKonto.setKontostand(zielKonto.getKontostand() + betrag);
                    transaktionsHistorie.add("Überweisung: " + betrag + "€ an " + zielKonto.getInhaber());
                    kontoAnzeigen(aktuellesKonto);
                } else {
                    JOptionPane.showMessageDialog(BankForm.this, "Fehler bei der Überweisung!");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bank Management System");
        frame.setSize(600, 420);
        frame.setResizable(false);
        frame.setContentPane(new BankForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Methode zur Anzeige der Kontodaten
    public void kontoAnzeigen(Konto konto) {
        kontoInhaberLabel.setText("Kontoinhaber: " + konto.getInhaber());
        kontoNummerLabel.setText("Kontonummer (IBAN): " + konto.getIabn());
        kontoStandLabel.setText("Kontostand: " + konto.getKontostand() + "€");
    }

    // Methode zum Setzen des aktuellen Kontos
    public void setAktuellesKonto(Konto neuesKonto) {
        this.aktuellesKonto = neuesKonto;
        kontoAnzeigen(neuesKonto);  // Aktualisiere die Anzeige des neuen Kontos
    }
}
