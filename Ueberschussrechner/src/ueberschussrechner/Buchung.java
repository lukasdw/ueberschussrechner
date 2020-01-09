package ueberschussrechner;

import java.util.*;

public class Buchung {

    private double ausgaben;
    private double einnahmen;
    private String buchungsdatum;
    private String bemerkung;
    private int buchungsnummer;
    private Calendar cal = Calendar.getInstance();

    //public Buchung(int buchungsnummer, String buchungsdatum, int tag, int Monat, int Jahr, String bemerkung, double einnahmen, double ausgaben) {
    public Buchung(int buchungsnummer, String buchungsdatum, String bemerkung, double einnahmen, double ausgaben) {
        this.buchungsdatum = buchungsdatum;
        this.buchungsnummer = buchungsnummer;
        this.bemerkung = bemerkung;
        this.einnahmen = einnahmen;
        this.ausgaben = ausgaben;
        //cal.set(Jahr, Monat, tag);
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
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
