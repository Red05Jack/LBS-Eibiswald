import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // GUI im Event Dispatch Thread starten
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Verbindungsinformationen zur Datenbank
                String dbUrl = "jdbc:mysql://localhost:3306/a9_accountingapplicationwithdb"; // Passe die URL, den Benutzernamen und das Passwort an deine Datenbank an
                String user = "root";  // Benutzername für die Datenbank
                String pass = "yourpassword";  // Passwort für die Datenbank

                // AccountingGUI-Instanz erstellen und sichtbar machen
                AccountingGUI gui = new AccountingGUI(dbUrl, user, pass);
                gui.setVisible(true);  // Die GUI sichtbar machen
            }
        });
    }
}
