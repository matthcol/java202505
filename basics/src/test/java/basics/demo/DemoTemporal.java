package basics.demo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.stream.Stream;

public class DemoTemporal {

    @Test
    void demoCalendar() {
        Calendar now = GregorianCalendar.getInstance();
        System.out.println(now);
        System.out.println("Tz: " + now.getTimeZone());
        System.out.println("Year: " + now.get(Calendar.YEAR));
        System.out.println("Month: " + now.get(Calendar.MONTH)); // start from 0  :(
    }

    @Test
    void demoModernTypes() {
        DateTimeFormatter formatDateTimeFr = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        // since Java 8: ISO-8061
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ldt1 = LocalDateTime.of(2024, 2, 29, 14, 0, 30);
        LocalDateTime ldt2 = LocalDateTime.parse("2400-02-29T12:30:10");
        LocalDateTime ldt3 = LocalDateTime.parse("29/02/2028 02:33:11", formatDateTimeFr);

        Stream.of(now, ldt1, ldt2, ldt3)
                .forEach(ldt -> {
                    System.out.println(ldt); // format ISO
                    System.out.println(ldt.format(formatDateTimeFr));
                    System.out.println("Year: " + ldt.getYear());
                    System.out.println("Month: " + ldt.getMonthValue()); // range 1-12 :)
                    System.out.println("Day: " + ldt.getDayOfMonth());
                    // ...
                    System.out.println("Second: " + ldt.getSecond());
                    System.out.println();
                });

        var sortedDateTimes = Stream.of(now, ldt1, ldt2, ldt3)
                .sorted()
                .toList();
        System.out.println(sortedDateTimes);
        System.out.println();

        System.out.println("Comparison: "  + now.compareTo(ldt1)); // result 1 > 0 : first > second
        System.out.println("Comparison: "  + now.compareTo(ldt2)); // result -375 : first < second
        System.out.println("Comparison: "  + now.compareTo(now)); // result  0 : equals
        System.out.println();

        // External comparison: Comparator
        Stream.of(now, ldt1, ldt2, ldt3)
                // .sorted((t1, t2) -> t1.getSecond() - t2.getSecond())
                .sorted(Comparator.comparing(LocalDateTime::getSecond))
                .forEach(System.out::println);
        System.out.println();

        // comparator general
        Comparator<Object> cmp = Comparator.comparing(Object::hashCode);
        Stream.of(now, ldt1, ldt2, ldt3)
                .sorted(cmp)
                .forEach(System.out::println);
        System.out.println();

        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);
        System.out.println();

        ZonedDateTime nowHere = ZonedDateTime.now();
        ZonedDateTime nowJapan = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        Stream.of(nowHere, nowJapan)
                .forEach(System.out::println);
    }
}
