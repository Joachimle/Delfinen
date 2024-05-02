package domain_model;

import java.time.LocalDate;

public class Medlem {
    //////// ATTRIBUTES /////////
    private LocalDate fødselsdato;
    private boolean aktivtMedlem;

    ////////    CONSTRUCTOR     /////////

    public Medlem(int månedsdag, int måned, int år, boolean aktivtMedlem) {
        this.fødselsdato = LocalDate.of(år, måned, månedsdag);
        this.aktivtMedlem = aktivtMedlem;
    }
}
