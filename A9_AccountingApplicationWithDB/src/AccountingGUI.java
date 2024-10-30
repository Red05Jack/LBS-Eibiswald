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

    private JTextField betragField;
    private JTextField infoField;
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
        inputPanel.setLayout(new GridLayout(9, 1, 10, 10));  // Schema angepasst
        inputPanel.setPreferredSize(new Dimension(300, 0));  // Festgelegte Breite des rechten Panels

        // Betrag Label und Eingabefeld
        inputPanel.add(new JLabel("Betrag:"));
        betragField = new JTextField();
        inputPanel.add(betragField);

        // Info Label und Eingabefeld
        inputPanel.add(new JLabel("Info:"));
        infoField = new JTextField();
        inputPanel.add(infoField);

        // Kategorie Button und ComboBox
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new GridLayout(1, 2));
        JButton newCategoryButton = new JButton("New Kategorie");
        categoryPanel.add(newCategoryButton);

        categoryComboBox = new JComboBox<>();
        categoryPanel.add(categoryComboBox);
        inputPanel.add(new JLabel("Kategorie:"));
        inputPanel.add(categoryPanel);

        // Button für "CLEAR", "LOAD", "SAVE" und "DELETE"
        JButton clearButton = new JButton("CLEAR");
        JButton loadButton = new JButton("LOAD");
        JButton saveButton = new JButton("SAVE");
        JButton deleteButton = new JButton("DELETE");

        inputPanel.add(clearButton);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        inputPanel.add(buttonPanel);
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
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInputs();  // Neue Methode zum Leeren der Felder und Zurücksetzen der Tabellenauswahl
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSelectedBooking();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBooking();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBooking();
            }
        });

        newCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewCategory();
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

    // Neue Kategorie hinzufügen
    private void addNewCategory() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        // Name der Kategorie
        panel.add(new JLabel("Name (max 100 Zeichen):"));
        JTextField nameField = new JTextField();
        panel.add(nameField);

        // Kürzel der Kategorie
        panel.add(new JLabel("Kürzel (max 5 Zeichen):"));
        JTextField kurzField = new JTextField();
        panel.add(kurzField);

        // Einnahme oder Ausgabe auswählen
        panel.add(new JLabel("Typ:"));
        JComboBox<String> einAusComboBox = new JComboBox<>(new String[] {"Einnahme", "Ausgabe"});
        panel.add(einAusComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Neue Kategorie erstellen", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String kurz = kurzField.getText();
            boolean isEinnahme = einAusComboBox.getSelectedItem().equals("Einnahme");

            if (name.length() <= 100 && kurz.length() <= 5) {
                accounting.addCategory(name, kurz, !isEinnahme);  // "isEinnahme = false" bedeutet Ausgabe
                loadCategoriesIntoComboBox();  // Nach dem Hinzufügen neu laden
            } else {
                JOptionPane.showMessageDialog(this, "Der Name darf max. 100 Zeichen und das Kürzel max. 5 Zeichen haben.");
            }
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

    // Buchung speichern
    private void saveBooking() {
        // Überprüfen, ob eine Kategorie ausgewählt wurde
        if (categoryComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Kategorie aus.");
            return;
        }

        String info = infoField.getText();
        String category = (String) categoryComboBox.getSelectedItem();

        // Betrag formatieren, damit sowohl "," als auch "." erlaubt sind
        String betragText = betragField.getText().replace(",", ".");
        double betrag;
        try {
            betrag = Double.parseDouble(betragText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ungültiger Betrag.");
            return;
        }

        long katId = accounting.getCategoryIdByName(category);

        // Falls eine Zeile in der Tabelle ausgewählt ist, wird sie aktualisiert, andernfalls wird eine neue Buchung erstellt
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            // Neue Buchung mit aktuellem Zeitstempel erstellen
            accounting.addBooking(new Timestamp(System.currentTimeMillis()), info, betrag, katId);
        } else {
            // Bestehende Buchung aktualisieren
            long id = (Long) tableModel.getValueAt(selectedRow, 0);
            Booking existingBooking = accounting.getBookingById(id);  // Hole die existierende Buchung

            // Aktualisiere Buchung mit dem vorhandenen Datum
            accounting.updateBooking(id, existingBooking.getDatumZeit(), info, betrag, katId);
        }
        loadBookingsIntoTable(); // Tabelle neu laden
        clearInputs();  // Felder leeren nach dem Speichern
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

    // Buchung laden
    private void loadSelectedBooking() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Buchung aus, um sie zu laden.");
            return;
        }

        long id = (Long) tableModel.getValueAt(selectedRow, 0);
        Booking booking = accounting.getBookingById(id);  // Holen Sie die Buchung basierend auf der ID

        betragField.setText(String.valueOf(booking.getBetrag()));
        infoField.setText(booking.getInfo());
        Category category = accounting.getCategoryById(booking.getKatId());
        categoryComboBox.setSelectedItem(category.getName());
    }

    // Eingabefelder leeren und Tabellenauswahl zurücksetzen
    private void clearInputs() {
        betragField.setText("");
        infoField.setText("");
        categoryComboBox.setSelectedIndex(-1);  // Keine Auswahl in der Kategorie
        table.clearSelection();  // Auswahl in der Tabelle aufheben
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
