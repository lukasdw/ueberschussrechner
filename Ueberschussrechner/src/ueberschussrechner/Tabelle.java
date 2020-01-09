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

    public void dateiAuswaehlen(String Option) {
        int csvFileInt = 0;
        // JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser();
        if (Option.equals("Öffnen")) {
            // Dialog zum Oeffnen von Dateien anzeigen
            csvFileInt = chooser.showOpenDialog(null);
        }
        if (Option.equals("Speichern")) {
            // Dialog zum Speichern von Dateien anzeigen
            csvFileInt = chooser.showSaveDialog(null);
        }
        /* Abfrage, ob auf "Öffnen" geklickt wurde */
        if (csvFileInt == JFileChooser.APPROVE_OPTION) {
            // Ausgabe der ausgewaehlten Datei
            this.dateipfad = chooser.getSelectedFile().getAbsolutePath();
        }
    }

    public void csvEinlesen() {
        String line = "";
        dateiAuswaehlen("Öffnen");
        try ( BufferedReader br = new BufferedReader(new FileReader(this.dateipfad))) {
            while ((line = br.readLine()) != null) {
                // use ";" as separator
                String[] buffer = line.split(";");
                // String[] datum = buffer[1].split(".");
                // this.buchungListe.add(new Buchung(this.BuchungsnummerCounter, buffer[1], Integer.parseInt(datum[3]), Integer.parseInt(datum[2]), Integer.parseInt(datum[1]), buffer[2], Double.parseDouble(buffer[3]), Double.parseDouble(buffer[4])));
                this.buchungListe.add(new Buchung(this.BuchungsnummerCounter, buffer[1], buffer[2], Double.parseDouble(buffer[3]), Double.parseDouble(buffer[4])));
                this.BuchungsnummerCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ueberschussBerechnen();
    }

    //https://www.tutorials.de/threads/array-in-eine-txt-schreiben.275850/
    public void csvSpeichern(JTable jTableTabelle) {
        /*
        //tabelle.csvSpeichern();
        String dateipfadSave = null;
        // JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser();
        // Dialog zum Oeffnen von Dateien anzeigen
        int csvFileInt = chooser.showSaveDialog(null);
        //Abfrage, ob auf "Öffnen" geklickt wurde
        if (csvFileInt == JFileChooser.APPROVE_OPTION) {
            // Ausgabe der ausgewaehlten Datei
            dateipfadSave = chooser.getSelectedFile().getAbsolutePath();
        }*/

        dateiAuswaehlen("Speichern");
        try {
            File file = new File(this.dateipfad);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            //loop for jtable rows
            for (int i = 0; i < jTableTabelle.getRowCount(); i++) {
                for (int j = 0; j < jTableTabelle.getColumnCount(); j++) {
                    bw.write(jTableTabelle.getModel().getValueAt(i, j) + ";");
                }
                bw.write("\n");
            }
            bw.close();
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //https://www.youtube.com/watch?v=wzWFQTLn8hI
    public void sortieren() {
        /*Collections.sort(buchungListe, new Comparator<Buchung>() {
            public int compare(Buchung b1, Buchung b2) {
                return b1.getCal().getTime().compareTo(b2.getCal().getTime());
            }
        });*/
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
                    + "                              " + this.buchungListe.get(i).getBuchungsdatum()
                    + "         " + this.buchungListe.get(i).getBemerkung()
                    + "          " + this.buchungListe.get(i).getEinnahmen()
                    + "          " + this.buchungListe.get(i).getAusgaben());
        }
        drucker.addLeerzeile();
        drucker.addString("Ueberschuss:     " + this.ueberschuss);
        drucker.druckeSeite("nix", false);
        //standardmäßig ist Hochformat
        //printer.druckeSeite(this,"nix",false,true); //würde es im Querformat drucken
    }

    public void aktualisieren(JTable jTableTabelle) {
        for (int i = 0; i < jTableTabelle.getRowCount(); i++) {
            for (int j = 0; j < jTableTabelle.getColumnCount(); j++) {
                this.buchungListe.get(i).setBuchungsnummer((int) jTableTabelle.getModel().getValueAt(j, j));
                this.buchungListe.get(i).setBuchungsdatum((String) jTableTabelle.getModel().getValueAt(j, j));
                this.buchungListe.get(i).setBemerkung((String) jTableTabelle.getModel().getValueAt(j, j));
                this.buchungListe.get(i).setEinnahmen((double) jTableTabelle.getModel().getValueAt(j, j));
                this.buchungListe.get(i).setAusgaben((double) jTableTabelle.getModel().getValueAt(j, j));
            }
        }
    }

    public ArrayList<Buchung> getBuchungListe() {
        return buchungListe;
    }

    public double getUeberschuss() {
        return ueberschuss;
    }

    public void setUeberschuss(double ueberschuss) {
        this.ueberschuss = ueberschuss;
    }

}
