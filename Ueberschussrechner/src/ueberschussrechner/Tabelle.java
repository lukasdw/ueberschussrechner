package ueberschussrechner;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class Tabelle {

    // Attribute
    private ArrayList<Buchung> buchungListe = new ArrayList<Buchung>();
    private ArrayList<Integer> bereitsvorhandeneBuchungsnummern = new ArrayList<Integer>();
    private String dateipfad = "null";
    private double ueberschuss = 0;
    private int anzahlBuchungen = 0;
    private int BuchungsnummerGenerator = 0;
    private int anzSpalten = 5;
    private boolean tabelleLeer = true;
    private boolean druckbar = false;

    /* Funktion, um eine Datei einzulesen oder abzuspeichern und den Pfad
    in "dateipfad" abzuspeichern.*/
    public void dateiAuswaehlen(String Option) {
        int csvFileInt = 0;
        // Instanz der Klasse, "JFileChooser" für die Funktion Dateien zu öffnen 
        JFileChooser chooser = new JFileChooser();
        if (Option.equals("Öffnen")) {
            // Dialogfenster zum Öffnen von Dateien anzeigen
            csvFileInt = chooser.showOpenDialog(null);
        }
        if (Option.equals("Speichern")) {
            // Dialogfenster zum Speichern von Dateien anzeigen
            csvFileInt = chooser.showSaveDialog(null);
        }
        // Wenn die Dateiabfrage sauber geklappt hat, mache ...
        if (csvFileInt == JFileChooser.APPROVE_OPTION) {
            // Abspeichern des "absoluten" Pfades der Datei. Also mit "C:\.." usw.
            this.dateipfad = chooser.getSelectedFile().getAbsolutePath();
        }
    }

    // Liest die CSV-Datei ein und speichert die Daten in die ArrayListe 
    public void csvEinlesen(JTable jTableTabelle) {
        dateiAuswaehlen("Öffnen");
        int datumZahl = 0;
        String lineTemp = "";
        try ( BufferedReader br = new BufferedReader(new FileReader(this.dateipfad))) {
            // Der BufferedReader soll bis zum Ende (!= null) Zeilen einlesen
            while ((lineTemp = br.readLine()) != null) {
                // Die Daten in der CSV-Datei sind durch ein Semikolon(;) voneinander getrennt.
                String[] zeile = lineTemp.split(";");

                /* Wenn die Datei eingelesen wird, kann es sein, dass das Feld
                mit "null" gefüllt ist, da beim Abspeichern der CSV-Datei kein
                Wert eingetragen war. Diese "Falschaussage" wollen wir
                überschreiben, indem wir nichts eintragen. Dies machen wir bei
                dem Buchungsdatum (zeile[1]) und bei der Bemerkung (zeile[2]) */
                if (zeile[1].equals("null")) {
                    zeile[1] = " ";
                } else {
                    /* Das Datum wird mit dem gleichen Prinzip wie die gesammte
                    Zeile aufgesplittetin Tag, Monat und Jahr. Am Ende wird es
                    in datumZahl zusammengesetzt, sodass der BubbleSort damit arbeiten kann */
                    String[] datum = zeile[1].split("[.]");
                    String datumText = datum[2] + datum[1] + datum[0];
                    datumZahl = Integer.parseInt(datumText);
                }
                if (zeile[2].equals("null")) {
                    zeile[2] = " ";
                }

                /* Die Buchungsliste (Array-Liste) ist sozusagen die Tabelle. Durch
                die "add"-Funktion erschaffen wir eine neue Buchung in der Array Liste */
                // Konstroktur:::: public Buchung(int buchungsnummer, String buchungsdatum, String bemerkung, double einnahmen, double ausgaben, int datumZahl)
                this.buchungListe.add(new Buchung(Integer.parseInt(zeile[0]), zeile[1], zeile[2], Double.parseDouble(zeile[3]), Double.parseDouble(zeile[4]), datumZahl));

                // Damit die Buchungsnummern nicht doppelt vorhanden sind packen wir alle eingelesenen Nummern aus der CSV in ein Ganzzahl Arrayliste.
                bereitsvorhandeneBuchungsnummern.add(Integer.parseInt(zeile[0]));

                // Wenn eine Buchung eingespeichert wird, erhöht sich die Anzahl an Buchungen um 1.
                anzahlBuchungen++;
            }
        } /* Mit "try and catch" versuchen wir Fehler abzufangen und ein
        Absturz zu verhindern. */ catch (IOException e) {
            e.printStackTrace();
        }

        druckbar = true;
        // Nach dem Einlesen der Tabelle wird der Wert in "ueberschuss" des Überschusses berechnet.
        ueberschussBerechnen();
    }

    // Speichert die Werte aus dem jTable in eine Datei
    public void csvSpeichern(JTable jTableTabelle) {
        dateiAuswaehlen("Speichern");
        try {
            /* Es wird eine neue Datei an dem Speicherort (this.dateipfad), 
            der in "dateiAuswaehlen" ausgewählt wurde */
            File file = new File(this.dateipfad);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            /* Instanz der Klasse, "BufferedWriter", der in einer Datei schreiben kann. */
            BufferedWriter bw = new BufferedWriter(fw);

            /* Zwei Zählerschleifen mit denen man jedes Feld aus der Tabelle
            (jTableTabelle) einzelt ansprechen und somit mit den Werten füllen kann */
            for (int zeile = 0; zeile < jTableTabelle.getRowCount(); zeile++) {
                for (int spalte = 0; spalte < jTableTabelle.getColumnCount(); spalte++) {
                    bw.write(jTableTabelle.getModel().getValueAt(zeile, spalte) + ";");
                    // Ist das schreiben der Zeile abgeschlossen, machen wir einen Zeilenumbruch.
                    bw.write("\n");
                }
            }
            // Wir schließen das Schreiben in der Datei.
            bw.close();
            fw.close();
        } /* Mit "try and catch" versuchen wir Fehler abzufangen und ein
        Absturz zu verhindern. */ catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Legt eine neue Zeile an
    public void csvAnlegen(JTable jTableTabelle) {
        /* Bei dem Anlegen einer neuen Zeile erschaffen wir selbstständig eine
        neue Buchungsnummer. Diese ergibt sich aus dem BuchungsnummerGenerator.
        Hier setze ich den Buchungsgenerator auf die höchste Buchungsnummer,
        die wir eingelesen haben, damit keine doppelten Buchungsnummern vorkommen.*/

        if (dateipfad != "null" && tabelleLeer == true) {
            BuchungsnummerGenerator = Collections.max(bereitsvorhandeneBuchungsnummern);
        }

        /* Die Buchungsliste (Array-Liste) ist sozusagen die Tabelle. Durch
        die "add"-Funktion erschaffen wir eine neue Buchung in der Array Liste 
           Konstroktur:::: public Buchung(int buchungsnummer) */
        this.buchungListe.add(new Buchung(this.BuchungsnummerGenerator + 1));

        /* Die Zeile wird nun in die Tabelle (jTableTabelle) hinzugefügt.
        Dazu müssen wir unser Model in ein "DefaultTableModel" umwandeln, um
        die nötigen Funktionen benutzen zu können */
        DefaultTableModel model = (DefaultTableModel) jTableTabelle.getModel();

        /* Nun werden die Werte aus der Buchung in ein Array, was als Zeile
        fungiert, gespeichert. Diese Zeile wird dann als Zeile in der Tabelle
        hinzugefügt. */
        Object zeile[] = new Object[anzSpalten];
        zeile[0] = buchungListe.get(this.BuchungsnummerGenerator).getBuchungsnummer();
        zeile[1] = buchungListe.get(this.BuchungsnummerGenerator).getBuchungsdatum();
        zeile[2] = buchungListe.get(this.BuchungsnummerGenerator).getBemerkung();
        zeile[3] = buchungListe.get(this.BuchungsnummerGenerator).getEinnahmen();
        zeile[4] = buchungListe.get(this.BuchungsnummerGenerator).getAusgaben();
        model.addRow(zeile);

        /* Wurde die Buchung mit der Nummer aus dem "BuchungsnummerGenerator"
        erschaffen. Erhöht sich die Anzahl der Buchungen, aber auch der
        "BuchungsGenerator", da die nächste Buchungsnummer ansteht */
        anzahlBuchungen++;
        BuchungsnummerGenerator++;

        // Wird eine neue Buchung hinzugefügt, ist die Tabelle nicht mehr leer
        tabelleLeer = false;
        druckbar = true;
    }

    // Sortiert die Buchungen anhand des Datums, mithilfe der Variable "datumZahl"
    public void sortieren() {
        /* Das Sortieren funktioniert wie der BubbleSort (http://www.java-programmieren.com/bubblesort-java.php).
        Dabei ist die Buchung, "buchung" unser temporärer Speicher beim Verschieben der Werte durch die Buchungsliste
        Erklärung: "https://www.youtube.com/watch?v=xli_FI7CuzA" */
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

    // Diese Funktion leert die gesammte Tabelle (jTableTabelle)
    public void tabelleLeeren(JTable jTableTabelle) {
        DefaultTableModel model = (DefaultTableModel) jTableTabelle.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }

    // Berechnet den Ueberschuss aus. Bei Ausgaben wird der Betrag subtrahiert, bei Einnahmen addiert */
    public void ueberschussBerechnen() {
        /* Der Überschuss wird einfach pro Zeile zusammen gerechnet zusammen
        gerechnet und in "summe" zwischengespeichert. Mit der Zählerschleife
        gehen wir alle Zeilen in der Tabelle durch. Am Ende speichern wir
        unsere Summe in "ueberschuss" ab. */
        double summe = 0;
        for (int i = 0; i < this.buchungListe.size(); i++) {
            summe = this.buchungListe.get(i).getAusgaben() + this.buchungListe.get(i).getEinnahmen() + summe;
        }
        this.ueberschuss = summe;
    }

    // Der Drucker entnimmt sich exakt die Darstellung der Tabelle (jTableTabelle) und druckt sie.
    // Quelle: "https://www.youtube.com/watch?v=0Z9IfjIAMo8"
    public void drucken(JTable jTableTabelle) {
        if (druckbar) {
            MessageFormat kopfzeile = new MessageFormat("Buchungsliste");
            // Zeigt die Anzahl der Seite an (Beispieloutput: "Seite 1")
            MessageFormat fusszeile = new MessageFormat("Seite {0,number,integer}");
            try {
                jTableTabelle.print(JTable.PrintMode.FIT_WIDTH, kopfzeile, fusszeile);

            } catch (java.awt.print.PrinterException e) {
                System.out.println("Fehler");
            }
        }
    }

    // Die Funktion kopiert die Buchung-Arrayliste (buchungsListe) in die Tabelle (JTable jTableTabelle).
    public void addBuchungslisteToJTable(JTable jTableTabelle) {
        /* Die Zeile wird nun in die Tabelle (jTableTabelle) hinzugefügt.
        Dazu müssen wir unser Model in ein "DefaultTableModel" umwandeln, um
        die nötigen Funktionen benutzen zu können */
        DefaultTableModel model = (DefaultTableModel) jTableTabelle.getModel();
        Object zeile[] = new Object[anzSpalten];

        // Damit die Buchungsnummer bei der ersten Buchung nicht bei 0 anfängt
        if (tabelleLeer == false) {
            BuchungsnummerGenerator++;
        }

        /* Nun werden die Werte aus der Buchung in ein Array, was als Zeile
        fungiert, gespeichert. Diese Zeile wird dann als Zeile in der Tabelle
        hinzugefügt. */
        for (int i = BuchungsnummerGenerator; i < buchungListe.size(); i++) {
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
