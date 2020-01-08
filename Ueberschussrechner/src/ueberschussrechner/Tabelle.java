package ueberschussrechner;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

public class Tabelle {

    private ArrayList<Buchung> buchungListe = new ArrayList<Buchung>();
    private Drucken drucker = new Drucken();
    private String dateipfad;
    private double ueberschuss;
    private int BuchungsnummerCounter = 0;

    public void csvEinlesen() {
        String line = "";

        // JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser();
        // Dialog zum Oeffnen von Dateien anzeigen
        int csvFileInt = chooser.showOpenDialog(null);
        /* Abfrage, ob auf "Öffnen" geklickt wurde */
        if (csvFileInt == JFileChooser.APPROVE_OPTION) {
            // Ausgabe der ausgewaehlten Datei
            this.dateipfad = chooser.getSelectedFile().getAbsolutePath();
        }

        try ( BufferedReader br = new BufferedReader(new FileReader(this.dateipfad))) {
            while ((line = br.readLine()) != null) {
                // use ";" as separator
                String[] buffer = line.split(";");
                this.buchungListe.add(this.BuchungsnummerCounter, new Buchung(this.BuchungsnummerCounter, buffer[1], buffer[2], Double.parseDouble(buffer[3]), Double.parseDouble(buffer[4])));
                this.BuchungsnummerCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(this.dateipfad);
    }

    public void csvSpeichern() {
    }

    public void tabelleSortieren() {
        // Heillige Scheiße ist das kompliziert. Man muss die ArrayListe nach den Objekten sortieren.
        // https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
    }

    public void ueberschussBerechnen() {
        double summe = 0;
        for (int i = 0; i < this.buchungListe.size(); i++) {
            summe = this.buchungListe.get(i).getAusgaben() - summe;
            summe = this.buchungListe.get(i).getEinnahmen() + summe;
        }
        this.ueberschuss = summe;
    }

    public void drucken() {
        drucker.addString("Überschussrechner");
        drucker.addString("Buchungsdatum    Buchungsnummer  Bemerkung   Einnahmen   Ausgaben");
        drucker.druckeSeite("nix", false);

        //this ist ein frame/panel/container, es darf halt nicht ''null'' sein! 
        //false steht für den Rahmen. Dass der Titel ausdruckt wird, habe ich noch nicht geschafft!
        //standardmäßig ist Hochformat
        //printer.druckeSeite(this,"nix",false,true); //würde es im Querformat drucken
    }
}
