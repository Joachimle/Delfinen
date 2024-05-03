package user_interface;

import domain_model.Controller;

import java.util.Scanner;

public class UserInterface {
    Scanner input = new Scanner(System.in);
    Controller controller = new Controller();

    ///////// CONSTRUCTOR ////////////
    public UserInterface() {
        System.out.println("\nVelkommen til Svømmeklubben Delfinen's administrative system");
        input.useDelimiter("\n");
        hovedmenu();
    }

    ////////// METHODS ///////////

    public void hovedmenu(){
        System.out.println("Velkommen til hovedmenuen");
        System.out.println("Du har følgende valgmuligheder:\n");
        System.out.println("1: Tilføj nyt medlem");
        System.out.println("2: Se oversigt over klubbens indtægter");
        System.out.println("0: Afslut programmet");
        System.out.print("\nIndtast tallet for det menupunkt, du ønsker at tilgå: ");

        String userInput = input.next();
        switch (userInput){
            case "1" -> menuPunktTilføjMedlem();
            case "2" -> menuPunktKontingentOversigt();
            case "0" -> System.out.println("Afslutter programmet...");
            default -> {
                System.out.println("\nDit input svarede ikke til et af menupunkterne.");
                System.out.println("Prøv igen:\n");
                hovedmenu();
            }
        }
    }

    ////////// HJÆLPEMETODER/AUXILIARY METHODS ///////////

    private void menuPunktTilføjMedlem(){
        System.out.println("\nDu er ved at tilføje et nyt medlem:");
        System.out.println("Du skal indtaste det nye medlems navn og fødselsdato...\n");
        System.out.print("Indtast det nye medlems fulde navn: ");
        String userNavn = input.next();
        System.out.print("Indtast månedsdag som et tal: ");
        int userMånedsdag = input.nextInt();
        System.out.print("Indtast måned som et tal: ");
        int userMåned = input.nextInt();
        System.out.print("Indtast årstal: ");
        int userÅr = input.nextInt();
        //TODO aktivt/aktiv
        System.out.println("Skal det nye medlem registreres som aktivt eller passivt?");
        boolean userAktivtMedlem = input.next().equals("aktivt");

        controller.tilføjMedlem(userNavn, userMånedsdag, userMåned, userÅr, userAktivtMedlem);
        System.out.println("Du har tilføjet: " + userNavn + " som nyt medlem.\n" );

        hovedmenu();
    }

    private void menuPunktKontingentOversigt(){
        System.out.println("\nSummen af klubbens årlige kontingent indbetalinger er: ");
        System.out.println(controller.sumAfKontingent() + " dkk.\n");

        hovedmenu();
    }

}
