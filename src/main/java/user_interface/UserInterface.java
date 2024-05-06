package user_interface;

import domain_model.Controller;
import domain_model.Konkurrencesvømmer;
import domain_model.Svømmedisciplin;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class UserInterface {

    private Scanner scanner;
    private Controller controller;

    ///////// CONSTRUCTOR ////////////
    public UserInterface() {

        controller = new Controller();

        // Hardcodede medlemmer til test af UI
        controller.tilføjMedlem("Joachim Leth Elgaard", 21, 02, 2001, Set.of(Svømmedisciplin.RYGCRAWL));
        controller.tilføjMedlem("Jens Ellegaard", 22, 9, 1337, Set.of(Svømmedisciplin.CRAWL, Svømmedisciplin.BUTTERFLY));
    }

    ////////// METHODS ///////////
    public void start() {

        // Programmet åbnes (her skal loades)
        scanner = new Scanner(System.in);
        System.out.println("\nVelkommen til Svømmeklubben Delfinens administrative system.");
        hovedmenu();

        // Programmet lukkes (her skal gemmes)
        scanner.close();
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
            0) Gem og luk
            """
        );

        int userInput = scanInt("Indtast menuvalg: ", 0, 4);

        System.out.println();

        switch (userInput) {
            case 1 -> menuPunktTilføjMedlem();
            case 2 -> menuPunktKontingentOversigt();
            case 3 -> menuPunktRestanceOversigt();
            case 4 -> menuPunktTilføjTræningsresultater();
            case 0 -> System.out.println("Gemmer...");
        }
    }

    private int scanInt(String prompt, int minimum, int maximum) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        try {
            int i = Integer.parseInt(input.trim());
            if (i >= minimum && i <= maximum) {
                return i;
            } else {
                System.out.println("Var forkert tal. Prøv igen.");
                return scanInt(prompt, minimum, maximum);
            }
        } catch (NumberFormatException nfe) {
            // Exception fra Integer.parseInt
            System.out.println("Var ikke et heltal. Prøv igen.");
            return scanInt(prompt, minimum, maximum);
        }
    }

    private LocalDate scanDate(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        if (input.trim().equals("0")) {
            return null;
        }
        try {
            String[] date = input.trim().split("/");
            if (date.length == 2 || date.length == 3) {
                int dayOfMonth = Integer.parseUnsignedInt(date[0]);
                int month = Integer.parseUnsignedInt(date[1]);
                int year;
                if (date.length == 2) {
                    // Her håndterer vi, hvis man ikke har skrevet år.
                    year = LocalDate.now().getYear();
                } else {
                    year = Integer.parseUnsignedInt(date[2]);
                    if (year < 100 && year > 9) {
                        if (year <= LocalDate.now().getYear() - 2000) {
                            // Her håndterer vi, hvis man har skrevet 23 i stedet for 2023
                            year += 2000;
                        } else {
                            // Her håndterer vi, hvis man har skrevet 98 i stedet for 1998
                            year += 1900;
                        }
                    }
                }
                return LocalDate.of(year, month, dayOfMonth);
            } else {
                // Der var ingen eller flere end 2 skråstreger.
                System.out.println("Var ikke datoformat D/M eller D/M/Å. Prøv igen.");
                return scanDate(prompt);
            }
        } catch (NumberFormatException nfe) {
            // Exception fra Integer.parseUnsignedInt
            System.out.println("Var ikke kun positive heltal og skråstreg. Prøv igen.");
            return scanDate(prompt);
        } catch (DateTimeException dte) {
            // Exception fra LocalDate.of
            System.out.println("Var ikke en korrekt dato. Prøv igen.");
            return scanDate(prompt);
        }
    }

    private Duration scanResult(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        if (input.trim().equals("0")) {
            return null;
        }
        try {
            String[] tid = input.trim().split("\\.");
            if (tid.length == 3 && tid[2].length() == 2) {
                int minutter = Integer.parseUnsignedInt(tid[0]);
                int sekunder = Integer.parseUnsignedInt(tid[1]);
                int centisekunder = Integer.parseUnsignedInt(tid[2]);
                if (sekunder > 59) {
                    // Der var for mange sekunder.
                    System.out.println("Antal sekunder var ikke under 60. Prøv igen.");
                    return scanResult(prompt);
                }
                int millisekunder = (minutter * 60 + sekunder) * 1000 + centisekunder * 10;
                return Duration.ofMillis(millisekunder);
            } else {
                // Der var færre eller flere end 2 punktummer eller C have færre eller flere end 2 cifre.
                System.out.println("Var ikke tidsformat M.S.C, hvor C er hundrededele sekunder med to cifre. Prøv igen.");
                return scanResult(prompt);
            }
        } catch (NumberFormatException nfe) {
            // Exception fra Integer.parseUnsignedInt
            System.out.println("Var ikke kun positive heltal og punktum. Prøv igen.");
            return scanResult(prompt);
        }
    }

    private void menuPunktTilføjMedlem(){
        System.out.println("Her udfyldes oplysninger om det nye medlem. Tast 0 undervejs for at annullere og gå tilbage til hovedmenuen.");
        System.out.print("Fulde navn: ");
        String navn = scanner.nextLine().trim();
        if (navn.equals("0")) {
            hovedmenu();
            return;
        }
        LocalDate dato = scanDate("Fødselsdato: ");
        if (dato == null) {
            hovedmenu();
            return;
        }
        int månedsdag = dato.getDayOfMonth();
        int måned = dato.getMonthValue();
        int år = dato.getYear();
        switch (scanInt("Passivt medlem (1), motionist (2) eller konkurrencesvømmer (3): ", 0, 3)) {
            case 0 -> {
                hovedmenu();
                return;
            }
            case 1 -> controller.tilføjMedlem(navn, månedsdag, måned, år, true);
            case 2 -> controller.tilføjMedlem(navn, månedsdag, måned, år, false);
            case 3 -> {
                System.out.println("Butterfly (1), crawl (2), rygcrawl (3) og brystsvømning (4).");
                System.out.println("Vælg én eller flere discipliner, fx 23 for crawl og rygcrawl: ");
                String input = scanner.nextLine();
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
                controller.tilføjMedlem(navn, månedsdag, måned, år, discipliner);
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
        int valg = scanInt("Juniorhold (1) eller seniorhold (2)? Svar: ", 0, 2);
        if (valg == 0) {
            hovedmenu();
            return;
        }
        LocalDate træningsdato = scanDate("Træningsdato: ");
        if (træningsdato == null) {
            hovedmenu();
            return;
        }
        System.out.println("Svømmernes resultater indtastes i tidsformat M.S.C, hvor C er hundrededele sekunder med to cifre.");
        System.out.println("Tast 0 undervejs for at springe en svømmers svømmedisciplin over.");
        List<Konkurrencesvømmer> hold = valg == 1 ? controller.getJuniorsvømmere() : controller.getSeniorsvømmere();
        for (Konkurrencesvømmer svømmer : hold) {
            for (Svømmedisciplin disciplin : svømmer.getAktiveDiscipliner()) {
                Duration resultat = scanResult("Hvilken tid fik " + svømmer.getNavn() + " i " + disciplin.toString().toLowerCase() + "? Svar: ");
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