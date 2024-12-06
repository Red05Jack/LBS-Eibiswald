import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection conn;

    // Konstruktor, der eine Verbindung zur Datenbank herstellt
    public Database(String dbUrl, String user, String pass) {
        connect(dbUrl, user, pass);
    }

    // Methode, um die Verbindung zur Datenbank herzustellen
    private void connect(String dbUrl, String user, String pass) {
        try {
            // MySQL JDBC-Treiber laden
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, user, pass);
            System.out.println("Verbindung zur Datenbank erfolgreich!");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL-Treiber nicht gefunden.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Datenbankverbindung fehlgeschlagen.");
            e.printStackTrace();
        }
    }

    // Methode, um eine neue Kategorie hinzuzufügen (NEU)
    public void addCategory(Category category) {
        String query = "INSERT INTO categories (Name, Kurz, Ein_Aus) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, category.getName());
            pstmt.setString(2, category.getKurz());
            pstmt.setBoolean(3, category.isEinAus());
            pstmt.executeUpdate();
            System.out.println("Kategorie erfolgreich hinzugefügt: " + category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methode, um alle Kategorien abzurufen
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Category category = new Category(
                        rs.getLong("ID"),
                        rs.getString("Name"),
                        rs.getString("Kurz"),
                        rs.getBoolean("Ein_Aus")
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Methode, um eine Kategorie nach ID abzurufen
    public Category getCategoryById(long id) {
        String query = "SELECT * FROM categories WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Category(
                        rs.getLong("ID"),
                        rs.getString("Name"),
                        rs.getString("Kurz"),
                        rs.getBoolean("Ein_Aus")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Methode, um die Kategorie-ID anhand des Namens abzurufen
    public long getCategoryIdByName(String name) {
        String query = "SELECT ID FROM categories WHERE Name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // -1 als Fehlerwert
    }

    // Methode, um eine neue Buchung hinzuzufügen
    public void addBooking(Booking booking) {
        String query = "INSERT INTO booking (Datum_Zeit, Info, Betrag, KatID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setTimestamp(1, booking.getDatumZeit());
            pstmt.setString(2, booking.getInfo());
            pstmt.setDouble(3, booking.getBetrag());
            pstmt.setLong(4, booking.getKatId());
            pstmt.executeUpdate();
            System.out.println("Buchung erfolgreich hinzugefügt: " + booking);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methode, um alle Buchungen abzurufen
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM booking";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getLong("ID"),
                        rs.getTimestamp("Datum_Zeit"),
                        rs.getString("Info"),
                        rs.getDouble("Betrag"),
                        rs.getLong("KatID")
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Methode, um eine Buchung nach ID abzurufen (NEU)
    public Booking getBookingById(long id) {
        String query = "SELECT * FROM booking WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Booking(
                        rs.getLong("ID"),
                        rs.getTimestamp("Datum_Zeit"),
                        rs.getString("Info"),
                        rs.getDouble("Betrag"),
                        rs.getLong("KatID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Methode, um eine Buchung zu aktualisieren
    public void updateBooking(Booking booking) {
        String query = "UPDATE booking SET Datum_Zeit = ?, Info = ?, Betrag = ?, KatID = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setTimestamp(1, booking.getDatumZeit());
            pstmt.setString(2, booking.getInfo());
            pstmt.setDouble(3, booking.getBetrag());
            pstmt.setLong(4, booking.getKatId());
            pstmt.setLong(5, booking.getId());
            pstmt.executeUpdate();
            System.out.println("Buchung erfolgreich aktualisiert: " + booking);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> searchBookings(String dateFrom, String dateTo, String category, String einAus, String infoKeyword) {
        List<Booking> bookings = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder("SELECT b.* FROM booking b INNER JOIN categories c ON b.KatID = c.ID WHERE 1=1");

            // Datum von
            if (!dateFrom.isEmpty()) {
                query.append(" AND b.`Datum_Zeit` >= '").append(dateFrom).append("'");
            }

            // Datum bis
            if (!dateTo.isEmpty()) {
                query.append(" AND b.`Datum_Zeit` <= '").append(dateTo).append("'");
            }

            // Kategorie
            if (!category.isEmpty()) {
                long katId = getCategoryIdByName(category);  // Hole die Kategorie-ID
                query.append(" AND b.KatID = ").append(katId);
            }

            // Einnahme oder Ausgabe
            if (!einAus.isEmpty()) {
                boolean isEinnahme = einAus.equals("Einnahme");
                query.append(" AND c.`Ein_Aus` = ").append(isEinnahme ? 0 : 1);  // Nutze den Ein_Aus-Wert aus der Kategorie
            }

            // Info-Schlagwort
            if (!infoKeyword.isEmpty()) {
                query.append(" AND b.Info LIKE '%").append(infoKeyword).append("%'");
            }

            // Führe die Abfrage aus
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());

            // Ergebnisse in Booking-Objekte umwandeln
            while (rs.next()) {
                long id = rs.getLong("ID");
                Timestamp datumZeit = rs.getTimestamp("Datum_Zeit");
                String info = rs.getString("Info");
                double betrag = rs.getDouble("Betrag");
                long katId = rs.getLong("KatID");

                bookings.add(new Booking(id, datumZeit, info, betrag, katId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Methode, um eine Buchung zu löschen
    public void deleteBooking(long id) {
        String query = "DELETE FROM booking WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            System.out.println("Buchung erfolgreich gelöscht. ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Schließen der Verbindung
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Verbindung zur Datenbank geschlossen.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
