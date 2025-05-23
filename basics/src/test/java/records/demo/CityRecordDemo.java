package records.demo;

import org.junit.jupiter.api.Test;
import records.CityRecord;

import static org.junit.jupiter.api.Assertions.*;

class CityRecordDemo {

    @Test
    void demoRecord(){
        var city = new CityRecord("Orange", 35_000);
        System.out.println(city);
        System.out.println(city.name());
    }

}