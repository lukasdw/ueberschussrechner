package ueberschussrechner;

import javax.swing.JFrame;

public class Ueberschussrechner {

    public static void main(String[] args) {

        Tabelle tabelle = new Tabelle();
        //tabelle.csvEinlesen();
        
        tabelle.drucken();
    }
}
