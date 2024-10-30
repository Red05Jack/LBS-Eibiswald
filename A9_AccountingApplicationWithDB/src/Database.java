import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private String dbUrl;
    private String user;
    private String pass;
    private Connection conn;

    // Konstruktor, um die Verbindungsdaten zu übergeben
    public Database(String dbUrl, String user, String pass) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.pass = pass;
        connect();
    }

    // Methode, um die Verbindung zur Datenbank herzustellen
    private void connect() {
        try {
            conn = DriverManager.getConnection(dbUrl, user, pass);
            System.out.println("Verbindung zur Datenbank erfolgreich!");
        } catch (SQLException e) {
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

    // Methode, um alle Kategorien anzuzeigen
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

    // Methode, um alle Buchungen anzuzeigen
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
