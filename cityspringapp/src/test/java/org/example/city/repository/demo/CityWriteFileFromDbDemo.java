package org.example.city.repository.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import org.example.city.entity.CityFr;
import org.example.city.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class CityWriteFileFromDbDemo {

    @Autowired
    CityRepository cityRepository; // layer Spring repository (wrap Hibernate)

    @Autowired
    EntityManager entityManager; // layer Hibernate

    @Test
    void demoWriteTextFile() throws IOException {
        // READ input
        String departmentNumber = "30";
        List<CityFr> cityList = cityRepository.findByDepartment(departmentNumber);
        // WRITE output
        Path pathOut = Path.of("output/cities.csv");
        Path directoryOut = pathOut.getParent();
        Files.createDirectories(directoryOut); // equivalent 'mkdir -p'
        try (
                // Binary Stream (no need here)
                // OutputStream out = Files.newOutputStream(pathOut);

                // Text stream
                Writer writer = new FileWriter(pathOut.toFile(), StandardCharsets.UTF_8);
                BufferedWriter bufferedWriter = new BufferedWriter((writer));
        ) {
            for (CityFr cityFr : cityList) {
                String csvLine = Stream.of(
                        cityFr.getName(),
                        cityFr.getInseeCode(),
                        cityFr.getZipcode(),
                        cityFr.getDepartmentNumber(),
                        "" + cityFr.getPopulation()
                ).collect(Collectors.joining(","));
                // or String.join
                bufferedWriter.write(csvLine);
                bufferedWriter.newLine(); // adapt char sequence to OS: LF, CRLF, LFCR
            }
        } // close resources
    }


    @Test
    void demoWriteCsvDynamicSQL() throws IOException {
        String sql = """
                SELECT
                    c.departmentNumber as departmentNumber,
                    count(*) as cityCount,
                    sum(c.population) as population
                FROM CityFr c
                GROUP BY c.departmentNumber
                ORDER BY departmentNumber
                """;

        // Execute query and store each row in a JPA Tuple object
        var query = entityManager.createQuery(sql, Tuple.class);
        //  query.getResultStream()
        //       .limit(5)
        //       .forEach(System.out::println);
        List<Tuple> results = query.getResultList();

        // Headers from first row
        Tuple firstRow = results.getFirst();
        List<String> headers = firstRow.getElements()
                .stream()
                .map(TupleElement::getAlias)
                .toList();

        Path pathOut = Path.of("output/result.csv");
        Path directoryOut = pathOut.getParent();
        Files.createDirectories(directoryOut); // equivalent 'mkdir -p'
        try (
                // Text stream
                Writer writer = new FileWriter(pathOut.toFile(), StandardCharsets.UTF_8);
                BufferedWriter bufferedWriter = new BufferedWriter((writer));
        ) {

                // TODO: write headers

                // TODO: loop write rows

        } // close resources
    }

}
