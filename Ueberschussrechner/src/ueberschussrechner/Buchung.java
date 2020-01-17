package ueberschussrechner;

import java.util.*;

public class Buchung {

    private int buchungsnummer = 0;
    private String buchungsdatum;
    private String bemerkung;
    private double einnahmen;
    private double ausgaben;
    private double datumZahl;

    public Buchung(int buchungsnummer, String buchungsdatum, String bemerkung, double einnahmen, double ausgaben, int datumZahl) {
        this.buchungsnummer = buchungsnummer;
        this.buchungsdatum = buchungsdatum;
        this.bemerkung = bemerkung;
        this.einnahmen = einnahmen;
        this.ausgaben = ausgaben;
        this.datumZahl = datumZahl;
    }

    public Buchung(int buchungsnummer) {
        this.buchungsnummer = buchungsnummer;
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

    public double getDatumZahl() {
        return datumZahl;
    }

    public void setDatumZahl(double datumZahl) {
        this.datumZahl = datumZahl;
    }
}
