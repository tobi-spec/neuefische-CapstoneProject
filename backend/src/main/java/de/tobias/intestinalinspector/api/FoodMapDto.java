package de.tobias.intestinalinspector.api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodMapDto implements Comparable<FoodMapDto>{

    private String date;
    private List<FoodDto> foods = new ArrayList<>();

    @Override
    public int compareTo(FoodMapDto foodMapDto){
        if(getDate() == null || foodMapDto.getDate() == null){
            return 0;
        }
        return getDate().compareTo(foodMapDto.getDate());
    }

    @Override
    public String toString() {
        return "FoodMapDto{" +
                "date='" + date + '\'' +
                ", foods=" + foods +
                '}';
    }
}
