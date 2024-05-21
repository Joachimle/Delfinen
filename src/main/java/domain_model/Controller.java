package domain_model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Controller {

    ///////// ATTRIBUTES ///////////
    private Klub klub;

    ///////// CONSTRUCTOR ///////////
    public Controller() {
        klub = new Klub();
    }

    ///////// METHODS //////////
    public void addMember(String navn, int månedsdag, int måned, int år, boolean iRestance, boolean passivtMedlem) {
        klub.addMember(navn, månedsdag, måned, år, iRestance, passivtMedlem);
    }

    public void addMember(String navn, int månedsdag, int måned, int år, boolean iRestance, Set<Svømmedisciplin> discipliner) {
        klub.addMember(navn, månedsdag, måned, år, iRestance, discipliner);
    }

    public void tilføjTræningsresultat(Konkurrencesvømmer svømmer, Svømmedisciplin disciplin, Duration resultat, LocalDate dato) {
        klub.tilføjTræningsresultat(svømmer, disciplin, resultat, dato);
    }

    public int sumAfKontingent() {
        return klub.sumAfKontingent();
    }

    public List<String> visMedlemmerIRestance() {
        return klub.visMedlemmerIRestance();
    }

    public List<Konkurrencesvømmer> getJuniorsvømmere() {
        return klub.getJuniorsvømmere();
    }

    public List<Konkurrencesvømmer> getSeniorsvømmere() {
        return klub.getSeniorsvømmere();
    }

    public List<String> visTop5Svømmere(Svømmedisciplin disciplin, boolean juniorHold) {
        return klub.visTop5Svømmere(disciplin, juniorHold);
    }

    public void slet(Medlem medlem) {
        klub.slet(medlem);
    }

    public List<Medlem> getMedlemmer() {
        return klub.getMedlemmer();
    }

    public void konverter(Medlem medlem, Set<Svømmedisciplin> aktiveDiscipliner) {
        klub.konverter(medlem, aktiveDiscipliner);
    }

    public void konverter(Konkurrencesvømmer medlem, boolean passivtMedlem) {
        klub.konverter(medlem, passivtMedlem);
    }

    public void slet(Resultat resultat) {
        klub.slet(resultat);
    }

    public List<Træningsresultat> getTræningsresultater(Konkurrencesvømmer ks) {
        return klub.getTræningsresultater(ks);
    }

    public List<Konkurrenceresultat> getKonkurrenceresultater(Konkurrencesvømmer ks) {
        return klub.getKonkurrenceresultater(ks);
    }

    public void tilføjKonkurrenceresultat(Konkurrencesvømmer svømmer, Svømmedisciplin disciplin, Duration resultat, LocalDate dato, String stævne, int placering) {
        klub.tilføjKonkurrenceresultat(svømmer, disciplin, resultat, dato, stævne, placering);
    }
}