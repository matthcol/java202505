package city.data;

import jakarta.validation.constraints.NotNull;
import lombok.*;

// Lombok: fast and simple
// @Data // getter/setter/toString/equals/hashCode

// Lombok: tuning
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder // uses @AllArgsConstructor
@Getter
@Setter
@ToString(of={"name", "zipcode", "inseeCode"})
public class CityFrLbk {
    @NotNull
    private String name;

    private String inseeCode;

    @NotNull
    private String zipcode;

    private String departmentNumber;

    private String department;

    private int population;
}
