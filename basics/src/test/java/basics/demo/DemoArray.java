package basics.demo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class DemoArray {

    @Test
    void demoStaticArray(){
        int[] values = {11, 22, 33};
        System.out.println(values);
        System.out.println(Arrays.toString(values));
        System.out.println(values.length);

        values[0] = 44;
        System.out.println(Arrays.toString(values));

        // dynamic array
        int[] values2 = new int[values.length + 1];
        int i;
        for (i = 0; i < values.length; i++) {
            values2[i] = values[i];
        }
        values2[i] = 55;
        System.out.println(Arrays.toString(values2));
    }
}
