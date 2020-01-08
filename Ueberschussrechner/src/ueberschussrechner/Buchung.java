package ueberschussrechner;

public class Buchung {

    private double ausgaben;
    private double einnahmen;
    private String buchungsdatum;
    private String bemerkung;
    private int buchungsnummer;

    public void buchungAusgaben(int buchungsnummer, String buchungsdatum, String bemerkung, double einnahmen) {
        this.buchungsdatum = buchungsdatum;
        this.buchungsdatum = buchungsdatum;
        this.bemerkung = bemerkung;
        this.einnahmen = einnahmen;
        
    }

    public void buchungEinnahmen(int buchungsnummer, String buchungsdatum, String bemerkung, double ausgaben) {
        this.buchungsdatum = buchungsdatum;
        this.buchungsdatum = buchungsdatum;
        this.bemerkung = bemerkung;
        this.einnahmen = einnahmen;
    }

    public double getAusgaben() {
        return ausgaben;
    }

    public void setAusgaben(double ausgaben) {
        this.ausgaben = ausgaben;
    }

    public double getEinnahmen() {
        return einnahmen;
    }

    public void setEinnahmen(double einnahmen) {
        this.einnahmen = einnahmen;
    }

    public String getBuchungsdatum() {
        return buchungsdatum;
    }

    public void setBuchungsdatum(String buchungsdatum) {
        this.buchungsdatum = buchungsdatum;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    public int getBuchungsnummer() {
        return buchungsnummer;
    }

    public void setBuchungsnummer(int buchungsnummer) {
        this.buchungsnummer = buchungsnummer;
    }
}
