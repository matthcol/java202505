package basics.tu;

import basics.Euclide;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class EuclideTest {

    @ParameterizedTest
    @CsvSource({
            "1, 1, 1",
            "1, 12, 1",
            "12, 1, 1",
            "15, 21, 3",
            "21, 15, 3"
    })
    void testGcd(int a, int b, int expectedGcd) {
        int actualGcd = Euclide.gcd(a, b);
        assertEquals(expectedGcd, actualGcd);
    }
}