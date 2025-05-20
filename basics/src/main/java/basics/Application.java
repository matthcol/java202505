package basics;

public class Application {
    public static void main(String[] args) {
        // Sortie standard
        System.out.println("Coucou c'est lundi !");
        System.out.println("Apprenons Java ensemble");

        // Sortie d'erreur
        System.err.println("Ceci est une erreur !");

        int g = Euclid.gcd(15, 21);
        System.out.println("Gcd = " + g);
    }
}
