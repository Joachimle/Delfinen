package domain_model;

import java.time.Duration;

public interface Resultat extends Comparable<Resultat> {
    public Konkurrencesvømmer svømmer();
    public Svømmedisciplin disciplin();
    public Duration resultat();
}