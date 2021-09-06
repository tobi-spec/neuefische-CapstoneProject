package de.tobias.intestinalinspector.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "food_table")
@Getter
@Setter
@Builder
public class FoodEntity {

    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;


    @Column(name= "food_name")
    private String foodName;

    @Column(name="date")
    private Date date;

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
