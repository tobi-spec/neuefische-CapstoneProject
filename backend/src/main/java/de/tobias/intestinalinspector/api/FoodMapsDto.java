package de.tobias.intestinalinspector.api;

import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodMapsDto {

    private List<FoodMapDto> foodMaps = new ArrayList<>();

    @Override
    public String toString() {
        return "FoodMapsDto{" +
                "foodMaps=" + foodMaps +
                '}';
    }
}
