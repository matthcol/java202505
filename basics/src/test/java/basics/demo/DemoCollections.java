package basics.demo;

import org.junit.jupiter.api.Test;

import java.util.List;

public class DemoCollections {

    @Test
    void demoList() {
        List<String> cities = List.of("Toulouse", "Pau", "Avignon"); // Java 11
        System.out.println(cities);
    }
}
