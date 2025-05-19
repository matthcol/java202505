package basics.demo;

import lombok.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class DemoString {


    @ParameterizedTest
    @ValueSource(strings = {
            "Toulouse",
            "déjà",
            "exécuter",
            "L'Haÿ-les-Roses",
            "Москва",
            "東京",
            "الدَّارُ ٱلْبَيْضَاء",
            "❤🐪🦐\uD83D\uDEDD" // length and substring have weird results
    })
    void demoString(String word) {
        char c = 'T';
        System.out.println("Word: " + word);
        System.out.println("Word length: " + word.length());
        System.out.println("Uppercase: " + word.toUpperCase());

        if (word.length() >= 5) {
            System.out.println("Substring 4: " + word.substring(4));
            System.out.println("Substring 4-6: " + word.substring(4, 6));
        } else {
            System.out.println("Substring: word is to short");
        }

        System.out.println("First character: " + word.charAt(0));
        // System.out.println("Last character: " + word.charAt(word.length())); // java.lang.StringIndexOutOfBoundsException
        System.out.println("Last character: " + word.charAt(word.length() - 1));
    }

    @Test
    void testEmojiString() {
        // Unicode characters with long code need 2 Java characters (2x16 bits)
        String word = "❤🐪🦐\uD83D\uDEDD";
        Assertions.assertEquals(7, word.length());
        Assertions.assertEquals('❤', word.charAt(0));
        Assertions.assertEquals("🐪", word.substring(1,3));
        Assertions.assertEquals("🦐", word.substring(3,5));
        Assertions.assertEquals("🛝", word.substring(5));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "Pau",
            "Toulouse",
            "Zèbre",
            "Zèbre dans une phrase de plus de 10 caractères",
            "to0123456789012345",
            "012345678901234567",
    })
    void demoConditionalMultiBranch(String word) {
        // 1st conditional structure
        if (word.isEmpty()) System.out.println("Word is Empty");
        else if (word.length() < 5) System.out.println("Word length is < 5");
        else if ((word.length() < 10) || word.toLowerCase().startsWith("z")) {
            System.out.println("Word length is in range [5-10[ ou begins with 'z'");
        } else if ((word.length() < 20) && word.toLowerCase().startsWith("to")) {
            System.out.println("Word length is in range [10-20[ and begins with 'to'");
            System.out.println("Substring [4-5]: " + word.substring(4, 6));
        } else System.out.println("Autre cas");

        // 2nd conditional structure
        if (!word.isEmpty()){
            System.out.println("Word is not empty");
        }
    }

    @ParameterizedTest
    @ValueSource(strings={
            "",
            "a",
            "ab",
            "abc",
            "abcd",
            "abcde",
    })
    void demoSwitchCaseClassic(String word) {
        // 8 types primitifs + enum (Java 5) + String (Java 7)
        switch (word.length()){
            case 0:
                System.out.println("Word is empty");
                break;
            case 1:
            case 2:
                System.out.println("Word length is in range [1-2]");
                break;
            case 3:
                System.out.println("Word length is 3");
                break;
            default:
                System.out.println("Word length is > 3");
        }
    }

    @ParameterizedTest
    @ValueSource(strings={
            "",
            "a",
            "ab",
            "abc",
            "abcd",
            "abcde",
    })
    void demoSwitchCasePatternMatching(String word) {
        // https://docs.oracle.com/en/java/javase/21/language/pattern-matching-switch.html
        // since Java 21 (12-20: previews)
        // works with primitive types, String, enum and any objects
        switch (word.length()){
            case 0 -> System.out.println("Word is empty");
            case 1, 2 ->
                System.out.println("Word length is in range [1-2]");
            case 3 -> System.out.println("Word length is 3");
            default -> System.out.println("Word length is > 3");
        }
    }

    @ParameterizedTest
    @ValueSource(strings={
            "",
            "a",
            "ab",
            "abc",
            "abcd",
            "abcde",
    })
    void demoSwitchCasePatternMatchingValue(String word) {
        // Java 21 : pattern matching returning a value
        // switch-case expression => default value is mandatory or all cases must be covered
        String description = switch (word.length()){
            case 0 -> "Word is empty";
            case 1, 2 ->
                    "Word length is in range [1-2]";
            case 3 -> "Word length is 3";
            default -> "Word length is > 3";
        };
        System.out.println("Description: " + description);
    }
}
