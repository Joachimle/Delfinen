package domain_model;

import java.time.LocalDate;
import java.time.Period;

public class Medlem {
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
}
