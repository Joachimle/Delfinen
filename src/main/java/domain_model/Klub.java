package domain_model;

import data_source.Filehandler;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Klub {
    private List<Medlem> medlemmer;
    private List<Resultat> resultater;

    public Klub() {
        medlemmer = new ArrayList<>();
        resultater = new ArrayList<>();
    }


    /////// METHODS ////////
    public void addMember(String navn, int månedsdag, int måned, int år, boolean iRestance, boolean passivtMedlem) {
        medlemmer.add(new Medlem(navn, månedsdag, måned, år, iRestance, passivtMedlem));
        Collections.sort(medlemmer);
    }

    public void addMember(String navn, int månedsdag, int måned, int år, boolean iRestance, Set<Svømmedisciplin> discipliner) {
        medlemmer.add(new Konkurrencesvømmer(navn, månedsdag, måned, år, iRestance, discipliner));
        Collections.sort(medlemmer);
    }

    public void tilføjTræningsresultat(Konkurrencesvømmer svømmer, Svømmedisciplin disciplin, Duration resultat, LocalDate dato) {
        resultater.add(new Træningsresultat(svømmer, disciplin, resultat, dato));
        Collections.sort(resultater);
    }

    public List<String> visTop5Svømmere(Svømmedisciplin disciplin, boolean juniorHold) {
        List<String> top5Svømmere = new ArrayList<>();
        List<Konkurrencesvømmer> hold = juniorHold ? getJuniorsvømmere() : getSeniorsvømmere();
        Collections.sort(resultater); // Naturlig sortering (Duration resultater)
        for (Resultat resultat : resultater) {
            if (resultat.disciplin() == disciplin) {
                if (hold.contains(resultat.svømmer())) {
                    if (top5Svømmere.size() < 5) {
                        if (!top5Svømmere.contains(resultat.svømmer().getNavn())) {
                            top5Svømmere.add(resultat.svømmer().getNavn());
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

    public List<Medlem> getMedlemmer() {
        return List.copyOf(medlemmer);
    }

    public void slet(Medlem medlem) {
        medlemmer.remove(medlem);
        if (medlem instanceof Konkurrencesvømmer ks) {
            resultater.removeIf(resultat -> resultat.svømmer() == ks);
        }
    }

    public void slet(Resultat resultat) {
        resultater.remove(resultat);
    }

    public void konverter(Medlem medlem, Set<Svømmedisciplin> aktiveDiscipliner) {
        medlemmer.remove(medlem);
        medlemmer.add(medlem.konverter(aktiveDiscipliner));
        Collections.sort(medlemmer);
    }

    public void konverter(Konkurrencesvømmer medlem, boolean passivtMedlem) {
        medlemmer.remove(medlem);
        medlemmer.add(medlem.konverter(passivtMedlem));
        Collections.sort(medlemmer);
    }

    public List<Træningsresultat> getTræningsresultater(Konkurrencesvømmer ks) {
        List<Træningsresultat> trr = new ArrayList<>();
        resultater.forEach(resultat -> {
            if (resultat.svømmer() == ks && resultat instanceof Træningsresultat tr) {
                trr.add(tr);
            }
        });
        return trr;
    }

    public List<Konkurrenceresultat> getKonkurrenceresultater(Konkurrencesvømmer ks) {
        List<Konkurrenceresultat> krr = new ArrayList<>();
        resultater.forEach(resultat -> {
            if (resultat.svømmer() == ks && resultat instanceof Konkurrenceresultat kr) {
                krr.add(kr);
            }
        });
        return krr;
    }

    public void tilføjKonkurrenceresultat(Konkurrencesvømmer svømmer, Svømmedisciplin disciplin, Duration resultat, LocalDate dato, String stævne, int placering) {
        resultater.add(new Konkurrenceresultat(svømmer, disciplin, resultat, dato, stævne, placering));
        Collections.sort(resultater);
    }

    public void load() {
        List[] lister = Filehandler.load();
        medlemmer.addAll(lister[0]);
        resultater.addAll(lister[1]);
    }
    public void save() {
        Filehandler.save(medlemmer, resultater);
    }
}