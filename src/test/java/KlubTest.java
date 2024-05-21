import domain_model.Klub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KlubTest {

    @DisplayName("Korrekt kontingentberegning")
    @Test
    void kontingentBeregning() {
        // Arrrange
        Klub klub = new Klub();
        klub.addMember("Frederik André Henrik Christian", 26, 5, 1968, false, true); // Passivt medlem
        klub.addMember("Christian Valdemar Henri John", 10, 10, 2007, false, false); // Under 18
        klub.addMember("Mary Elizabeth", 5, 2, 1972, false, false); // Mellem 18 og 60
        klub.addMember("Margrethe Alexandrine Þórhildur Ingrid", 16, 4, 1940, false, false); // Over 60
        // Act
        int actual = klub.sumAfKontingent();
        // Assert
        assertEquals(4300, actual);
    }
}