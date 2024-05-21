package user_interface;

import domain_model.*;

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
        controller.addMember("Margrethe Alexandrine Þórhildur Ingrid", 16, 4, 1940, false, Set.of(Svømmedisciplin.RYGCRAWL, Svømmedisciplin.BRYSTSVØMNING));
        controller.addMember("Frederik André Henrik Christian", 26, 5, 1968, false, Set.of(Svømmedisciplin.CRAWL));
        controller.addMember("Mary Elizabeth", 5, 2, 1972, false, Set.of(Svømmedisciplin.BUTTERFLY));
        controller.addMember("Christian Valdemar Henri John", 10, 10, 2005, false, Set.of(Svømmedisciplin.CRAWL, Svømmedisciplin.BRYSTSVØMNING, Svømmedisciplin.BUTTERFLY));

        // Hardcodede træningsresultater
        Random rnd = new Random();
        List<Konkurrencesvømmer> svømmere = controller.getSeniorsvømmere();
        for (int i = 0; i < 500; i++) {
            Konkurrencesvømmer svømmer = svømmere.get(rnd.nextInt(svømmere.size()));
            List<Svømmedisciplin> discipliner = svømmer.getAktiveDiscipliner().stream().toList();
            Svømmedisciplin disciplin = discipliner.get(rnd.nextInt(discipliner.size()));
            controller.tilføjTræningsresultat(svømmer, disciplin, Duration.ofMillis(rnd.nextInt(600000)), LocalDate.now());
        }
    }

    ////////// METHODS ///////////
    public void start() {

        // Programmet åbnes (her skal loades)
        scan = new Scan();
        System.out.println("\nVelkommen til Svømmeklubben Delfinens administrative system.");
        hovedmenu();

        // Programmet lukkes (her skal gemmes)
        System.out.println("Programmet er lukket.");
    }

    ////////// HJÆLPEMETODER/AUXILIARY METHODS ///////////
    private void hovedmenu() {

        System.out.println("""
            
            Hovedmenu
            1) Se alle medlemmer og resultater
            2) Tilføj nyt medlem
            3) Se samlede forventede kontingent
            4) Se medlemmer i restance
            5) Tilføj træningsresultater
            6) Top 5 svømmere
            0) Gem og luk
            """
        );

        int userInput = scan.number("Indtast menuvalg: ", 0, 6);

        System.out.println();

        switch (userInput) {
            case 1 -> seAlt();
            case 2 -> menuPunktTilføjMedlem();
            case 3 -> menuPunktKontingentOversigt();
            case 4 -> menuPunktRestanceOversigt();
            case 5 -> menuPunktTilføjTræningsresultater();
            case 6 -> menuPunktTop5Svømmere();
            case 0 -> System.out.println("Gemmer...");
        }
    }

    private void menuPunktTop5Svømmere(){
        System.out.println("Her kan vises et holds top 5 svømmere indenfor en disciplin. Tast 0 undervejs for at annullere og gå tilbage til hovedmenuen.");
        int valgHold = scan.number("Juniorhold (1) eller seniorhold (2): ", 0, 2);
        if (valgHold == 0) {
            hovedmenu();
            return;
        }

        boolean juniorHold = valgHold == 1;

        int valgDisciplin = scan.number("Butterfly (1), crawl (2), rygcrawl (3) og brystsvømning (4): ", 0, 4);
        if (valgDisciplin == 0) {
            hovedmenu();
            return;
        }

        Svømmedisciplin disciplin = switch (valgDisciplin) {
            case 1 -> Svømmedisciplin.BUTTERFLY;
            case 2 -> Svømmedisciplin.CRAWL;
            case 3 -> Svømmedisciplin.RYGCRAWL;
            default -> Svømmedisciplin.BRYSTSVØMNING;
        };

        if (controller.visTop5Svømmere(disciplin, juniorHold).isEmpty()){
            System.out.println(STR."\nDer er ingen registrerede resultater i \{disciplin.toString().toLowerCase()} på \{valgHold == 1 ? "ju" : "se"}niorholdet.");
        } else {
            System.out.println(STR."\nTop 5 \{valgHold == 1 ? "ju" : "se"}niorsvømmere i \{disciplin.toString().toLowerCase()}:");
        }

        for (String topSvømmer : controller.visTop5Svømmere(disciplin, juniorHold)) {
            System.out.println("  " + topSvømmer);
        }

        hovedmenu();
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
        switch(scan.number("Har medlemmet betalt kontingent (1) eller er medlemmet i restance (2): ", 0, 2)){
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
                Set<Svømmedisciplin> discipliner = scan.disclipliner();
                controller.addMember(navn, månedsdag, måned, år, iRestance, discipliner);
                System.out.println(STR."\{navn} er tilføjet som nyt medlem, der er konkurrencesvømmer i \{sd(discipliner)}.");
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
                    controller.tilføjTræningsresultat(svømmer, disciplin, resultat, træningsdato);
                    System.out.println("Resultat registreret.");
                } else {
                    System.out.println("Sprunget over.");
                }
            }
        }
        hovedmenu();
    }

    private void seAlt() {
        System.out.println("Alle medlemmer\n");
        List<Medlem> medlemmer = controller.getMedlemmer();
        int medlemNummer = 0;
        for (Medlem medlem : medlemmer) {
            System.out.println(STR."""
                \{++medlemNummer}) \{medlem.getNavn()}
                   Født den \{medlem.getFødselsdato()}
                   \{medlem.getIRestance()} a \{medlem.kontingentPris()},00 DKK
                   \{medlem.getPassivtMedlem()}
                   \{medlem instanceof Konkurrencesvømmer ks ? ((medlem.erMindreårig() ? "Ju" : "Se") + "niorsvømmer " + (ks.getAktiveDiscipliner().isEmpty() ? "uden aktive discipliner" : STR."i \{sd(ks.getAktiveDiscipliner())}")) : "Motionist"}
                """);
        }
        System.out.println("0) Tilbage til hovedmenu\n");
        int valg = scan.number("Indtast nummer på medlem: ", 0, medlemNummer);
        if (valg == 0) {
            hovedmenu();
            return;
        }
        Medlem valgt = medlemmer.get(valg - 1);
        String ekstraPunkter = valgt instanceof Konkurrencesvømmer ? "6) Konvertér til motionssvømmer\n7) Aktive discipliner\n8) Træningsresultater\n9) Konkurrenceresultater" : "6) Konvertér til konkurrencesvømmer";
        System.out.println(STR."""

            Medlemsmenu
            1) Redigér navn fra '\{valgt.getNavn()}'
            2) Redigér fødselsdato fra \{valgt.getFødselsdato()}
            3) Redigér betaling fra '\{valgt.getIRestance()}'
            4) Redigér status fra '\{valgt.getPassivtMedlem()}'
            5) Slet dette medlem
            \{ekstraPunkter}
            0) Tilbage til hovedmenu
            """);
        int valg2 = scan.number("Indtast valg: ", 0, valgt instanceof Konkurrencesvømmer ? 9 : 6);
        System.out.println();
        switch (valg2) {
            case 1 -> {
                System.out.print("Indtast nyt navn eller tast 0 for at fortryde: ");
                String valg3 = scan.string();
                if (valg3.equals("0")) {
                    System.out.println("Navnet blev ikke ændret.");
                } else {
                    valgt.setNavn(valg3);
                }
            }
            case 2 -> {
                LocalDate ny = scan.date("Ny fødselsdato: ");
                if (ny == null) {
                    System.out.println("Fødselsdato blev ikke ændret.");
                } else {
                    valgt.setFødselsdato(ny.getDayOfMonth(), ny.getMonthValue(), ny.getYear());
                }
            }
            case 3 -> valgt.setiRestance(scan.number("Har medlemmet betalt kontingent (1), eller er medlemmet i restance (2)?", 1, 2) == 2);
            case 4 -> valgt.setPassivtMedlem(scan.number("Passivt medlem (1) eller aktivt medlem (2)?", 1, 2) == 2);
            case 5 -> {
                System.out.println(STR."\u001B[91mAdvarsel:\u001B[0m Er du sikker på, at du vil slette \{valgt.getNavn()} som medlem inklusiv alt vedr. data?");
                int valg3 = scan.number("Slet permanent (1) eller fortryd (0): ", 0, 1);
                if (valg3 == 1) {
                    controller.slet(valgt);
                } else {
                    System.out.println(valgt.getNavn() + " blev ikke slettet.");
                }
            }
            case 6 -> {
                if (valgt instanceof Konkurrencesvømmer konvertit) {
                    System.out.println("\u001B[91mAdvarsel:\u001B[0m Når et medlem konverteres fra konkurrencesvømmer til motionist, slettes alt medlemmets data vedr. aktive discipliner, træningsresultater og konkurrenceresultater.");
                    switch (scan.number(STR."Konvertér \{konvertit.getNavn()} til passivt medlem (1), motionist (2) eller fortryd (0): ", 0, 2)) {
                        case 1 -> {
                            controller.konverter(konvertit, true);
                            System.out.println(STR."\{konvertit.getNavn()} er konverteret til motionist med passivt medlemskab.");
                        }
                        case 2 -> {
                            controller.konverter(konvertit, false);
                            System.out.println(STR."\{konvertit.getNavn()} er konverteret til motionist og alt resultatdata er slettet.");
                        }
                        case 0 -> System.out.println(STR."\{konvertit.getNavn()} forbliver konkurrencesvømmer.");
                    }
                } else {
                    System.out.println(STR."For at blive konverteret til konkurrencesvømmer skal \{valgt.getNavn()} registreres med aktive displiciner.\nTast 0 for at annullere og gå tilbage til hovedmenuen.");
                    Set<Svømmedisciplin> discipliner = scan.disclipliner();
                    if (discipliner == null) {
                        System.out.println(STR."\{valgt.getNavn()} forbliver motionist.");
                    } else {
                        controller.konverter(valgt, discipliner);
                        System.out.println(STR."\{valgt.getNavn()} er konverteret til konkurrencesvømmer i \{sd(discipliner)}.");
                    }
                }
            }
            case 7 -> {
                Konkurrencesvømmer ks = (Konkurrencesvømmer) valgt;
                System.out.println(STR."\{ks.getNavn()} er registreret indenfor \{sd(ks.getAktiveDiscipliner())}.\nTast 0 for at annullere og gå tilbage til hovedmenuen.");
                Set<Svømmedisciplin> discipliner = scan.disclipliner();
                if (discipliner == null) {
                    System.out.println(STR."\{ks.getNavn()} fik ikke ændret sine svømmediscipliner.");
                } else {
                    ks.setAktiveDiscipliner(discipliner);
                    System.out.println(STR."\{valgt.getNavn()} fik ændret sine svømmediscipliner til \{sd(discipliner)}.");
                }
            }
            case 8 -> {
                Konkurrencesvømmer ks = (Konkurrencesvømmer) valgt;
                System.out.println(STR."Træningsresultater for \{ks.getNavn()}");
                int trNummer = 0;
                List<Træningsresultat> trr = controller.getTræningsresultater(ks);
                for (Træningsresultat tr : trr) {
                    System.out.println(++trNummer + ") " + tr);
                }
                System.out.println(++trNummer + ") Opret nyt træningsresultat\n0) Tilbage til hovedmenu\n");
                int valg3 = scan.number("Indtast nummer på træningsresultat, som skal slettes: ", 0, trNummer);
                if (valg3 == trNummer) {
                    boolean b = false;
                    Svømmedisciplin sd = switch (scan.number("Vælg butterfly (1), crawl (2), rygcrawl (3) og brystsvømning (4): ", 1, 4)) {
                        case 1 -> Svømmedisciplin.BUTTERFLY;
                        case 2 -> Svømmedisciplin.CRAWL;
                        case 3 -> Svømmedisciplin.RYGCRAWL;
                        case 4 -> Svømmedisciplin.BRYSTSVØMNING;
                        default -> {
                            b = true;
                            yield Svømmedisciplin.BRYSTSVØMNING;
                        }
                    };
                    if (b) {
                        break;
                    }
                    Duration r = scan.result("Tid: ");
                    if (r == null) {
                        break;
                    }
                    LocalDate d = scan.date("Dato: ");
                    if (d == null) {
                        break;
                    }
                    controller.tilføjTræningsresultat(ks, sd, r, d);
                    System.out.println("Træningsresultat tilføjet.");
                } else if (valg3 == 0) {} else {
                    controller.slet(trr.get(valg3 - 1));
                    System.out.println("Følgende træningsresultat for " + ks.getNavn() + " blev slettet: " + trr.get(valg3 - 1));
                }
            }
            case 9 -> {
                Konkurrencesvømmer ks = (Konkurrencesvømmer) valgt;
                System.out.println(STR."Konkurrenceresultater for \{ks.getNavn()}");
                int krNummer = 0;
                List<Konkurrenceresultat> krr = controller.getKonkurrenceresultater(ks);
                for (Konkurrenceresultat kr : krr) {
                    System.out.println(++krNummer + ") " + kr);
                }
                System.out.println(++krNummer + ") Opret nyt konkurrenceresultat\n0) Tilbage til hovedmenu\n");
                int valg3 = scan.number("Indtast nummer på konkurrenceresultat, som skal slettes: ", 0, krNummer);
                if (valg3 == krNummer) {
                    boolean b = false;
                    Svømmedisciplin sd = switch (scan.number("Vælg butterfly (1), crawl (2), rygcrawl (3) og brystsvømning (4): ", 1, 4)) {
                        case 1 -> Svømmedisciplin.BUTTERFLY;
                        case 2 -> Svømmedisciplin.CRAWL;
                        case 3 -> Svømmedisciplin.RYGCRAWL;
                        case 4 -> Svømmedisciplin.BRYSTSVØMNING;
                        default -> {
                            b = true;
                            yield Svømmedisciplin.BRYSTSVØMNING;
                        }
                    };
                    if (b) {
                        break;
                    }
                    Duration r = scan.result("Tid: ");
                    if (r == null) {
                        break;
                    }
                    LocalDate d = scan.date("Dato: ");
                    if (d == null) {
                        break;
                    }
                    System.out.print("Indtast navn på stævne: ");
                    String stævne = scan.string();
                    if (stævne.trim().equals("0")) {
                        break;
                    }
                    int placering = scan.number("Indtast placering: ", 0, Integer.MAX_VALUE);
                    if (placering == 0) {
                        break;
                    }
                    controller.tilføjKonkurrenceresultat(ks, sd, r, d, stævne, placering);
                    System.out.println("Konkurrenceresultat tilføjet.");
                } else if (valg3 == 0) {} else {
                    controller.slet(krr.get(valg3 - 1));
                    System.out.println("Følgende konkurrenceresultat for " + ks.getNavn() + " blev slettet: " + krr.get(valg3 - 1));
                }
            }
        }
        hovedmenu();
    }

    private String sd(Set<Svømmedisciplin> discipliner) {
        StringJoiner sj = new StringJoiner(", ");
        discipliner.forEach(svømmedisciplin -> sj.add(svømmedisciplin.toString().toLowerCase()));
        String s = sj.toString();
        int index = s.lastIndexOf(',');
        if (index == -1) {
            return s;
        }
        return s.substring(0, index) + " og" + s.substring(index + 1);
    }
}