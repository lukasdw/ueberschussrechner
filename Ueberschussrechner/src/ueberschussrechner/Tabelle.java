package ueberschussrechner;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Tabelle {

    private ArrayList<Buchung> buchungListe = new ArrayList<Buchung>();
    private Drucken drucker = new Drucken();
    private String dateipfad;
    private double ueberschuss;
    private int buchungsnummerCounter = 1;
    private boolean tabelleGefuellt = false;

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
        if (tabelleGefuellt = true) {
            for (int i = 0; i < jTableTabelle.getRowCount(); i++) {
                // Hier müsste eine Funktion hin, die die ganze Tabelle löscht
                //((DefaultTableModel) jTableTabelle.getModel()).removeRow(i);
            }
        }
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(this.dateipfad))) {
            while ((line = br.readLine()) != null) {
                // use ";" as separator
                String[] buffer = line.split(";");
                // String[] datum = buffer[1].split(".");
                // this.buchungListe.add(new Buchung(this.buchungsnummerCounter, buffer[1], Integer.parseInt(datum[3]), Integer.parseInt(datum[2]), Integer.parseInt(datum[1]), buffer[2], Double.parseDouble(buffer[3]), Double.parseDouble(buffer[4])));
                this.buchungListe.add(new Buchung(this.buchungsnummerCounter, buffer[1], buffer[2], Double.parseDouble(buffer[3]), Double.parseDouble(buffer[4])));
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
        /*Collections.sort(buchungListe, new Comparator<Buchung>() {
            public int compare(Buchung b1, Buchung b2) {
                return b1.getCal().getTime().compareTo(b2.getCal().getTime());
            }
        });*/
    }

    /* Berechnet den Ueberschuss aus. Bei Ausgaben wird der Betrag subtrahiert, bei Einnahmen addiert */
    public void ueberschussBerechnen() {
        double summe = 0;
        for (int i = 0; i < this.buchungListe.size(); i++) {
            summe = this.buchungListe.get(i).getAusgaben() - summe;
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
        int anzSpalten = 5;
        DefaultTableModel model = (DefaultTableModel) jTableTabelle.getModel();
        Object zeile[] = new Object[anzSpalten];
        for (int i = 0; i < buchungListe.size(); i++) {
            zeile[0] = buchungListe.get(i).getBuchungsnummer();
            zeile[1] = buchungListe.get(i).getBuchungsdatum();
            zeile[2] = buchungListe.get(i).getBemerkung();
            zeile[3] = buchungListe.get(i).getEinnahmen();
            zeile[4] = buchungListe.get(i).getAusgaben();
            model.addRow(zeile);
        }
    }

    public void csvAnlegen(JTable jTableTabelle) {
        int anzSpalten = 5;
        DefaultTableModel model = (DefaultTableModel) jTableTabelle.getModel();
        Object zeile[] = new Object[anzSpalten];
        zeile[0] = buchungsnummerCounter;
        buchungsnummerCounter++;
        model.addRow(zeile);
        
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

    public boolean isTabelleGefuellt() {
        return tabelleGefuellt;
    }

    public void setTabelleGefuellt(boolean tabelleGefuellt) {
        this.tabelleGefuellt = tabelleGefuellt;
    }
}
