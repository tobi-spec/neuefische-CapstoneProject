package de.tobias.intestinalinspector.api;


import lombok.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDto {

    private String foodName;
    private long id;
    private String date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodDto foodDto = (FoodDto) o;
        return Objects.equals(foodName, foodDto.foodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodName);
    }

    @Override
    public String toString() {
        return "FrontendFoodDto{" +
                "foodName='" + foodName + '\'' +
                ", id=" + id +
                ", date=" + date +
                '}';
    }
}