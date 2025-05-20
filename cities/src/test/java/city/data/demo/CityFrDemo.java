package city.data.demo;

import city.data.CityFr;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityFrDemo {

    @Test
    void demoBuildCity() {
        var city = new CityFr();
        System.out.println(city);

        // setter and getter
        city.setName("Avignon");
        System.out.println(city.getName());
        System.out.println(city);

        city.setZipcode("84000");
        System.out.println(city);
    }

    @Test
    void demoBuildCity2() {
        var city = new CityFr("Avignon", "84000");
        System.out.println(city);
    }

}