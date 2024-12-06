public class Category {
    private long id;
    private String name;
    private String kurz;
    private boolean einAus;

    // Konstruktor
    public Category(long id, String name, String kurz, boolean einAus) {
        this.id = id;
        this.name = name;
        this.kurz = kurz;
        this.einAus = einAus;
    }

    // Getter und Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKurz() {
        return kurz;
    }

    public void setKurz(String kurz) {
        this.kurz = kurz;
    }

    public boolean isEinAus() {
        return einAus;
    }

    public void setEinAus(boolean einAus) {
        this.einAus = einAus;
    }

    // toString-Methode zur einfachen Darstellung
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", kurz='" + kurz + '\'' +
                ", einAus=" + einAus +
                '}';
    }
}
