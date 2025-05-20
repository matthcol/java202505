package city.data.demo;


import city.data.CityFr;
import city.data.CityFrLbk;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CityFrLbkDemo {

    @Test
    void demoBuild() {
        var city1 = new CityFrLbk();
        city1.setName("Avignon");
        city1.setZipcode("84000");

//        var city2 = new CityFrLbk("Toulouse");
//        var city3 = new CityFrLbk("Pau", 64000);
//        var city4 = new CityFrLbk("Marseille", "13055", "13000", "13", 877_215);

        var city2 = CityFrLbk.builder()
                .name("Toulouse")
                .build();
        var city3 = CityFrLbk.builder()
                .name("Pau")
                .zipcode("64000")
                .build();

        // var city4 = new CityFrLbk("Marseille", "13055", "13000", "13", 877_215);

        Stream.of(city1, city2, city3)
                .forEach(city -> {
                    System.out.println(city);
                    System.out.println("\t-name: " + city.getName());
                    System.out.println();
                });
    }

}