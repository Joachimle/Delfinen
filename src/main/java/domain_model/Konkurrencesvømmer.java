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

    public void setAktiveDiscipliner(Set<Svømmedisciplin> aktiveDiscipliner) {
        this.aktiveDiscipliner = aktiveDiscipliner;
    }

    public Medlem konverter(boolean passivtMedlem) {
        String[] dato = getFødselsdato().split("/");
        return new Medlem(getNavn(), Integer.parseInt(dato[0]), Integer.parseInt(dato[1]), Integer.parseInt(dato[2]), erIRestance(), passivtMedlem);
    }
}