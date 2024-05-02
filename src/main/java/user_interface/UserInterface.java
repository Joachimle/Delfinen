package user_interface;

import java.util.Scanner;

public class UserInterface {
    Scanner input = new Scanner(System.in);

    ///////// CONSTRUCTOR ////////////
    public UserInterface() {
        System.out.println("Velkommen til Svømmeklubben Delfinen's administrative system");

    }

    ////////// METHODS ///////////
    public void menuPunkt1(){
        System.out.println("Tast 1 for at tilføje et nyt medlem:");
        String userInput = input.nextLine();
        if(userInput.equals("1")){
            //TODO
        } else {
            //TODO
        }

    }


}
