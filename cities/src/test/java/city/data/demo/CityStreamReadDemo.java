package city.data.demo;

import city.converter.CsvConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.CsvFormatException;
import utils.IOTools;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.GZIPInputStream;

public class CityStreamReadDemo {

    // Streams Java
    // - I/O : files, network (Java 1.0) : packages java.io, java.nio
    //      * class InputStream, OutputStream
    // - functional programing map/reduce (Java 8):
    //      * packages java.util.stream, java.util.function
    //      * interface Stream
    //          - methods: map, filter, forEach, peek, ...
    //      * lambda function

    @Test
    void demoReadData() throws URISyntaxException, IOException {
        String resource = "/communes_france_2025.csv.gz";
        URI uri = this.getClass() // current class: CityStreamDemo
                .getResource(resource) // check if resource exists
                .toURI();
        Path path = Paths.get(uri);
        System.out.println("Path: " + path);
        System.out.println("Exists " + Files.exists(path));

        // Java 7: try-with-resources
        try (
            // declared resources here:

            // 1 - binary stream
            InputStream inputStream = Files.newInputStream(path);

            // NB: binary read:
            //  byte[] buffer = new byte[1024];
            //  inputStream.read(buffer);
            //  System.out.println(Arrays.toString(buffer));

            // 2 - decompression
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);

            // 3 - read as text with encoding
            Reader reader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);

            // NB: character read
            //        char[] buffer = new char[255];
            //        reader.read(buffer);
            //        System.out.println(Arrays.toString(buffer));

            // 4 - read line by line
            BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            bufferedReader.lines()
                    .limit(10)
                    // handle data
                    .forEach(System.out::println);

        } // auto-close of 4 resources even if any exception happens

        // here catch exceptions or let them propagate

    }

    @Test
    void demoReadDataTools() {
        Stream<String> streamLines = IOTools.resourceToStream(getClass(), "/communes_france_2025.csv.gz");
        streamLines.limit(10)
                .forEach(System.out::println);
    }

    @Test
    void demoReadDataParseCsv() {
        // headers
        String headers = IOTools.resourceToStream(getClass(), "/communes_france_2025.csv.gz")
                .findFirst()
                .orElseThrow(CsvFormatException::new);
        System.out.println(headers);

        // data
        System.out.println();
        IOTools.resourceToStream(getClass(), "/communes_france_2025.csv.gz")
                .skip(1)
                .limit(10)
                .forEach(System.out::println);
        // TODO: parse each line as CSV line
    }

    @Test
    void testCsvFormatException() {
        CsvFormatException ex = Assertions.assertThrows(
                CsvFormatException.class,
                () -> Stream.empty()
                    .findFirst()
                    .orElseThrow(CsvFormatException::new)
        );
        Assertions.assertEquals("the input is not conform to CSV standard", ex.getMessage());
        // System.out.println(ex);
    }

    @Test
    void demoReadDataParseCsv2() {
        // method mixing iterator and stream mechanisms
        var iterator =IOTools.resourceToStream(getClass(), "/communes_france_2025.csv.gz")
                .iterator();
        if (!iterator.hasNext()) throw new CsvFormatException();
        String headerLine = iterator.next(); // advance and return next element
        String[] headers = headerLine.split(",", 0);
        System.out.println(Arrays.toString(headers));
        System.out.println();

        Iterable<String> iterable = () -> iterator;
        var streamLines = StreamSupport.stream(iterable.spliterator(), false);
        streamLines.limit(10)
                .map(line -> line.replace(", ", "|"))
                .map(line -> line.split(",", 0))
                //.filter(cityInfoArray -> cityInfoArray.length == headers.length)
                // .peek(cityInfoArray -> System.out.println(Arrays.toString(cityInfoArray)))
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
                ))
                .forEach(System.out::println);
    }

}
