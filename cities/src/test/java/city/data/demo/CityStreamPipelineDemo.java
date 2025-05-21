package city.data.demo;

import city.converter.CsvConverter;
import city.data.CityFrLbk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.CsvFormatException;
import utils.IOTools;

import java.util.Arrays;
import java.util.Map;
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

}
