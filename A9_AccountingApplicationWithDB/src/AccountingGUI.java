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

    // Suchfelder
    private JTextField searchDateFromField;
    private JTextField searchDateToField;
    private JComboBox<String> searchCategoryComboBox;
    private JComboBox<String> searchEinAusComboBox;
    private JTextField searchInfoField;

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
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);  // Mehrfachauswahl erlauben

        // Tabelle mit Scrollpane (mit Margin)
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Margin: 10px um die Tabelle
        add(tableScrollPane, BorderLayout.CENTER);

        // Eingabebereich rechts (mit Margin)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(9, 1, 10, 10));  // Schema angepasst
        inputPanel.setPreferredSize(new Dimension(300, 0));  // Festgelegte Breite des rechten Panels
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Margin: 10px um den Eingabebereich

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

        // Filter Bereich unten (mit Margin)
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridLayout(1, 8, 10, 10));  // 1 Zeile für alle Suchfelder und Buttons
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Margin: 10px um den Filterbereich

        // Datum von
        searchDateFromField = new JTextField("Datum von");
        searchDateFromField.setForeground(Color.GRAY);
        searchDateFromField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchDateFromField.getText().equals("Datum von")) {
                    searchDateFromField.setText("");
                    searchDateFromField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchDateFromField.getText().isEmpty()) {
                    searchDateFromField.setText("Datum von");
                    searchDateFromField.setForeground(Color.GRAY);
                }
            }
        });
        filterPanel.add(searchDateFromField);

        // Datum bis
        searchDateToField = new JTextField("Datum bis");
        searchDateToField.setForeground(Color.GRAY);
        searchDateToField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchDateToField.getText().equals("Datum bis")) {
                    searchDateToField.setText("");
                    searchDateToField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchDateToField.getText().isEmpty()) {
                    searchDateToField.setText("Datum bis");
                    searchDateToField.setForeground(Color.GRAY);
                }
            }
        });
        filterPanel.add(searchDateToField);

        // Kategorie Dropdown
        searchCategoryComboBox = new JComboBox<>();
        searchCategoryComboBox.addItem("Alle Kategorien");  // "Alle Kategorien" als Platzhalter
        filterPanel.add(searchCategoryComboBox);

        // Ein/Ausgabe Dropdown
        searchEinAusComboBox = new JComboBox<>(new String[]{"Ein/Ausgabe", "Einnahme", "Ausgabe"});
        filterPanel.add(searchEinAusComboBox);

        // Info
        searchInfoField = new JTextField("Info");
        searchInfoField.setForeground(Color.GRAY);
        searchInfoField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchInfoField.getText().equals("Info")) {
                    searchInfoField.setText("");
                    searchInfoField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchInfoField.getText().isEmpty()) {
                    searchInfoField.setText("Info");
                    searchInfoField.setForeground(Color.GRAY);
                }
            }
        });
        filterPanel.add(searchInfoField);

        // Such- und Clear-Button in der gleichen Zeile
        JButton searchButton = new JButton("SEARCH");
        JButton clearSearchButton = new JButton("CLEAR");

        filterPanel.add(searchButton);
        filterPanel.add(clearSearchButton);

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
                deleteSelectedBookings();  // Mehrere Zeilen löschen
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
                searchBookings();
            }
        });

        clearSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSearchFields();  // Methode zum Zurücksetzen der Felder
                searchBookings();
            }
        });
    }

    // Kategorien in das ComboBox laden
    private void loadCategoriesIntoComboBox() {
        List<Category> categories = accounting.getAllCategories();
        categoryComboBox.removeAllItems();
        searchCategoryComboBox.removeAllItems();  // Auch in der Suche aktualisieren
        searchCategoryComboBox.addItem("Alle Kategorien");   // "Alle Kategorien" als Standardwert hinzufügen
        for (Category category : categories) {
            categoryComboBox.addItem(category.getName());
            searchCategoryComboBox.addItem(category.getName());  // Für die Suche
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
        JComboBox<String> einAusComboBox = new JComboBox<>(new String[]{"Einnahme", "Ausgabe"});
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

    // Suchfelder und Platzhalter zurücksetzen
    private void clearSearchFields() {
        searchDateFromField.setText("Datum von");
        searchDateFromField.setForeground(Color.GRAY);

        searchDateToField.setText("Datum bis");
        searchDateToField.setForeground(Color.GRAY);

        searchCategoryComboBox.setSelectedIndex(0);  // Setzt auf "Alle Kategorien"
        searchEinAusComboBox.setSelectedIndex(0);    // Setzt auf "Ein/Ausgabe"

        searchInfoField.setText("Info");
        searchInfoField.setForeground(Color.GRAY);
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

    // Buchungen anhand der Suchkriterien filtern
    private void searchBookings() {
        // Suchkriterien abrufen
        String dateFrom = searchDateFromField.getText().equals("Datum von") ? "" : searchDateFromField.getText();
        String dateTo = searchDateToField.getText().equals("Datum bis") ? "" : searchDateToField.getText();
        String selectedCategory = searchCategoryComboBox.getSelectedItem().toString().equals("Alle Kategorien") ? "" : searchCategoryComboBox.getSelectedItem().toString();
        String selectedEinAus = searchEinAusComboBox.getSelectedItem().toString().equals("Ein/Ausgabe") ? "" : searchEinAusComboBox.getSelectedItem().toString();
        String infoKeyword = searchInfoField.getText().equals("Info") ? "" : searchInfoField.getText();

        // Filter-Logik implementieren
        List<Booking> filteredBookings = accounting.searchBookings(dateFrom, dateTo, selectedCategory, selectedEinAus, infoKeyword);

        // Tabelle leeren und gefilterte Buchungen hinzufügen
        tableModel.setRowCount(0);
        for (Booking booking : filteredBookings) {
            Category category = accounting.getCategoryById(booking.getKatId());
            boolean isIncome = !category.isEinAus();  // Annahme: 'Ein_Aus = false' für Einnahmen, 'true' für Ausgaben

            Object[] rowData = {
                    booking.getId(),
                    category.getName(),
                    isIncome ? booking.getBetrag() : "",
                    !isIncome ? booking.getBetrag() : "",
                    booking.getDatumZeit(),
                    booking.getInfo()
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

    // Mehrere Buchungen löschen
    private void deleteSelectedBookings() {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie mindestens eine Buchung aus, um sie zu löschen.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this, "Sind Sie sicher, dass Sie die ausgewählten Buchungen löschen möchten?", "Löschen bestätigen", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            for (int row : selectedRows) {
                long id = (Long) tableModel.getValueAt(row, 0);
                accounting.deleteBooking(id);
            }
            loadBookingsIntoTable();
        }
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
