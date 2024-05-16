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

    public void tilføjTræningsresultat(Konkurrencesvømmer svømmer, Svømmedisciplin disciplin, LocalDate dato, Duration resultat) {
        klub.tilføjTræningsresultat(svømmer, disciplin, dato, resultat);
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

    public List<String> visTop5Svømmere() {
        return klub.visTop5Svømmere();
    }
}