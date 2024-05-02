package domain_model;

public class Controller {
    Klub klub = new Klub();

    public void tilføjMedlem(String navn, int månedsdag, int måned, int år, boolean aktivtMedlem) {
        klub.tilføjMedlem(navn, månedsdag, måned, år, aktivtMedlem);
    }
}
