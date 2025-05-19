package basics.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DemoNumeric {

    @Test
    void demoIntegers() {
        System.out.println("Demo Integers");

        // 3 primitive types: short, int, long
        short xs = 3; // 16 bits signed: -2^15 to 2^15-1 (~30k)
        int xi = 1_234_567; // 32 bits signed: -2^31 to 2^31-1 (~2G)
        long xl = 9_876_543_210L; // 64 bits signed: -2^63 to 2^63-1

        System.out.println(xs);
        System.out.println(xi);
        System.out.println(xl);

        // operators: + - / * %
        System.out.println(xi * 3 + (xs - 1) / 2); // xs converted in 'int'
        // Euclide division
        System.out.println(xi % 3); // reminder (reste)
        System.out.println(xi / 3); // divider (quotient)

        int ri = xi % 3;
        short rs = (short) (xi % 3); // explicit conversion
        long rl = xi % 3; // implicit conversion

        int yi = xi * 2000; // !! -1825833296
        // int yi2 = Math.multiplyExact(xi, 2000); // java.lang.ArithmeticException: integer overflow
        System.out.println(yi);

        int mini = Math.min(xi, 30); // calls class method (static keyword)
        System.out.println(mini);
    }

    @Test
    void demoIntegerRanges(){
        System.out.println("Shorts: " + Short.MIN_VALUE + " to " + Short.MAX_VALUE);
        System.out.println("Integers: " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE);
        System.out.println("Longs: " + Long.MIN_VALUE + " to " + Long.MAX_VALUE);
    }

    @Test
    void demoDivisionByZero() {
        int x = 3;
        int y = 0;
        int z = x / y; // java.lang.ArithmeticException: / by zero
        System.out.println(z);
    }

    @Test
    void demoFloats() {
        System.out.println("Demo Floats");
        // specification IEEE754: simple or double precision
        float xf = 3.14F; // 32 bits
        double xd = 3.1459; // 64 bits
        System.out.println(xf);
        System.out.println(xd);
        System.out.println(Math.PI); // 3.141592653589793
        System.out.println(Math.PI * 1_000); // 3141.592653589793
        System.out.println(Math.PI * 1_000_000_000); // 3.141592653589793E9

        // implicit conversion int => double
        System.out.println(1_234_567 / 3.0); // 411522.3333333333
        // implicit conversion float => double
        double yd = xf + xd;
        System.out.println(yd);
        // explicit conversion float => double
        float yf = (float) (xf + Math.PI);
        System.out.println(yf);

        // double => int
        double q = 1_234_567 / 3.0;
        System.out.println(Math.round(q)); // nearest int
        System.out.println(Math.ceil(q)); // int above as double
        System.out.println(Math.floor(q)); // int under as double
    }

    @Test
    void demoSqrtNegativeNumber() {
        double x = -3.14;
        double y = Math.sqrt(x); // NaN
        System.out.println(y);
        System.out.println(y + 3);
        System.out.println(y / 3);
    }

    @Test
    void demoInfinite() {
        double x = 1E308;
        System.out.println(x);
        System.out.println(x * 2); // Infinity
        System.out.println(x / 0.0); // Infinity
        double nan = Double.NaN;
        double inf = Double.POSITIVE_INFINITY;
        System.out.println(nan);
        System.out.println(inf);
        System.out.println(- inf); // -infinity
        System.out.println(inf / inf); // Nan
    }


    @ParameterizedTest
    @CsvSource({
            "3, 6",
            "6, 3",
            "3, 3"
    })
    void demoComparisons(int x, int y) {
        boolean qEquals = x == y;
        boolean qDifferent = x != y;
        boolean qGreater = x > y;
        boolean qLesser = x < y;
        boolean qGreaterEquals = x >= y;
        boolean qLesserEquals = x <= y;
        System.out.println("Equals: " + qEquals);
        System.out.println("Different: " + qDifferent);
        System.out.println("Greater: " + qGreater);
        System.out.println("Lesser: " + qLesser);
        System.out.println("Greater or equals: " + qGreaterEquals);
        System.out.println("Lesser or equals: " + qLesserEquals);
    }
}
