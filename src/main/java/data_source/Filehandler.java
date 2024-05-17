package data_source;

import domain_model.Medlem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Filehandler {

    //////// ATTRIBUTES ///////////
    private static final File medlemsFil = new File("src/main/resources/medlemsdata.csv");
    private static final File træningsresultatFil = new File("src/main/resources/træningsdata.csv");

    //////// METHODS ///////////
    public static List<Medlem> loadMedlemsdata() {
        List<Medlem> medlemmer = new ArrayList<>();

        Scanner scanner;
        try {
            scanner = new Scanner(medlemsFil);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while(scanner.hasNext()){
            String line = scanner.nextLine();
            String[] values = line.split(";");
            Medlem medlem = new Medlem(
                    values[0],
                    Integer.parseInt(values[1]),
                    Integer.parseInt(values[2]),
                    Integer.parseInt(values[3]),
                    Boolean.parseBoolean(values[4]),
                    Boolean.parseBoolean(values[5])
            );
            medlemmer.add(medlem);
        }

        scanner.close();
        return medlemmer;
    }

    public static void saveMedlemsdata(List<Medlem> medlemmer) {
        PrintStream printStream;
        try {
            printStream = new PrintStream(medlemsFil);
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }

        for (Medlem medlem : medlemmer) {
            printStream.println(medlem);
        }

        printStream.close();
    }

}
