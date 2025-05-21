package basics.demo;

import org.junit.jupiter.api.Test;

import java.text.Collator;
import java.util.*;
import java.util.stream.Stream;


public class DemoCollections {

    @Test
    void demoList() {
        // Java 11: list creation simplified BUT not mutable !
        List<String> cities = List.of("Toulouse", "Pau", "Avignon");
        System.out.println(cities);

        // read list

        // - read at index
        String city0 = cities.get(0);
        System.out.println("Cities[0] = " + city0);
        // String cityN = cities.get(4); // java.lang.ArrayIndexOutOfBoundsException: Index 4 out of bounds for length 3

        // - size
        System.out.println(cities.size());

        // loop: iterate on its elements

        // - foreach loop: Java 5
        System.out.println();
        System.out.println("List of cities:");
        for (String city: cities) {
            System.out.println("\t- " + city);
        }

        // - Java 11 simplification: keyword 'var'
        System.out.println();
        System.out.println("List of cities (2):");
        for (var city: cities) {
            System.out.println("\t- " + city);
        }

        // - Java 8: function forEach
        System.out.println();
        System.out.println("List of cities (3):");
        cities.forEach(System.out::println);

        System.out.println();
        System.out.println("List of cities (4):");
        cities.forEach(city -> System.out.println("\t- " + city));

        // for-i: Java "1" (old school)
        System.out.println();
        System.out.println("List of cities (5):");
        for (int i = 0; i < cities.size(); i++) {
            String city = cities.get(i);
            System.out.println("\t- " + city);
        }

        System.out.println();
        // sort: an ArrayList is a mutable List
        List<String> sortedCities = new ArrayList<>(cities);
        Collections.sort(sortedCities);
        System.out.println(sortedCities);

        sortedCities.add("Orange");
        sortedCities.add("Villeneuve-lès-Avignon");
        sortedCities.add(2, "Bayonne");
        sortedCities.addAll(List.of("Narbonne", "Montpellier", "Nîmes"));
        Collections.addAll(sortedCities,
                "Tarbes", "Bordeaux", "Valence", "Lyon",
                "Sarrians", "Séméac", "Soues");
        // NB: default sort is based on unicode codes !
        System.out.println(sortedCities);

        Collections.sort(sortedCities);
        System.out.println(sortedCities);
    }

    @Test
    void demoSortLocale(){
        var locale = Locale.getDefault();
        System.out.println(locale);

        var localeFr = Locale.of("fr", "FR");
        System.out.println(localeFr);

        List<String> wordsFr = new ArrayList<>();
        Collections.addAll(wordsFr,
                "artichaud", "Animal",
                "été", "étuve", "étage",
                "cœur", "cobra", "Corde",
                "garçon", "gare", "garage",
                "zèbre"
        );
        Collections.sort(wordsFr); // not good for language fr
        System.out.println(wordsFr);

        var collatorFr = Collator.getInstance(localeFr);
        Collections.sort(wordsFr, collatorFr);
        System.out.println(wordsFr);

        // Exercise: sort spanish words: "mano", "mañana", "matador", "llamar", "loco"
        var localeES = Locale.of("es","ES");
        System.out.println(localeES);

        List<String> wordsEs = new ArrayList<>();
        Collections.addAll(wordsEs,"mano", "mañana", "matador", "llamar", "loco");
        Collections.sort(wordsEs); // not good for language fr
        System.out.println(wordsEs);

        var collatorEs = Collator.getInstance(localeES);
        Collections.sort(wordsEs,collatorEs);
        System.out.println(wordsEs);
    }

    @Test
    void demoFindLocale(){
        Locale[] locales = Locale.getAvailableLocales();

        // default toString of arrays is ugly ;)
        System.out.println(locales); // Java 1: [Ljava.util.Locale;@31368b99

        // better toString via toolbox Arrays
        System.out.println(Arrays.toString(locales));

        // stream pipeline 1
        System.out.println("All locales");
        Arrays.stream(locales)
                .forEach(System.out::println);

        // stream pipeline 2
        System.out.println();
        System.out.println("Locales using language spanish");
        Arrays.stream(locales)
                .filter(locale -> locale.getLanguage().startsWith("es"))
                .forEach(System.out::println);

        // stream pipeline 3
        System.out.println();
        System.out.println("Locales using language spanish and variant Latn");
        Optional<Locale> optLocaleEs = Arrays.stream(locales)
                .filter(locale -> locale.toString().equals("es_ES_#Latn"))
                .peek(locale -> System.out.println(
                        locale
                        + " with language: " + locale.getLanguage()
                        + " with country: " + locale.getCountry()
                        + " with variant: " + locale.getVariant())
                )
                .findFirst();
        if (optLocaleEs.isPresent()) {
            Locale localeEs = optLocaleEs.get();
            Stream.of("Llamar", "loco")
                    .sorted(Collator.getInstance(localeEs))
                    .forEach(System.out::println);
        }
    }

    @Test
    void demoListSet(){
        List<String> listWords = List.of("artichaud", "Animal",
                "été", "étuve", "étage",
                "cœur", "cobra", "Corde",
                "garçon", "gare", "garage",
                "zèbre");
        Set<String> setWords = new HashSet<>(listWords);
        System.out.println(setWords);
        Collections.addAll(setWords, "cobra", "zoo");
        System.out.println(setWords);

        // TreeSet with default String order
        NavigableSet<String> sortedSetWords = new TreeSet<>(setWords); // String natural order
        System.out.println(sortedSetWords);

        // TreeSet with collator Fr
        NavigableSet<String> sortedSetWordsFr = new TreeSet<>(Collator.getInstance(Locale.of("fr", "FR")));
        sortedSetWordsFr.addAll(sortedSetWords);
        System.out.println(sortedSetWordsFr);
        sortedSetWordsFr.add("xylophone");
        System.out.println(sortedSetWordsFr);
    }

    @Test
    void demoListOfIntegers() {
        List<Integer> numbers = List.of(11, 22, 33, 44);
        Integer element2 = numbers.get(2);
        int element2b = numbers.get(2); // unboxing
        int z = 3 + numbers.get(2); // unboxing
    }
}
