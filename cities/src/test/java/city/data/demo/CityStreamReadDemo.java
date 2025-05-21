package city.data.demo;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

}
