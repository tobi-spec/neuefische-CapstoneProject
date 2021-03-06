package de.tobias.intestinalinspector.api.food;


import de.tobias.intestinalinspector.api.BasicDtoInterface;
import lombok.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDto implements BasicDtoInterface {

    private String foodName;
    private long id;
    private String date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodDto foodDto = (FoodDto) o;
        return id == foodDto.id && Objects.equals(foodName, foodDto.foodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodName, id);
    }

    @Override
    public String toString() {
        return "FoodDto{" +
                "foodName='" + foodName + '\'' +
                ", id=" + id +
                ", date=" + date +
                '}';
    }
}
