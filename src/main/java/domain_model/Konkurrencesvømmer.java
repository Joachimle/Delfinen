package domain_model;

import java.util.Set;

public class Konkurrencesvømmer extends Medlem {

    private Set<Svømmedisciplin> aktiveDiscipliner;

    public Konkurrencesvømmer(String navn, int månedsdag, int måned, int år, boolean iRestance, Set<Svømmedisciplin> aktiveDiscipliner) {
        super(navn, månedsdag, måned, år, iRestance,false);
        this.aktiveDiscipliner = aktiveDiscipliner;
    }

    public Set<Svømmedisciplin> getAktiveDiscipliner() {
        return aktiveDiscipliner;
    }
}