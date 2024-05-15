package domain_model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Klub {
    private List<Medlem> medlemmer;
    private List<Træningsresultat> træningsresultater;

    public Klub() {
        medlemmer = new ArrayList<>();
        træningsresultater = new ArrayList<>();
    }


    /////// METHODS ////////
    public void addMember(String navn, int månedsdag, int måned, int år, boolean iRestance, boolean passivtMedlem) {
        medlemmer.add(new Medlem(navn, månedsdag, måned, år, iRestance, passivtMedlem));
    }

    public void addMember(String navn, int månedsdag, int måned, int år, boolean iRestance, Set<Svømmedisciplin> discipliner) {
        medlemmer.add(new Konkurrencesvømmer(navn, månedsdag, måned, år, iRestance, discipliner));
    }

    public void tilføjTræningsresultat(Konkurrencesvømmer svømmer, Svømmedisciplin disciplin, LocalDate dato, Duration resultat) {
        træningsresultater.add(new Træningsresultat(svømmer, disciplin, dato, resultat));
    }

    public List<Træningsresultat> visTop5Svømmere() {
        List<Træningsresultat> top5Svømmere = new ArrayList<>();
        Collections.sort(træningsresultater); // Naturlig sortering (Duration resultater)
        for (Træningsresultat træningsresultat : træningsresultater) {
            if (træningsresultat.disciplin() == Svømmedisciplin.CRAWL) {
                if (getJuniorsvømmere().contains(træningsresultat.svømmer())) {
                    if (top5Svømmere.size() < 5) {
                        if (!top5Svømmere.contains(træningsresultat.svømmer())) {
                            top5Svømmere.add(træningsresultat);
                        }
                    }
                }
            }
        }
        return top5Svømmere;
    }

    public int sumAfKontingent(){
        int sum = 0;
        for (Medlem medlem : medlemmer) {
            sum += medlem.kontingentPris();
        }
        return sum;
    }

    public List<String> visMedlemmerIRestance(){
        ArrayList<String> medlemmerIRestance = new ArrayList<>();
        for (Medlem medlem : medlemmer) {
            if (medlem.erIRestance()) {
                medlemmerIRestance.add(medlem.getNavn());
            }
        }
        return medlemmerIRestance;
    }

    public List<Konkurrencesvømmer> getJuniorsvømmere() {
        ArrayList<Konkurrencesvømmer> hold = new ArrayList<>();
        for (Medlem medlem : medlemmer) {
            if (medlem instanceof Konkurrencesvømmer juniorsvømmer && medlem.erMindreårig()) {
                hold.add(juniorsvømmer);
            }
        }
        return hold;
    }

    public List<Konkurrencesvømmer> getSeniorsvømmere() {
        ArrayList<Konkurrencesvømmer> hold = new ArrayList<>();
        for (Medlem medlem : medlemmer) {
            if (medlem instanceof Konkurrencesvømmer seniorsvømmer && !medlem.erMindreårig()) {
                hold.add(seniorsvømmer);
            }
        }
        return hold;
    }
}