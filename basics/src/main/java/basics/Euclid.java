package basics;

/**
 * The class Euclid contains mathematical functions implementing algorithms
 * from the famous Greek mathematician Euclid.
 */
public class Euclid {

    // Algorithm: https://fr.wikipedia.org/wiki/Algorithme_d%27Euclide

    /**
     * Compute the greatest common divider of strictly positive integers.
     * @param a first number
     * @param b second number
     * @return the greatest common divider
     * @throws ArithmeticException if any argument is negative or zero
     */
    public static int gcd(int a, int b){
        // wrong parameters: negative or zero argument
        if ((a<=0) || (b <= 0)) throw new ArithmeticException("Euclid gcd needs two numbers strictly positive");
        // nominal case with good arguments
        while (a != b) {
            if (a > b) a = a - b;
            else b = b - a;
        }
        return a;
    }
}
