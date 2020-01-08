package ueberschussrechner;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

public class Tabelle {

    private ArrayList<Buchung> buchungListe = new ArrayList<Buchung>();
    private String dateipfad;
    private double ueberschuss;
    private int BuchungsnummerCounter;

    public void csvEinlesen() {
        int counter = 0;
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
                this.buchungListe.add(counter, new Buchung());
                counter++;

                // Abfrage zur Überprüfung, ob die Buchung eine Ausgabe oder Einnahme ist.
                if (Double.parseDouble(buffer[3]) > 0) {
                    this.buchungListe.get(counter).buchungAusgaben(this.BuchungsnummerCounter, buffer[1], buffer[2], Double.parseDouble(buffer[3]));
                } else {
                    this.buchungListe.get(counter).buchungEinnahmen(this.BuchungsnummerCounter, buffer[1], buffer[2], Double.parseDouble(buffer[4]));
                }
                this.BuchungsnummerCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(this.dateipfad);
        ueberschussBerechnen();
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
}
