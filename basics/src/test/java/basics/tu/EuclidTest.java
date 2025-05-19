package basics.tu;

// from my project (src/main/java or src/test/java)
import basics.Euclid;

// from dependencies
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// from Java SE
import java.util.concurrent.TimeUnit;

// import static
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;

// needed for all algorithms with a while loop
@Timeout(value = 1, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD)
class EuclidTest {

    @ParameterizedTest
    @CsvSource({
            "1, 1, 1",
            "1, 12, 1",
            "12, 1, 1",
            "15, 21, 3",
            "21, 15, 3",
    })
    void testGcd_ok(int a, int b, int expectedGcd) {
        int actualGcd = Euclid.gcd(a, b);
        assertEquals(expectedGcd, actualGcd);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "1, 0",
            "0, 1",
            "-1, 1",
            "1, -1",
            "-1, -1",
    })
    void testGcd_ko(int a, int b) {
        assertThrows(
                ArithmeticException.class, // type of expected exception
                () -> Euclid.gcd(a, b) // anonynous function wrapping the call of tested exception
        );
    }


}