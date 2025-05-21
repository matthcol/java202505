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
        String nameColumn = infoColumnMap.get("name"); // => "nom_standard"
        int nameIndex = Arrays.asList(headers).indexOf(nameColumn);
        String name = row[nameIndex];

//        String inseeCodeColumn,
//        String zipCodeColumn,
//        String departmentNumberColumn,
//        String departmentColumn,
//        String populationColumn
        return CityFrLbk.builder()
                .name(name)
                .build();
    }


}
