package org.example.city.entity;

// ORM: Object Relational Mapper => JPA-Hibernate
// class CityFr  <-> table city
//   attribute name  <->  column nom_standard

// JPA: package jakarta.persistence

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// lombok
@Getter
@Setter
@ToString(of={"id", "name", "inseeCode"})
// JPA
@Entity
@Table(name = "city")
public class CityFr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="nom_standard", nullable = false)
    private String name;

    @Column(name="code_insee", nullable = false)
    private String inseeCode;

    @Column(name="code_postal")
    private String zipcode;

    private Integer population;
}
