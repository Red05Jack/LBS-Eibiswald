import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.List;

public class AccountingGUI extends JFrame {
    private Accounting accounting;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField eingabeField;
    private JTextField zusatzInfoField;
    private JComboBox<String> categoryComboBox;
    private JTextField filterFrom;
    private JTextField filterTo;

    public AccountingGUI(String dbUrl, String user, String pass) {
        accounting = new Accounting(dbUrl, user, pass);
        initComponents();
        loadCategoriesIntoComboBox();
        loadBookingsIntoTable();
    }

    // GUI Komponenten initialisieren
    private void initComponents() {
        setTitle("Einnahmen Ausgaben Rechner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);  // Breitere GUI
        setLayout(new BorderLayout());

        // Tabelle
        String[] columnNames = {"ID", "Bezeichnung", "Einnahmen", "Ausgaben", "Datum", "Info"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        add(tableScrollPane, BorderLayout.CENTER);

        // Eingabebereich rechts
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(10, 2, 10, 10));  // Abstand zwischen den Komponenten erhöht
        inputPanel.setPreferredSize(new Dimension(300, 0));  // Festgelegte Breite des rechten Panels

        inputPanel.add(new JLabel("Eingabe:"));
        eingabeField = new JTextField();
        inputPanel.add(eingabeField);

        inputPanel.add(new JLabel("Zusatzinfo:"));
        zusatzInfoField = new JTextField();
        inputPanel.add(zusatzInfoField);

        inputPanel.add(new JLabel("Kategorie:"));
        categoryComboBox = new JComboBox<>();
        inputPanel.add(categoryComboBox);

        JButton newButton = new JButton("New");
        inputPanel.add(newButton);

        JButton saveButton = new JButton("Save");
        inputPanel.add(saveButton);

        JButton deleteButton = new JButton("Delete");
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.EAST);

        // Filter Bereich unten
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());

        filterPanel.add(new JLabel("Filter von:"));
        filterFrom = new JTextField(10);
        filterPanel.add(filterFrom);

        filterPanel.add(new JLabel("bis:"));
        filterTo = new JTextField(10);
        filterPanel.add(filterTo);

        JButton searchButton = new JButton("Search");
        filterPanel.add(searchButton);

        JButton resetButton = new JButton("Reset");
        filterPanel.add(resetButton);

        add(filterPanel, BorderLayout.SOUTH);

        // Button Aktionen
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewBooking();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBooking();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBooking();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterBookings();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFilters();
            }
        });
    }

    // Kategorien in das ComboBox laden
    private void loadCategoriesIntoComboBox() {
        List<Category> categories = accounting.getAllCategories();
        categoryComboBox.removeAllItems();
        for (Category category : categories) {
            categoryComboBox.addItem(category.getName());
        }
    }

    // Buchungen in die Tabelle laden
    private void loadBookingsIntoTable() {
        List<Booking> bookings = accounting.getAllBookings();
        tableModel.setRowCount(0); // Löscht alle bestehenden Zeilen
        for (Booking booking : bookings) {
            Category category = accounting.getCategoryById(booking.getKatId()); // Hole die Kategorie basierend auf KatID
            boolean isIncome = !category.isEinAus();  // Annahme: 'Ein_Aus = false' für Einnahmen, 'true' für Ausgaben

            Object[] rowData = {
                    booking.getId(),
                    category.getName(),  // Zeige den Namen der Kategorie anstelle der Buchungsinfo
                    isIncome ? booking.getBetrag() : "",  // Wenn Einnahme, Betrag hier, ansonsten leer
                    !isIncome ? booking.getBetrag() : "",  // Wenn Ausgabe, Betrag hier, ansonsten leer
                    booking.getDatumZeit(),
                    booking.getInfo()  // Zeige die Zusatzinfo im "Info"-Feld
            };
            tableModel.addRow(rowData);
        }
    }

    // Neue Buchung erstellen
    private void createNewBooking() {
        String info = eingabeField.getText();
        String zusatzInfo = zusatzInfoField.getText();
        String category = (String) categoryComboBox.getSelectedItem();

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        double betrag = Double.parseDouble(eingabeField.getText());

        // Beispielkategorie-ID (in der echten Anwendung würdest du die Kategorie-ID aus der Datenbank holen)
        long katId = accounting.getCategoryIdByName(category);

        accounting.addBooking(currentTimestamp, info + " - " + zusatzInfo, betrag, katId);
        loadBookingsIntoTable(); // Tabelle neu laden
    }

    // Buchung aktualisieren
    private void updateBooking() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Buchung aus, um sie zu aktualisieren.");
            return;
        }

        long id = (Long) tableModel.getValueAt(selectedRow, 0);
        String info = eingabeField.getText();
        String zusatzInfo = zusatzInfoField.getText();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        double betrag = Double.parseDouble(eingabeField.getText());

        accounting.updateBooking(id, currentTimestamp, info + " - " + zusatzInfo, betrag, 1);
        loadBookingsIntoTable();
    }

    // Buchung löschen
    private void deleteBooking() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Buchung aus, um sie zu löschen.");
            return;
        }

        long id = (Long) tableModel.getValueAt(selectedRow, 0);
        accounting.deleteBooking(id);
        loadBookingsIntoTable();
    }

    // Filter anwenden
    private void filterBookings() {
        String from = filterFrom.getText();
        String to = filterTo.getText();

        // Filter-Logik implementieren
        // Für eine einfache Implementierung könnten Sie die Datenbankabfrage direkt anpassen, um gefilterte Daten zu erhalten

        loadBookingsIntoTable(); // Nach Anwendung der Filter neu laden
    }

    // Filter zurücksetzen
    private void resetFilters() {
        filterFrom.setText("");
        filterTo.setText("");
        loadBookingsIntoTable(); // Tabelle ohne Filter neu laden
    }
}
