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
    private int BuchungsnummerCounter = 1;
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
            summe = this.buchungListe.get(i).getAusgaben() + summe;
            summe = this.buchungListe.get(i).getEinnahmen() - summe;
        }
        this.ueberschuss = summe;
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

    // https://www.youtube.com/watch?v=GAl1FSKvoFY
    // Kopiert die Buchungsliste in die Tabelle
    public void addBuchungslisteToJTable(JTable jTableTabelle) {
        DefaultTableModel model = (DefaultTableModel) jTableTabelle.getModel();
        Object rowData[] = new Object[5];
        for (int i = 0; i < buchungListe.size(); i++) {
            rowData[0] = buchungListe.get(i).getBuchungsnummer();
            rowData[1] = buchungListe.get(i).getBuchungsdatum();
            rowData[2] = buchungListe.get(i).getBemerkung();
            rowData[3] = buchungListe.get(i).getEinnahmen();
            rowData[4] = buchungListe.get(i).getAusgaben();
            model.addRow(rowData);
        }
    }

    // https://www.youtube.com/watch?v=GAl1FSKvoFY
    // Kopiert die Tabelle in die Buchungsliste
    public void addJTableToBuchungsliste(JTable jTableTabelle) {
        for (int row = 0; row < jTableTabelle.getRowCount(); row++) {
            for (int col = 0; col < jTableTabelle.getColumnCount(); col++) {
                if (col == 0) this.buchungListe.get(row).setBuchungsnummer((int) jTableTabelle.getModel().getValueAt(row, col));
                if (col == 1) this.buchungListe.get(row).setBuchungsdatum((String) jTableTabelle.getModel().getValueAt(row, col));
                if (col == 2) this.buchungListe.get(row).setBemerkung((String) jTableTabelle.getModel().getValueAt(row, col));
                if (col == 3) this.buchungListe.get(row).setEinnahmen((double) jTableTabelle.getModel().getValueAt(row, col));
                if (col == 4) this.buchungListe.get(row).setAusgaben((double) jTableTabelle.getModel().getValueAt(row, col));
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
