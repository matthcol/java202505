package city.data.demo;

import city.converter.CsvConverter;
import city.data.CityFrLbk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import utils.CsvFormatException;
import utils.IOTools;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        System.out.println("Count: " + citiesAandR.size());
        citiesAandR.stream()
                .limit(10)
                .forEach(System.out::println);
        System.out.println("List class: " + citiesAandR.getClass());
    }

    @Test
    void demoCityNameContainingOrderedSet() {
        Pattern pattern = Pattern.compile(".*r.*a.*|.*a.*r.*", Pattern.CASE_INSENSITIVE);
        NavigableSet<CityFrLbk> citiesAandR = streamCity.filter(city ->
                        //city.getName().toLowerCase().contains("a") && city.getName().toLowerCase().contains("r")
                        pattern.matcher(city.getName()).matches()
                )
                .collect(Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(CityFrLbk::getPopulation)
                                .thenComparing(CityFrLbk::getInseeCode)
                )));
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

    @Test
    void demoWhichCitiesShareSameInseeCode() {
        List<CityFrLbk> cities = streamCity.toList();
        List<CityFrLbk> sameCities = new ArrayList<>();
        for (int i=0; i < cities.size(); i++) {
            for (int j=i+1; j < cities.size(); j++) {
                if (cities.get(i).getInseeCode().equals(cities.get(j).getInseeCode())) {
                    sameCities.add(cities.get(i));
                    sameCities.add(cities.get(j));
                }
            }
        }
        System.out.println(sameCities);
    }

    @Test
    void demoAnyAllDepartmentNumbers() {
        // departementNumber: are all numbers composed of only digits
        boolean ok = streamCity.map(CityFrLbk::getDepartmentNumber)
                .allMatch(departmentNumber -> departmentNumber.matches("[0-9]+"));
        System.out.println("All department numbers are composed of digits only: " + ok);
    }

    @Test
    void demoDistinctDepartmentNumber() {
        // which departement numbers contains letters
        NavigableSet<String> departments = streamCity.map(CityFrLbk::getDepartmentNumber)
                .filter(DepartmentNumber -> !DepartmentNumber.matches("[0-9]+"))
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(departments);
    }

    @Test
    void demoInseeCode() {
        boolean ok = streamCity.map(CityFrLbk::getInseeCode)
                .anyMatch(inseeCode -> !inseeCode.matches("[0-9]+"));
        System.out.println("Any INSEE code contains a non-digit character: "+ ok);
    }

    @Test
    void demoZipCode() {
        boolean ok = streamCity.map(CityFrLbk::getZipcode)
                // .filter(zipcode -> Objects.nonNull(zipcode)) // simplified with following lambda
                .filter(Objects::nonNull)
                .anyMatch(zipcode -> !zipcode.matches("[0-9]+"));
        System.out.println("Any zipcode contains a non-digit character: "+ ok);
    }

    @Test
    void demoZipcode() {
        streamCity.filter(city -> Objects.isNull(city.getZipcode()))
                .forEach(System.out::println);
        // 3 cities with no zipcode
    }

    // Exercise: count cities of department 30
    @ParameterizedTest
    @NullSource
    @ValueSource(strings={"30", "84"})
    void demoCountCities(String departmentNumber) {
        long cityCount = streamCity
                .filter(dep -> dep.getDepartmentNumber().equals(departmentNumber))
                .count();
        System.out.println(cityCount);
    }

    // total population of department 30
    @ParameterizedTest
    @ValueSource(strings={"30", "84"})
    void demoPopulationTotal(String departmentNumber) {
        int populationTotal = streamCity.filter(dep -> dep.getDepartmentNumber().equals(departmentNumber))
                .mapToInt(CityFrLbk::getPopulation)
                .sum();
        System.out.println(populationTotal);
    }

    // min, max, average, total population of department + number of cities of this department

    // Top 5 populations (integers only)

    // names of cities from department 30 separated with a comma
    // Ex: Nîmes, Les Angles


    // number of cities by department

    // list of cities by department

    // partition cities with a population threshold
    // Ex: population >= 100K, population < 100K
}
