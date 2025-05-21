package city.data.demo;

import city.converter.CsvConverter;
import city.data.CityFrLbk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.CsvFormatException;
import utils.IOTools;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CityStreamPipelineDemo {

    // initialized by following @BeforeEach method
    Stream<CityFrLbk> streamCity;

    @BeforeEach
    void initStreamFromCsvFile(){
        // headers: first row
        var iterator = IOTools.resourceToStream(getClass(), "/communes_france_2025.csv.gz")
                .iterator();
        if (!iterator.hasNext()) throw new CsvFormatException();
        String headerLine = iterator.next(); // advance and return next element
        String[] headers = headerLine.split(",", 0);

        // data: other rows
        Iterable<String> iterable = () -> iterator;
        var streamLines = StreamSupport.stream(iterable.spliterator(), false);
        streamCity = streamLines
                .map(line -> line.replace(", ", "|"))
                .map(line -> line.split(",", 0))
                .map(cityInfoArray -> CsvConverter.rowToCity(
                        headers,
                        cityInfoArray,
                        Map.of(
                                "name", "nom_standard",
                                "inseeCode", "code_insee",
                                "zipcode", "code_postal",
                                "departmentNumber", "dep_code",
                                "department", "dep_nom",
                                "population", "population"
                        )
                ));
    }

    @Test
    void demoSkipLimit() {
        streamCity.skip(1000)
                .limit(10)
                .forEach(System.out::println);
    }

    // 1. villes qui contiennent un A et un R et les ranger dans une liste (quelconque)
    // 2. villes qui contiennent un A et un R et les ranger dans une liste de type array list
    // 3. villes qui contiennent un A et un R et les ranger dans un ensemble ordonné par population décroissante
    @Test
    void demoCityNameContaining() {
        Pattern pattern = Pattern.compile("(.*r.*a.*|.*a.*r.*)", Pattern.CASE_INSENSITIVE);
        List<CityFrLbk> citiesAandR = streamCity.filter(city ->
                        //city.getName().toLowerCase().contains("a") && city.getName().toLowerCase().contains("r")
                        pattern.matcher(city.getName()).matches()
                )
                .toList(); // Shortcut Java 17
                // .collect(Collectors.toList()); // Shortcut Java 8
        System.out.println("Count: " + citiesAandR.size());
        citiesAandR.stream()
                .limit(10)
                .forEach(System.out::println);
        System.out.println("List class: " + citiesAandR.getClass());
    }

    @Test
    void demoCityNameContainingArrayList() {
        Pattern pattern = Pattern.compile("(.*r.*a.*|.*a.*r.*)", Pattern.CASE_INSENSITIVE);
        List<CityFrLbk> citiesAandR = streamCity.filter(city ->
                        //city.getName().toLowerCase().contains("a") && city.getName().toLowerCase().contains("r")
                        pattern.matcher(city.getName()).matches()
                )
                .collect(Collectors.toCollection(ArrayList::new));
        // .collect(Collectors.toList()); // Shortcut Java 8
        System.out.println("Count: " + citiesAandR.size());
        citiesAandR.stream()
                .limit(10)
                .forEach(System.out::println);
        System.out.println("List class: " + citiesAandR.getClass());
    }

    @Test
    void demoCityNameContainingOrderedSet() {
        Pattern pattern = Pattern.compile("(.*r.*a.*|.*a.*r.*)", Pattern.CASE_INSENSITIVE);
        NavigableSet<CityFrLbk> citiesAandR = streamCity.filter(city ->
                        //city.getName().toLowerCase().contains("a") && city.getName().toLowerCase().contains("r")
                        pattern.matcher(city.getName()).matches()
                )
                .collect(Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(CityFrLbk::getPopulation)
                                .thenComparing(CityFrLbk::getInseeCode)
                )));
        // .collect(Collectors.toList()); // Shortcut Java 8
        System.out.println("Count: " + citiesAandR.size());
        citiesAandR.stream()
                .limit(5)
                .forEach(System.out::println);
        System.out.println("...");
        citiesAandR.stream()
                .skip(citiesAandR.size() - 5)
                .limit(5)
                .forEach(System.out::println);
        System.out.println();
        System.out.println("List class: " + citiesAandR.getClass());
    }


}
