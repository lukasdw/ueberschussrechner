package ueberschussrechner;

public class Tabelle {

    private Buchung[] buchungListe;
    private String dateipfad;
    private double ueberschuss;

    public void csvEinlesen(String dateipfad) {
        this.dateipfad = dateipfad;
    }

    public void csvSpeichern() {
    }
    
    public void tabelleSortieren(){
         
    }
    
    public void ueberschussBerechnen(){
        double summe = 0;        
        summe = this.ueberschuss;
    }
}
