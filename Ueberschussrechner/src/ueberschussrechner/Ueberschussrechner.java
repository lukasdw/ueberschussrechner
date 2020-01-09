package ueberschussrechner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.JFrame;

public class Ueberschussrechner {

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);
        Tabelle tabelle = new Tabelle();
        gui.setTabelle(tabelle);
    }
}
