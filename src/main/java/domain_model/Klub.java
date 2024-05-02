package domain_model;

import java.util.ArrayList;

public class Klub {
    private ArrayList<Medlem> medlemmer = new ArrayList<>();



    public void tilføjMedlem(String navn, int månedsdag, int måned, int år, boolean aktivtMedlem) {
        medlemmer.add(new Medlem(navn, månedsdag, måned, år, aktivtMedlem));
    }

}
