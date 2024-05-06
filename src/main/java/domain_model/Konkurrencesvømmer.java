package domain_model;

import java.util.Set;

public class Konkurrencesvømmer extends Medlem {

    private Set<Svømmedisciplin> aktiveDiscipliner;

    public Konkurrencesvømmer(String navn, int månedsdag, int måned, int år, Set<Svømmedisciplin> aktiveDiscipliner) {
        super(navn, månedsdag, måned, år, true);
        this.aktiveDiscipliner = aktiveDiscipliner;
    }

    public Set<Svømmedisciplin> getAktiveDiscipliner() {
        return aktiveDiscipliner;
    }
}