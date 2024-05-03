package domain_model;

import java.util.ArrayList;

public class Klub {
    private ArrayList<Medlem> medlemmer = new ArrayList<>();


    /////// METHODS ////////
    public void tilføjMedlem(String navn, int månedsdag, int måned, int år, boolean aktivtMedlem) {
        medlemmer.add(new Medlem(navn, månedsdag, måned, år, aktivtMedlem));
    }

    public int sumAfKontingent(){
        int sum = 0;
        for (Medlem medlem : medlemmer) {
            sum += medlem.kontingentPris();
        }
        return sum;
    }

}
