package ueberschussrechner;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class Tabelle {

    private ArrayList<Buchung> buchungListe = new ArrayList<Buchung>();
    private Drucken drucker = new Drucken();
    private String dateipfad;
    private double ueberschuss;
    private int BuchungsnummerCounter = 1;

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
                this.buchungListe.add(new Buchung(this.BuchungsnummerCounter, buffer[1], buffer[2], Double.parseDouble(buffer[3]), Double.parseDouble(buffer[4])));
                this.BuchungsnummerCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(this.dateipfad);
    }
    
    //https://www.gutefrage.net/frage/kann-man-eine-array--list-in-einer-csv--datei-txt-speichern
    public void csvSpeichern() {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter(dateipfad));
            Iterator iter = buchungListe.iterator();
            while (iter.hasNext()) {
                Object o = iter.next();
                printWriter.println(o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    public void sortieren() {
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

    public void ausgabe() {
        System.out.println("Buchungsnummer  Buchungsdatum   Bemerkung       Einnahmen   Ausgaben");
        for (int i = 0; i < this.buchungListe.size(); i++) {
            System.out.println(this.buchungListe.get(i).getBuchungsnummer()
                    + "      " + this.buchungListe.get(i).getBuchungsdatum()
                    + "      " + this.buchungListe.get(i).getBemerkung()
                    + "      " + this.buchungListe.get(i).getEinnahmen()
                    + "   " + this.buchungListe.get(i).getAusgaben());
        }
        System.out.println("");
        System.out.println("Ueberschuss:     " + this.ueberschuss);
    }

    public void drucken() {
        drucker.addString("Überschussrechner");
        drucker.addLeerzeile();
        drucker.addString("Buchungsdatum    Buchungsnummer  Bemerkung   Einnahmen   Ausgaben");
        for (int i = 0; i < this.buchungListe.size(); i++) {
            drucker.addString(this.buchungListe.get(i).getBuchungsnummer()
                    + "            " + this.buchungListe.get(i).getBuchungsdatum()
                    + "      " + this.buchungListe.get(i).getBemerkung()
                    + "      " + this.buchungListe.get(i).getEinnahmen()
                    + "      " + this.buchungListe.get(i).getAusgaben());
        }
        drucker.addLeerzeile();
        drucker.addString("Ueberschuss:     " + this.ueberschuss);
        drucker.druckeSeite("nix", false);
        //standardmäßig ist Hochformat
        //printer.druckeSeite(this,"nix",false,true); //würde es im Querformat drucken
    }
}