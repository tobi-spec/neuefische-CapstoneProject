package de.tobias.intestinalinspector.api;

import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodMapDto {

    private Map<String,List<FoodDto>> foodMap = new HashMap<>();

    public void putAll(Map<String,List<FoodDto>> map){
        foodMap.putAll(map);
    }

    @Override
    public String toString() {
        return "FoodListDto{" +
                "foodMap=" + foodMap +
                '}';
    }
}
