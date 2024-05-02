package domain_model;

import java.time.LocalDate;

public class Medlem {
    //////// ATTRIBUTES /////////
    private String navn;
    private LocalDate fødselsdato;
    private boolean aktivtMedlem;

    ////////    CONSTRUCTOR     /////////

    public Medlem(String navn, int månedsdag, int måned, int år, boolean aktivtMedlem) {
        this.navn = navn;
        this.fødselsdato = LocalDate.of(år, måned, månedsdag);
        this.aktivtMedlem = aktivtMedlem;
    }
}
