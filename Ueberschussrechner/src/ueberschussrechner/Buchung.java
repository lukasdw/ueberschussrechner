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
}
