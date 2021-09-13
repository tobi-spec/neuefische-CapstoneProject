package de.tobias.intestinalinspector.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "food_table")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodEntity {

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false, unique = true)
    private long id;

    @Column(name="date", nullable = false)
    private String date;

    @Column(name= "food_name", nullable = false)
    private String foodName;

    @Column(name="user_name", nullable = false)
    private String userName;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodEntity that = (FoodEntity) o;
        return id == that.id && Objects.equals(foodName, that.foodName) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foodName, date);
    }

    @Override
    public String toString() {
        return "FoodEntity{" +
                "id=" + id +
                ", foodName='" + foodName + '\'' +
                ", date=" + date +
                '}';
    }
}
