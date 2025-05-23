package org.example.city.repository;


import org.example.city.entity.CityFr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<CityFr, Integer> {

    // Custom Queries
}
