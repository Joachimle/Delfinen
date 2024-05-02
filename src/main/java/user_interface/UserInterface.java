package user_interface;

import domain_model.Controller;

import java.util.Scanner;

public class UserInterface {
    Scanner input = new Scanner(System.in);
    Controller controller = new Controller();

    ///////// CONSTRUCTOR ////////////
    public UserInterface() {
        System.out.println("Velkommen til Svømmeklubben Delfinen's administrative system");
        input.useDelimiter("\n");
        menuPunkt1();
    }

    ////////// METHODS ///////////
    public void menuPunkt1(){
        System.out.println("Tast 1 for at tilføje et nyt medlem:");
        String userInput = input.next();
        if(userInput.equals("1")){
            System.out.println("Du er ved at tilføje et nyt medlem:");
            System.out.println("Du skal indtaste det nye medlems navn og fødselsdato...\n");
            System.out.println("Indtast det nye medlems fulde navn: ");
            String userNavn = input.next();
            System.out.println("Indtast månedsdag som et tal: ");
            int userMånedsdag = input.nextInt();
            System.out.println("Indtast måned som et tal: ");
            int userMåned = input.nextInt();
            System.out.println("Indtast årstal: ");
            int userÅr = input.nextInt();
            System.out.println("Skal det nye medlem registreres som aktivt eller passivt?");
            boolean userAktivtMedlem = input.next().equals("aktivt");

            controller.tilføjMedlem(userNavn, userMånedsdag, userMåned, userÅr, userAktivtMedlem);
            System.out.println("Du har tilføjet: " + userNavn + " som nyt medlem." );
        } else {
            //TODO
        }

    }


}
