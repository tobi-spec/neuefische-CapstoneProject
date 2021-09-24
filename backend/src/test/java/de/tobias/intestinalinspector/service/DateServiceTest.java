package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.api.FoodDto;
import de.tobias.intestinalinspector.api.PainDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DateServiceTest {

    DateService dateService = new DateService();


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

    @Test
    public void testSortPain(){
        //GIVEN
        PainDto pain1 = PainDto.builder()
                .id(1)
                .painLevel(7)
                .date("2021/09/23")
                .build();
        PainDto pain2 = PainDto.builder()
                .id(1)
                .painLevel(3)
                .date("2021/09/22")
                .build();
        List<PainDto> list = new ArrayList<>();
        list.add(pain1);
        list.add(pain2);
        //WHEN
        Map<String, List<PainDto>> actual = dateService.sortPainByDay(list);
        //THEN
        Map<String, List<PainDto>> expected = new HashMap<>();
        expected.put("2021/09/23", List.of(pain1));
        expected.put("2021/09/22", List.of(pain2));

        assertEquals(actual, expected);
    }

}