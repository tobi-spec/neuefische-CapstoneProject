package de.tobias.intestinalinspector.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodListDto {

    private List<FoodDto> foodList = new ArrayList<>();

    public void addFood(FoodDto frontendFoodDto){
        foodList.add(frontendFoodDto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodListDto that = (FoodListDto) o;
        return Objects.equals(foodList, that.foodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodList);
    }

    @Override
    public String toString() {
        return "FrontendFoodListDto{" +
                "foodList=" + foodList +
                '}';
    }

}
