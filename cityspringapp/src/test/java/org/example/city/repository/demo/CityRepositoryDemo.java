package org.example.city.repository.demo;

import org.example.city.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.MessageFormat;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE) // do not replace db from main app
class CityRepositoryDemo {

    @Autowired
    CityRepository cityRepository;

    @Test
    void demoFindById() {
        int id = 1;
        cityRepository.findById(id) // Optional<CityFr>
                // SQL: select cf1_0.id,cf1_0.code_insee,cf1_0.nom_standard,cf1_0.population,cf1_0.code_postal from city cf1_0 where cf1_0.id=?
                .ifPresentOrElse(
                        city -> System.out.println(MessageFormat.format(
                                "City found with id {0}: {1}",
                                id,
                                city
                        )),
                        () -> System.out.println(MessageFormat.format(
                                "No city found with id {0}",
                                id
                        ))
                );
    }

    @ParameterizedTest
    @ValueSource(strings={"30", "84", "64", "666"})
    void demoFindByDepartment(String departmentNumber) {
        cityRepository.findByDepartmentNumberOrderByPopulationDesc(departmentNumber)
                .stream()
                .limit(10)
                .forEach(System.out::println);
    }

    @ParameterizedTest
    @ValueSource(strings={"30", "84", "64", "666"})
    void demoFindByDepartmentJPQL(String departmentNumber) {
        cityRepository.findByDepartment(departmentNumber)
                .stream()
                .limit(10)
                .forEach(System.out::println);
    }

}