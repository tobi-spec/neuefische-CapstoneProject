package de.tobias.intestinalinspector.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FrontendFoodListDto {

    List<FrontendFoodDto> foodList = new ArrayList<>();

    public void addFood(FrontendFoodDto frontendFoodDto){
        foodList.add(frontendFoodDto);
    }
}
