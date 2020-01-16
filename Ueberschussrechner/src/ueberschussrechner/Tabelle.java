package ueberschussrechner;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Tabelle {

    // eine Liste von Buchungen erstellt (letzendlich die Tabell)
    private ArrayList<Buchung> buchungListe = new ArrayList<Buchung>();

    // Drucker kann drucken
    private Drucken drucker = new Drucken();

    // Dateipfad, der verwendeten Datei
    private String dateipfad;

    // Variable zur Abspeicherung des Überschuss-Betrages
    private double ueberschuss = 0;
    private int buchungsnummerCounter = 0;
    private int buchungsnummerCounterVor = 0;
    private int anzSpalten = 5;
    private boolean tabelleLeer = true;

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

    public void csvEinlesen(JTable jTableTabelle) {
        dateiAuswaehlen("Öffnen");
        String line = "";
        int datumZahl = 0;
        try ( BufferedReader br = new BufferedReader(new FileReader(this.dateipfad))) {
            while ((line = br.readLine()) != null) {
                // use ";" as separator
                String[] buffer = line.split(";");

                if (buffer[1].equals("null")) {
                    buffer[1] = " ";
                } else {
                    String[] datum = buffer[1].split("[.]");
                    String datumText = datum[2] + datum[1] + datum[0];
                    datumZahl = Integer.parseInt(datumText);
                }
                if (buffer[2].equals("null")) {
                    buffer[2] = " ";
                }

                this.buchungListe.add(new Buchung(this.buchungsnummerCounter + 1, buffer[1], buffer[2], Double.parseDouble(buffer[3]), Double.parseDouble(buffer[4]), datumZahl));
                this.buchungsnummerCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ueberschussBerechnen();
    }

    // https://www.tutorials.de/threads/array-in-eine-txt-schreiben.275850/
    // https://www.w3schools.com/java/java_try_catch.asp
    /* Speichert die Daten in eine CSV Datei */
    public void csvSpeichern(JTable jTableTabelle) {
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
        Buchung buchung;
        for (int i = 1; i < buchungListe.size(); i++) {
            for (int j = 0; j < buchungListe.size() - i; j++) {
                if (buchungListe.get(j).getDatumZahl() > buchungListe.get(j + 1).getDatumZahl()) {
                    buchung = buchungListe.get(j);
                    buchungListe.set(j, buchungListe.get(j + 1));
                    buchungListe.set(j + 1, buchung);
                }
            }
        }
    }

    /* Berechnet den Ueberschuss aus. Bei Ausgaben wird der Betrag subtrahiert, bei Einnahmen addiert */
    public void ueberschussBerechnen() {
        double summe = 0;
        for (int i = 0; i < this.buchungListe.size(); i++) {
            summe = this.buchungListe.get(i).getAusgaben() + summe;
            summe = this.buchungListe.get(i).getEinnahmen() + summe;
        }
        this.ueberschuss = summe;
    }

    /* Druckt die buchungsListe mit den dazugehörigen Funktionen der Klasse, Drucken, ohne Formatierung aus. */
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

    // https://www.youtube.com/watch?v=GAl1FSKvoFY
    /* Die Funktion kopiert die Buchung-Arrayliste (buchungsListe) in die Tabelle (JTable jTableTabelle).
       Als Erstes werden die Daten einer Zeile zugewiesen. Danach wird die Zeile in die Tabelle zugetragen. */
    public void addBuchungslisteToJTable(JTable jTableTabelle) {
        DefaultTableModel model = (DefaultTableModel) jTableTabelle.getModel();
        Object zeile[] = new Object[anzSpalten];
        if (tabelleLeer == false) {
            buchungsnummerCounterVor++;
        }
        for (int i = buchungsnummerCounterVor; i < buchungListe.size(); i++) {
            zeile[0] = buchungListe.get(i).getBuchungsnummer();
            zeile[1] = buchungListe.get(i).getBuchungsdatum();
            zeile[2] = buchungListe.get(i).getBemerkung();
            zeile[3] = buchungListe.get(i).getEinnahmen();
            zeile[4] = buchungListe.get(i).getAusgaben();
            model.addRow(zeile);
        }
    }

    public void csvAnlegen(JTable jTableTabelle) {
        buchungsnummerCounterVor = buchungsnummerCounter;
        this.buchungListe.add(new Buchung(this.buchungsnummerCounter + 1));
        DefaultTableModel model = (DefaultTableModel) jTableTabelle.getModel();
        Object zeile[] = new Object[anzSpalten];
        zeile[0] = buchungListe.get(buchungsnummerCounter).getBuchungsnummer();
        zeile[1] = buchungListe.get(buchungsnummerCounter).getBuchungsdatum();
        zeile[2] = buchungListe.get(buchungsnummerCounter).getBemerkung();
        zeile[3] = buchungListe.get(buchungsnummerCounter).getEinnahmen();
        zeile[4] = buchungListe.get(buchungsnummerCounter).getAusgaben();
        model.addRow(zeile);
        buchungsnummerCounter++;
        tabelleLeer = false;
    }

    // https://www.youtube.com/watch?v=GAl1FSKvoFY
    /* Die Funktion kopiert die Tabelle (JTable jTableTabelle) in die Buchung-Arrayliste (buchungsListe).
       Um die verschiedenen Felder anzusprechen, benutzen wir zwei Zählerschleifen. Jede Zeile (Datensatz)
       wird einer Buchung zugewiesen. */
    public void addJTableToBuchungsliste(JTable jTableTabelle) {
        for (int Zeile = 0; Zeile < jTableTabelle.getRowCount(); Zeile++) {
            for (int Spalte = 0; Spalte < jTableTabelle.getColumnCount(); Spalte++) {
                if (Spalte == 0) {
                    this.buchungListe.get(Zeile).setBuchungsnummer((int) jTableTabelle.getModel().getValueAt(Zeile, Spalte));
                }
                if (Spalte == 1) {
                    this.buchungListe.get(Zeile).setBuchungsdatum((String) jTableTabelle.getModel().getValueAt(Zeile, Spalte));
                }
                if (Spalte == 2) {
                    this.buchungListe.get(Zeile).setBemerkung((String) jTableTabelle.getModel().getValueAt(Zeile, Spalte));
                }
                if (Spalte == 3) {
                    this.buchungListe.get(Zeile).setEinnahmen((double) jTableTabelle.getModel().getValueAt(Zeile, Spalte));
                }
                if (Spalte == 4) {
                    this.buchungListe.get(Zeile).setAusgaben((double) jTableTabelle.getModel().getValueAt(Zeile, Spalte));
                }
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
