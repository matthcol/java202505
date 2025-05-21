package city.data.demo;

import city.data.CityFrLbk;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;


class CityFrLbkDemo {

    @Test
    void demoBuild() {
        var city0 = new CityFrLbk();

        var city1 = new CityFrLbk();
        city1.setName("Avignon");
        city1.setZipcode("84000");

        // NB: avoid calling specific constructor => improve maintainability
        //  var city2 = new CityFrLbk("Toulouse");
        //  var city3 = new CityFrLbk("Pau", 64000);
        //  var city4 = new CityFrLbk("Marseille", "13055", "13000", "13", 877_215);

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

        var city5 = CityFrLbk.builder()
                .name("City with wrong zipcode")
                .zipcode("6400")
                .build();

        Stream.of(city0, city1, city2, city3, city4, city5)
                .forEach(city -> {
                    System.out.println(city);
                    System.out.println("\t-name: " + city.getName());
                    System.out.println();
                });

        // Validator setup (auto in spring(boot) applications)
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        System.out.println("Cities not valid:");
        Stream.of(city0, city1, city2, city3, city4, city5)
                .map(city -> validator.validate(city))
                .filter(constraintViolationSet -> !constraintViolationSet.isEmpty())
                .forEach(constraintViolationSet -> constraintViolationSet.forEach(constraintViolation -> {
                    System.out.println("\t- constraint: " + constraintViolation);
                    System.out.println("\t\t* object: " + constraintViolation.getRootBean());
                    System.out.println("\t\t* attribute: " + constraintViolation.getPropertyPath());
                    System.out.println("\t\t* invalid value: " + constraintViolation.getInvalidValue());
                    System.out.println("\t\t* message: " + constraintViolation.getMessage());
                }));
    }

    @Test
    void demoRuntimeAnnotations() {
        var city = CityFrLbk.builder()
                .name("Marseille")
                .zipcode("13000")
                .inseeCode("13055")
                .departmentNumber("13")
                .population(877_215)
                .build();

        Class<?> objectClass = city.getClass();
        System.out.println("Class: " + objectClass);
        // Annotations of the class
        Annotation[] annotations = objectClass.getAnnotations();
        System.out.println("Annotations: " +  Arrays.toString(annotations)); // Lombok annotations have disappeared
        Field[] fields = objectClass.getDeclaredFields();
        System.out.println("Fields: "); // + Arrays.toString(fields));
        Arrays.stream(fields)
                .forEach(field -> {
                    System.out.println("Nom du paramètre : "
                            + field.getName()
                            + ", ses Annotations : "
                            + Arrays.toString(field.getAnnotations()));
                });

    }

}

















