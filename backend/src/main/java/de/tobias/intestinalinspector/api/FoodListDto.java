package de.tobias.intestinalinspector.api;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
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
