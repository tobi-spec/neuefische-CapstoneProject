package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.SpringBootTests;
import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.repository.FoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;



class FoodServiceTest extends SpringBootTests {

    @Autowired
    FoodService foodService;

    @Test
    public void testAddName(){
        //GIVEN
        FoodEntity foodToAdd = FoodEntity.builder()
                .foodName("Prüfpanacotta")
                .build();
        //WHEN
        FoodEntity addedFood = foodService.add(foodToAdd);
        //THEN
        assertEquals("Prüfpanacotta", addedFood.getFoodName());
    }

    @Test
    public void testAddDate(){
        //GIVEN
        FoodEntity foodToAdd = FoodEntity.builder()
                .foodName("Platzhalterplätzchen")
                .build();
        //WHEN
        FoodEntity addedFood = foodService.add(foodToAdd);
        //THEN
        assertThat(addedFood.getDate(), is(not("")));
    }

}