package domain_model;

import java.time.Duration;
import java.time.LocalDate;

public interface Resultat extends Comparable<Resultat> {
    Konkurrencesvømmer svømmer();
    Svømmedisciplin disciplin();
    Duration resultat();
    LocalDate dato();
}