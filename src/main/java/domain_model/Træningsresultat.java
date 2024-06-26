package domain_model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Træningsresultat(Konkurrencesvømmer svømmer, Svømmedisciplin disciplin, Duration resultat, LocalDate dato) implements Resultat {
    @Override
    public int compareTo(Resultat that) {
        return this.resultat.compareTo(that.resultat());
    }
    @Override
    public String toString() {
        int centisekunder = (int) resultat.toMillis() / 10;
        int sekunder = centisekunder / 100;
        centisekunder %= 100;
        int minutter = sekunder / 60;
        sekunder %= 60;
        String tid = minutter + "." + sekunder + "." + centisekunder;
        String d = dato.getDayOfMonth() + "/" + dato.getMonthValue() + "/" + dato.getYear();
        return tid + " | " + disciplin.toString().toLowerCase() + " | " + d;
    }
}