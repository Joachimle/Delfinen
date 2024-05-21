package domain_model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

public class Medlem implements Comparable<Medlem> {
    //////// ATTRIBUTES /////////
    private String navn;
    private LocalDate fødselsdato;
    private boolean iRestance;
    private boolean passivtMedlem;

    ////////    CONSTRUCTOR     /////////

    public Medlem(String navn, int månedsdag, int måned, int år, boolean iRestance, boolean passivtMedlem) {
        this.navn = navn;
        this.fødselsdato = LocalDate.of(år, måned, månedsdag);
        this.iRestance = iRestance;
        this.passivtMedlem = passivtMedlem;
    }

    ///////// METHODS ////////

    public int kontingentPris(){
        int antalÅrGammel = Period.between(fødselsdato, LocalDate.now()).getYears();

        if(passivtMedlem){
            return 500;
        } else if (antalÅrGammel < 18) {
            return 1000;
        } else if (antalÅrGammel > 60) {
            return 1200;
        } else {
            return 1600;
        }
    }

    public boolean erMindreårig() {
        int antalÅrGammel = Period.between(fødselsdato, LocalDate.now()).getYears();
        return antalÅrGammel < 18;
    }

    public boolean erIRestance(){
        return iRestance;
    }

    public String getNavn(){
        return navn;
    }

    @Override
    public String toString(){
        return
                navn + ";" +
                fødselsdato.getDayOfMonth() + ";" +
                fødselsdato.getMonthValue() + ";" +
                fødselsdato.getYear() + ";" +
                iRestance + ";" +
                passivtMedlem;
    }

    @Override
    public int compareTo(Medlem that) {
        return this.navn.compareTo(that.navn);
    }

    public String getFødselsdato() {
        return fødselsdato.getDayOfMonth() + "/" + fødselsdato.getMonthValue() + "/" + fødselsdato.getYear();
    }

    public String getPassivtMedlem() {
        return passivtMedlem ? "Passivt medlem" : "Aktivt medlem";
    }

    public String getIRestance() {
        return iRestance ? "Er i restance" : "Har betalt kontingent";
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setFødselsdato(int månedsdag, int måned, int år) {
        fødselsdato = LocalDate.of(månedsdag, måned, år);
    }

    public void setiRestance(boolean iRestance) {
        this.iRestance = iRestance;
    }

    public void setPassivtMedlem(boolean passivtMedlem) {
        this.passivtMedlem = passivtMedlem;
    }

    public Konkurrencesvømmer konverter(Set<Svømmedisciplin> aktiveDiscipliner) {
        return new Konkurrencesvømmer(navn, fødselsdato.getDayOfMonth(), fødselsdato.getMonthValue(), fødselsdato.getYear(), iRestance, aktiveDiscipliner);
    }
}