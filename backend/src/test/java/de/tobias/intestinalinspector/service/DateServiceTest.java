package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.SpringBootTests;
import de.tobias.intestinalinspector.api.FoodDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DateServiceTest extends SpringBootTests {

    @Autowired
    DateService dateService;


    @Test
    public void testSortFood(){
        //GIVEN
        FoodDto food1 = FoodDto.builder()
                .id(1)
                .foodName("Testtraube")
                .date("2021/09/23")
                .build();
        FoodDto food2 = FoodDto.builder()
                .id(1)
                .foodName("Platzhalterplätzchen")
                .date("2021/09/22")
                .build();
        List<FoodDto> list = new ArrayList<>();
        list.add(food1);
        list.add(food2);
        //WHEN
        Map<String, List<FoodDto>> actual = dateService.sortFoodByDay(list);
        //THEN
        Map<String, List<FoodDto>> expected = new HashMap<>();
        expected.put("2021/09/23", List.of(food1));
        expected.put("2021/09/22", List.of(food2));


        assertEquals(actual, expected);
    }

}