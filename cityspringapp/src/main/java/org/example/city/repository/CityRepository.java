package org.example.city.repository;


import org.example.city.entity.CityFr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<CityFr, Integer> {

    // Custom Queries

    // Spring Data writes automatically queries with following vocabulary
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    // SQL:
    //    select cf1_0.id,cf1_0.dep_code,cf1_0.code_insee,cf1_0.nom_standard,cf1_0.population,cf1_0.code_postal
    //    from city cf1_0
    //    where cf1_0.dep_code=?
    //    order by cf1_0.population desc
    List<CityFr> findByDepartmentNumberOrderByPopulationDesc(String departmentNumber);

    // SQL written in JPQL => native SQL:
    //      select cf1_0.id,cf1_0.dep_code,cf1_0.code_insee,cf1_0.nom_standard,cf1_0.population,cf1_0.code_postal
    //      from city cf1_0
    //      where cf1_0.dep_code=?
    //      order by cf1_0.population desc
    @Query("""
            SELECT c
            FROM CityFr c
            WHERE c.departmentNumber = :departmentNumber
            ORDER BY c.population DESC
            """)
    List<CityFr> findByDepartment(String departmentNumber);
}
