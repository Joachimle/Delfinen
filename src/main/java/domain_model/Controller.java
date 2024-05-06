package domain_model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Controller {

    private Klub klub;

    public Controller() {
        klub = new Klub();
    }

    public void tilføjMedlem(String navn, int månedsdag, int måned, int år, boolean aktivtMedlem) {
        klub.tilføjMedlem(navn, månedsdag, måned, år, aktivtMedlem);
    }

    public void tilføjMedlem(String navn, int månedsdag, int måned, int år, Set<Svømmedisciplin> discipliner) {
        klub.tilføjMedlem(navn, månedsdag, måned, år, discipliner);
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
}