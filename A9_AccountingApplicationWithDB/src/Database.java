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

    // Methode, um eine neue Kategorie hinzuzufügen
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

    // NEU: Kategorie nach ID abrufen
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

    // NEU: Kategorie-ID anhand des Namens abrufen
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
