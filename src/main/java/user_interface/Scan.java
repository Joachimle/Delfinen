package user_interface;

import domain_model.Svømmedisciplin;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Scan {
    ///////// ATTRIBUTES ///////////
    private final Scanner scanner;

    ///////// CONSTRUCTOR ///////////
    public Scan() {
        scanner = new Scanner(System.in);
    }

    ///////// METHODS //////////
    public String string(){
        return scanner.nextLine();
    }

    public int number(String prompt, int minimum, int maximum) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        try {
            int i = Integer.parseInt(input.trim());
            if (i >= minimum && i <= maximum) {
                return i;
            } else {
                System.out.println("Var forkert tal. Prøv igen.");
                return number(prompt, minimum, maximum);
            }
        } catch (NumberFormatException nfe) {
            // Exception fra Integer.parseInt
            System.out.println("Var ikke et heltal. Prøv igen.");
            return number(prompt, minimum, maximum);
        }
    }

    public LocalDate date(String prompt) {
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
                return date(prompt);
            }
        } catch (NumberFormatException nfe) {
            // Exception fra Integer.parseUnsignedInt
            System.out.println("Var ikke kun positive heltal og skråstreg. Prøv igen.");
            return date(prompt);
        } catch (DateTimeException dte) {
            // Exception fra LocalDate.of
            System.out.println("Var ikke en korrekt dato. Prøv igen.");
            return date(prompt);
        }
    }

    public Duration result(String prompt) {
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
                    return result(prompt);
                }
                int millisekunder = (minutter * 60 + sekunder) * 1000 + centisekunder * 10;
                return Duration.ofMillis(millisekunder);
            } else {
                // Der var færre eller flere end 2 punktummer eller C have færre eller flere end 2 cifre.
                System.out.println("Var ikke tidsformat M.S.C, hvor C er hundrededele sekunder med to cifre. Prøv igen.");
                return result(prompt);
            }
        } catch (NumberFormatException nfe) {
            // Exception fra Integer.parseUnsignedInt
            System.out.println("Var ikke kun positive heltal og punktum. Prøv igen.");
            return result(prompt);
        }
    }

    public Set<Svømmedisciplin> disclipliner() {
        System.out.println("Butterfly (1), crawl (2), rygcrawl (3) og brystsvømning (4).");
        System.out.print("Vælg én eller flere discipliner, fx 23 for crawl og rygcrawl: ");
        String input = scanner.nextLine();
        if (input.trim().equals("0")) {
            return null;
        }
        Set<Svømmedisciplin> discipliner = new TreeSet<>();
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
        return discipliner;
    }
}