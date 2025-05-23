package enums.demo;

import enums.TrafficLightEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TrafficLightEnumDemo {

    @Test
    void demoEnum(){
        TrafficLightEnum trafficLight = TrafficLightEnum.RED;
        System.out.println(trafficLight); // RED
        System.out.println(trafficLight.ordinal()); // RED

        System.out.println();
        for (TrafficLightEnum tf: TrafficLightEnum.values()) {
            System.out.println(tf);
        }
    }

    @ParameterizedTest
    @ValueSource(strings={
            "RED", "GREEN", "ORANGE"
    })
    void demoParse(String trafficLightString){
        TrafficLightEnum trafficLight = TrafficLightEnum.valueOf(trafficLightString);
        System.out.println(trafficLight);
    }

    @ParameterizedTest
    @NullSource
    @EnumSource(TrafficLightEnum.class)
    void demoSwitchCase(TrafficLightEnum trafficLight){
        System.out.println("Traffic light: " + trafficLight);
        switch (trafficLight) {
            case GREEN -> System.out.println("I'm Hulk");
            case RED -> System.out.println("I'm red Hulk");
            case ORANGE -> System.out.println("I'm the Thing");
            case null, default -> System.out.println("no traffic light");
        }

        var message = switch (trafficLight) {
            case GREEN -> "I'm Hulk";
            case RED -> "I'm red Hulk";
            case ORANGE -> "I'm the Thing";
            case null, default -> "no traffic light";
        };
        System.out.println(message);
    }
}