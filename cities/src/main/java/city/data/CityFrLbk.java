package city.data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

// Bean Validation API
// https://jakarta.ee/specifications/bean-validation/3.1/apidocs/

// Lombok: fast and simple
// @Data // getter/setter/toString/equals/hashCode

// Lombok: tuning
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder // uses @AllArgsConstructor
@Getter
@Setter
@ToString(of={"name", "zipcode", "inseeCode", "population"})
public class CityFrLbk {
    @NotNull(message = "{cityfrblk.name.notnull}")
    private String name;

    @NotNull
    private String inseeCode;

    //@NotNull
    @Size(min=5, max=5, message = "{cityfrblk.zipcode.size}")
    private String zipcode;

    private String departmentNumber;

    private String department;

    private int population;
}
