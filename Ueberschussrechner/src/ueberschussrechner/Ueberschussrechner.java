package ueberschussrechner;

import javax.swing.JFrame;

public class Ueberschussrechner {

    public static void main(String[] args) {

        Tabelle tabelle = new Tabelle();
        tabelle.csvEinlesen();
        tabelle.ueberschussBerechnen();
        tabelle.ausgabe();
        tabelle.sortieren();
        tabelle.drucken();
        tabelle.csvSpeichern();
    }
}
