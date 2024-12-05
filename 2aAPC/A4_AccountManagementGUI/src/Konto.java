public class Konto {
    private String inhaber;
    private String iabn;
    private double kontostand;

    public Konto(String inhaber, String iabn, double kontostand) {
        this.inhaber = inhaber;
        this.iabn = iabn;
        this.kontostand = kontostand;
    }

    public String getInhaber() {
        return inhaber;
    }

    public String getIabn() {
        return iabn;
    }

    public double getKontostand() {
        return kontostand;
    }

    public void setKontostand(double kontostand) {
        this.kontostand = kontostand;
    }

    @Override
    public String toString() {
        return "Konto von " + inhaber;
    }
}
