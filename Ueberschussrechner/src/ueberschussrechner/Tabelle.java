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
        JFileChooser chooser = new JFileChooser();
        if (Option.equals("Öffnen")) {
            // Dialog zum Öffnen von Dateien anzeigen
            csvFileInt = chooser.showOpenDialog(null);
        }
        if (Option.equals("Speichern")) {
            // Dialog zum Speichern von Dateien anzeigen
            csvFileInt = chooser.showSaveDialog(null);
        }
        // Abfrage, ob auf "Öffnen" bzw. "Speichern" geklickt wurde
        if (csvFileInt == JFileChooser.APPROVE_OPTION) {
            // Ausgabe der ausgewaehlten Datei
            this.dateipfad = chooser.getSelectedFile().getAbsolutePath();
        }
    }

    // Liest die CSV-Datei ein und speichert die Daten in die ArrayListe 
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

    // Speichert die Daten in eine CSV Datei
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

    // Legt eine neue Zeile an
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

    // Sortiert die Buchungen anhand des Datums, mithilfe der Variable "datumZahl"
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

    // Berechnet den Ueberschuss aus. Bei Ausgaben wird der Betrag subtrahiert, bei Einnahmen addiert */
    public void ueberschussBerechnen() {
        double summe = 0;
        for (int i = 0; i < this.buchungListe.size(); i++) {
            summe = this.buchungListe.get(i).getAusgaben() + this.buchungListe.get(i).getEinnahmen() + summe;
        }
        this.ueberschuss = summe;
    }

    // Druckt die "buchungsListe" mithilfe den dazugehörigen Funktionen der Klasse, Drucken, ohne Formatierung aus.
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
    }

    // Die Funktion kopiert die Buchung-Arrayliste (buchungsListe) in die Tabelle (JTable jTableTabelle).
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

    // Die Funktion kopiert die Tabelle (JTable jTableTabelle) in die Buchung-Arrayliste (buchungsListe).
    public void addJTableToBuchungsliste(JTable jTableTabelle) {
        for (int Zeile = 0; Zeile < jTableTabelle.getRowCount(); Zeile++) {
            this.buchungListe.get(Zeile).setBuchungsnummer((int) jTableTabelle.getModel().getValueAt(Zeile, 0));
            this.buchungListe.get(Zeile).setBuchungsdatum((String) jTableTabelle.getModel().getValueAt(Zeile, 1));
            this.buchungListe.get(Zeile).setBemerkung((String) jTableTabelle.getModel().getValueAt(Zeile, 2));
            this.buchungListe.get(Zeile).setEinnahmen((double) jTableTabelle.getModel().getValueAt(Zeile, 3));
            this.buchungListe.get(Zeile).setAusgaben((double) jTableTabelle.getModel().getValueAt(Zeile, 4));
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
