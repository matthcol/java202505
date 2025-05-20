package city.data;

import lombok.*;

// Lombok: fast and simple
// @Data // getter/setter/toString/equals/hashCode

// Lombok: tuning
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString(of={"name", "zipcode"})
public class CityFrLbk {
    private String name;
    private String inseeCode;
    private String zipcode;
    private String departmentNumber;
    private int population;
}
