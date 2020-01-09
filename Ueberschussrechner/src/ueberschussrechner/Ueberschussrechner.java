package ueberschussrechner;

import java.io.*;
import java.util.*;
import javax.swing.JFrame;

public class Ueberschussrechner {

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);
        Tabelle tabelle = new Tabelle();
        gui.setTabelle(tabelle);
    }
}