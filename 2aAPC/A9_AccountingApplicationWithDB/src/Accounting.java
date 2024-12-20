import java.sql.Timestamp;
import java.util.List;

public class Accounting {
    private final Database db;

    // Konstruktor, der eine Verbindung zur Datenbank aufbaut
    public Accounting(String dbUrl, String user, String pass) {
        this.db = new Database(dbUrl, user, pass);
    }

    // Methode, um eine neue Kategorie hinzuzufügen
    public void addCategory(String name, String kurz, boolean einAus) {
        Category category = new Category(0, name, kurz, einAus);
        db.addCategory(category);
    }

    // Methode, um alle Kategorien abzurufen
    public List<Category> getAllCategories() {
        return db.getAllCategories();
    }

    // Methode, um eine Kategorie nach ID zu bekommen
    public Category getCategoryById(long id) {
        return db.getCategoryById(id);
    }

    // Methode, um die Kategorie-ID basierend auf dem Namen zu bekommen
    public long getCategoryIdByName(String name) {
        return db.getCategoryIdByName(name);
    }

    // Methode, um eine neue Buchung hinzuzufügen
    public void addBooking(Timestamp datumZeit, String info, double betrag, long katId) {
        Booking booking = new Booking(0, datumZeit, info, betrag, katId);
        db.addBooking(booking);
    }

    // Methode, um alle Buchungen abzurufen
    public List<Booking> getAllBookings() {
        return db.getAllBookings();
    }

    // Methode, um eine Buchung anhand der ID abzurufen (NEU)
    public Booking getBookingById(long id) {
        return db.getBookingById(id);
    }

    // Methode, um eine bestehende Buchung zu aktualisieren
    public void updateBooking(long id, Timestamp datumZeit, String info, double betrag, long katId) {
        Booking booking = new Booking(id, datumZeit, info, betrag, katId);
        db.updateBooking(booking);
    }

    public List<Booking> searchBookings(String dateFrom, String dateTo, String category, String einAus, String infoKeyword) {
        return db.searchBookings(dateFrom, dateTo, category, einAus, infoKeyword);
    }

    // Methode, um eine Buchung zu löschen
    public void deleteBooking(long id) {
        db.deleteBooking(id);
    }

    // Methode zum Schließen der Datenbankverbindung
    public void close() {
        db.close();
    }
}
