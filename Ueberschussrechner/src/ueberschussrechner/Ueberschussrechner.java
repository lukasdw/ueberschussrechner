package ueberschussrechner;

import javax.swing.JFrame;

public class Ueberschussrechner {

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);

        Tabelle tabelle = new Tabelle();
        //tabelle.csvEinlesen();
        tabelle.ueberschussBerechnen();
        tabelle.ausgabe();
        //tabelle.sortieren();
        //tabelle.drucken();
        //tabelle.csvSpeichern();
        tabelle.getBuchungListe();

        gui.setTabelle(tabelle);
        gui.addRowToJTable();
    }
}
