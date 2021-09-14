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
public class FrontendFoodListDto {

    private List<FrontendFoodDto> foodList = new ArrayList<>();

    public void addFood(FrontendFoodDto frontendFoodDto){
        foodList.add(frontendFoodDto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrontendFoodListDto that = (FrontendFoodListDto) o;
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
