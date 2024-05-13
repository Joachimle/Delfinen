package domain_model;

import java.time.Duration;
import java.time.LocalDate;

public record Træningsresultat(Konkurrencesvømmer svømmer, Svømmedisciplin disciplin, LocalDate dato, Duration resultat) {}