package city.converter;

import city.data.CityFrLbk;

import java.util.Arrays;
import java.util.Map;

public class CsvConverter {

    public static CityFrLbk rowToCity(
            String[] headers,
            String[] row,
            Map<String, String> infoColumnMap
    ) {
        String nameColumn = infoColumnMap.get("name"); // => "nom_standard" in this project
        int nameIndex = Arrays.asList(headers).indexOf(nameColumn);
        String name = row[nameIndex];

        String inseeCodeColumn = infoColumnMap.get("inseeCode"); // => "code_insee" in this project
        int inseeCodeIndex = Arrays.asList(headers).indexOf(inseeCodeColumn);
        String inseeCode = row[nameIndex];

        String zipcodeColumn = infoColumnMap.get("zipcode");
        int zipcodeIndex = Arrays.asList(headers).indexOf(zipcodeColumn);
        String zipcode = row[zipcodeIndex];

        String departmentNumberColumn = infoColumnMap.get("departmentNumber");
        int departmentNumberIndex = Arrays.asList(headers).indexOf(departmentNumberColumn);
        String departmentNumber = row[departmentNumberIndex];

        String departmentColumn = infoColumnMap.get("department");
        int departmentIndex = Arrays.asList(headers).indexOf(departmentColumn);
        String department = row[departmentIndex];

        String populationColumn = infoColumnMap.get("population");
        int populationIndex = Arrays.asList(headers).indexOf(populationColumn);
        int population = Integer.parseInt(row[populationIndex]);

        return CityFrLbk.builder()
                .name(name)
                .inseeCode(inseeCode)
                .zipcode(zipcode)
                .departmentNumber(departmentNumber)
                .department(department)
                .population(population)
                .build();
    }


}
