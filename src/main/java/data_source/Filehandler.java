package data_source;

import domain_model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class Filehandler {

    //////// ATTRIBUTES ///////////
    private static File medlemsFil = new File("src/main/resources/medlemsdata.csv");
    private static File resultatFil = new File("src/main/resources/træningsdata.csv");

    //////// METHODS ///////////
    public static List[] load() {

        // LOAD MEDLEMMER //
        List<Medlem> medlemmer = new ArrayList<>();
        Scanner scanMedlemmer;
        try {
            scanMedlemmer = new Scanner(medlemsFil);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(scanMedlemmer.hasNext()){
            String line = scanMedlemmer.nextLine();
            String[] values = line.split(";");
            String input = values[6];
            Medlem medlem;
            if (input.equals("0")) {
                medlem = new Medlem(
                        values[0],
                        Integer.parseInt(values[1]),
                        Integer.parseInt(values[2]),
                        Integer.parseInt(values[3]),
                        Boolean.parseBoolean(values[4]),
                        Boolean.parseBoolean(values[5])
                );
            } else {
                Set<Svømmedisciplin> asd = new TreeSet<>();
                if (input.contains("1")) {
                    asd.add(Svømmedisciplin.BUTTERFLY);
                }
                if (input.contains("2")) {
                    asd.add(Svømmedisciplin.CRAWL);
                }
                if (input.contains("3")) {
                    asd.add(Svømmedisciplin.RYGCRAWL);
                }
                if (input.contains("4")) {
                    asd.add(Svømmedisciplin.BRYSTSVØMNING);
                }
                medlem = new Konkurrencesvømmer(
                        values[0],
                        Integer.parseInt(values[1]),
                        Integer.parseInt(values[2]),
                        Integer.parseInt(values[3]),
                        Boolean.parseBoolean(values[4]),
                        asd
                );
            }
            medlemmer.add(medlem);
            medlem.setID(Integer.parseInt(values[7]));
        }
        scanMedlemmer.close();

        // LOAD RESULTATER //
        List<Resultat> resultater = new ArrayList<>();
        Scanner scanResultater;
        try {
            scanResultater = new Scanner(resultatFil);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(scanResultater.hasNext()){
            String line = scanResultater.nextLine();
            String[] values = line.split(";");
            String input = values[5];
            Resultat resultat;
            Konkurrencesvømmer svømmer = null;
            for (Medlem medlem : medlemmer) {
                if (medlem.getID() == Integer.parseInt(values[0])) {
                    svømmer = (Konkurrencesvømmer) medlem;
                }
            }
            Svømmedisciplin sd = Svømmedisciplin.valueOf(values[1]);
            Duration r = Duration.ofMillis(Integer.parseInt(values[2]));
            String[] d = values[3].split("/");
            LocalDate dato = LocalDate.of(Integer.parseInt(d[2]), Integer.parseInt(d[1]), Integer.parseInt(d[0]));
            if (input.equals("$")) {
                resultat = new Træningsresultat(
                    svømmer,
                    sd,
                    r,
                    dato
                );
            } else {
                resultat = new Konkurrenceresultat(
                    svømmer,
                    sd,
                    r,
                    dato,
                    values[4],
                    Integer.parseInt(values[5])
                );
            }
            resultater.add(resultat);
        }
        scanResultater.close();

        // RETURNÉR BEGGE
        return new List[]{medlemmer, resultater};
    }

    public static void save(List<Medlem> medlemmer, List<Resultat> resultater) {

        // GEM MEDLEMMER //
        PrintStream psMedlemmer;
        try {
            psMedlemmer = new PrintStream(medlemsFil);
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
        int ID = 0;
        for (Medlem medlem : medlemmer) {
            psMedlemmer.print(medlem + ";");
            if (medlem instanceof Konkurrencesvømmer ks) {
                Set<Svømmedisciplin> asd = ks.getAktiveDiscipliner();
                if (asd.contains(Svømmedisciplin.BUTTERFLY)) {
                    psMedlemmer.print("1");
                }
                if (asd.contains(Svømmedisciplin.CRAWL)) {
                    psMedlemmer.print("2");
                }
                if (asd.contains(Svømmedisciplin.RYGCRAWL)) {
                    psMedlemmer.print("3");
                }
                if (asd.contains(Svømmedisciplin.BRYSTSVØMNING)) {
                    psMedlemmer.print("4");
                }
            } else {
                psMedlemmer.print("0");
            }
            medlem.setID(++ID);
            psMedlemmer.println(";" + medlem.getID());
        }
        psMedlemmer.close();

        // GEM RESULTATER //
        PrintStream psResultater;
        try {
            psResultater = new PrintStream(resultatFil);
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
        for (Resultat r : resultater) {
            StringJoiner sj = new StringJoiner(";");
            sj.add(r.svømmer().getID() + "");
            sj.add(r.disciplin().toString());
            sj.add(((int) r.resultat().toMillis()) + "");
            LocalDate d = r.dato();
            sj.add(d.getDayOfMonth() + "/" + d.getMonthValue() + "/" + d.getYear());
            if (r instanceof Konkurrenceresultat kr) {
                sj.add(kr.stævne());
                sj.add(kr.placering() + "");
            } else {
                sj.add("$");
                sj.add("$");
            }
            psResultater.println(sj);
        }
        psResultater.close();
    }
}
