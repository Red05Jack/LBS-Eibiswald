import java.sql.Timestamp;

public class Booking {
    private long id;
    private Timestamp datumZeit;
    private String info;
    private double betrag;
    private long katId;

    // Konstruktor
    public Booking(long id, Timestamp datumZeit, String info, double betrag, long katId) {
        this.id = id;
        this.datumZeit = datumZeit;
        this.info = info;
        this.betrag = betrag;
        this.katId = katId;
    }

    // Getter und Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getDatumZeit() {
        return datumZeit;
    }

    public void setDatumZeit(Timestamp datumZeit) {
        this.datumZeit = datumZeit;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public long getKatId() {
        return katId;
    }

    public void setKatId(long katId) {
        this.katId = katId;
    }

    // toString-Methode zur einfachen Darstellung
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", datumZeit=" + datumZeit +
                ", info='" + info + '\'' +
                ", betrag=" + betrag +
                ", katId=" + katId +
                '}';
    }
}
