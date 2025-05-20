package city.data.demo;

import city.data.CityFrLbk;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CityFrLbkDemo {

    @Test
    void demoBuild() {
        var city0 = new CityFrLbk();

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
        var city4 = CityFrLbk.builder()
                .name("Marseille")
                .zipcode("13000")
                .inseeCode("13055")
                .departmentNumber("13")
                .population(877_215)
                .build();

        Stream.of(city0, city1, city2, city3, city4)
                .forEach(city -> {
                    System.out.println(city);
                    System.out.println("\t-name: " + city.getName());
                    System.out.println();
                });

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        System.out.println("Cities not valid:");
        Stream.of(city0, city1, city2, city3, city4)
                .filter(city -> !validator.validate(city).isEmpty())
                .forEach(System.out::println);

    }

}