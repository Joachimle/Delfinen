package user_interface;

import domain_model.Controller;
import domain_model.Konkurrencesvømmer;
import domain_model.Svømmedisciplin;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class UserInterface {
    private Scan scan;
    private Controller controller;

    ///////// CONSTRUCTOR ////////////
    public UserInterface() {

        controller = new Controller();

        // Hardcodede medlemmer til test af UI
        controller.addMember("Joachim Leth Elgaard", 21, 02, 2001, false, Set.of(Svømmedisciplin.RYGCRAWL));
        controller.addMember("Jens Ellegaard", 22, 9, 1337, true, Set.of(Svømmedisciplin.CRAWL, Svømmedisciplin.BUTTERFLY));
    }

    ////////// METHODS ///////////
    public void start() {
        scan = new Scan();
        // Programmet åbnes (her skal loades)
        System.out.println("\nVelkommen til Svømmeklubben Delfinens administrative system.");
        hovedmenu();

        // Programmet lukkes (her skal gemmes)
        System.out.println("Programmet er lukket.");
    }

    ////////// HJÆLPEMETODER/AUXILIARY METHODS ///////////
    private void hovedmenu() {

        System.out.println("""
            
            Hovedmenu
            1) Tilføj nyt medlem
            2) Se samlede forventede kontingent
            3) Se medlemmer i restance
            4) Tilføj træningsresultater
            5) Top 5 svømmere
            0) Gem og luk
            """
        );

        int userInput = scan.number("Indtast menuvalg: ", 0, 4);

        System.out.println();

        switch (userInput) {
            case 1 -> menuPunktTilføjMedlem();
            case 2 -> menuPunktKontingentOversigt();
            case 3 -> menuPunktRestanceOversigt();
            case 4 -> menuPunktTilføjTræningsresultater();
            case 5 -> ...
            case 0 -> System.out.println("Gemmer...");
        }
    }


    private void menuPunktTop5Svømmere(){
        System.out.println("Oversigt over top 5 svømmere junior/senior i alle svømmedicipliner");
        System.out.println("Der skal tasts noget her senere... ");

    }

    private void menuPunktTilføjMedlem(){
        System.out.println("Her udfyldes oplysninger om det nye medlem. Tast 0 undervejs for at annullere og gå tilbage til hovedmenuen.");
        System.out.print("Fulde navn: ");
        String navn = scan.string();
        if (navn.equals("0")) {
            hovedmenu();
            return;
        }
        LocalDate dato = scan.date("Fødselsdato: ");
        if (dato == null) {
            hovedmenu();
            return;
        }
        int månedsdag = dato.getDayOfMonth();
        int måned = dato.getMonthValue();
        int år = dato.getYear();

        boolean iRestance;
        switch(scan.number("Har medlemmet betalt kontingent (1), eller er medlemmet i restance (2)?", 0, 2)){
            case 2 -> iRestance = true;
            case 1 -> iRestance = false;
            default -> {
                hovedmenu();
                return;
            }
        }

        switch (scan.number("Passivt medlem (1), motionist (2) eller konkurrencesvømmer (3): ", 0, 3)) {
            case 0 -> {
                hovedmenu();
                return;
            }
            case 1 -> controller.addMember(navn, månedsdag, måned, år, iRestance, true);
            case 2 -> controller.addMember(navn, månedsdag, måned, år, iRestance,false);
            case 3 -> {
                System.out.println("Butterfly (1), crawl (2), rygcrawl (3) og brystsvømning (4).");
                System.out.println("Vælg én eller flere discipliner, fx 23 for crawl og rygcrawl: ");
                String input = scan.string();
                Set<Svømmedisciplin> discipliner = new TreeSet<>();
                if (input.trim().equals("0")) {
                    hovedmenu();
                    return;
                }
                if (input.contains("1")) {
                    discipliner.add(Svømmedisciplin.BUTTERFLY);
                }
                if (input.contains("2")) {
                    discipliner.add(Svømmedisciplin.CRAWL);
                }
                if (input.contains("3")) {
                    discipliner.add(Svømmedisciplin.RYGCRAWL);
                }
                if (input.contains("4")) {
                    discipliner.add(Svømmedisciplin.BRYSTSVØMNING);
                }
                controller.addMember(navn, månedsdag, måned, år, iRestance, discipliner);
                System.out.print(navn + " er tilføjet som ny konkurrencesvømmer i ");
                StringJoiner sj = new StringJoiner(", ");
                discipliner.forEach(svømmedisciplin -> sj.add(svømmedisciplin.toString().toLowerCase()));
                System.out.println(sj + ".");
                hovedmenu();
                return;
            }
        }
        System.out.println(navn + " er tilføjet som nyt medlem.");
        hovedmenu();
    }

    private void menuPunktKontingentOversigt(){
        System.out.println("Det samlede forventede kontingent er " + controller.sumAfKontingent() + ",00 DKK.");
        hovedmenu();
    }

    private void menuPunktRestanceOversigt(){
        System.out.println("Medlemmer i restance: ");
        for (String navn : controller.visMedlemmerIRestance()) {
            System.out.println("  " + navn);
        }
        hovedmenu();
    }

    private void menuPunktTilføjTræningsresultater() {
        System.out.println("Her oprettes træningsresultater.");
        System.out.println("Tast 0 ved valg af hold eller dato for at annullere og gå tilbage til hovedmenuen.");
        int valg = scan.number("Juniorhold (1) eller seniorhold (2): ", 0, 2);
        if (valg == 0) {
            hovedmenu();
            return;
        }
        List<Konkurrencesvømmer> hold = valg == 1 ? controller.getJuniorsvømmere() : controller.getSeniorsvømmere();
        if (hold.isEmpty()){
            System.out.println("Der er ingen svømmere på holdet");
            hovedmenu();
            return;
        }
        LocalDate træningsdato = scan.date("Træningsdato: ");
        if (træningsdato == null) {
            hovedmenu();
            return;
        }
        System.out.println("Svømmernes resultater indtastes i tidsformat M.S.C, hvor C er hundrededele sekunder med to cifre.");
        System.out.println("Tast 0 undervejs for at springe en svømmers svømmedisciplin over.");
        for (Konkurrencesvømmer svømmer : hold) {
            for (Svømmedisciplin disciplin : svømmer.getAktiveDiscipliner()) {
                Duration resultat = scan.result("Hvilken tid fik " + svømmer.getNavn() + " i " + disciplin.toString().toLowerCase() + "? Svar: ");
                if (resultat != null) {
                    controller.tilføjTræningsresultat(svømmer, disciplin, træningsdato, resultat);
                    System.out.println("Resultat registreret.");
                } else {
                    System.out.println("Sprunget over.");
                }
            }
        }
        hovedmenu();
    }
}