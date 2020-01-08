package ueberschussrechner;

import javax.swing.JFrame;

public class Ueberschussrechner {

    public static void main(String[] args) {

        Tabelle tabelle = new Tabelle();
        tabelle.csvEinlesen();

        Drucken drucker = new Drucken();
        JFrame Jdrucker = new JFrame();
        drucker.addString("Daten");
        drucker.addLeerzeile();
        drucker.addTab();
        drucker.addString("JUHU");
        drucker.druckeSeite(Jdrucker, "nix", false); //this ist ein frame/panel/container, es darf halt nicht ''null'' sein! 

        //false steht für den Rahmen. Dass der Titel ausdruckt wird, habe ich noch nicht geschafft!
        //standardmäßig ist Hochformat
        //printer.druckeSeite(this,"nix",false,true); //würde es im Querformat drucken
    }
}
