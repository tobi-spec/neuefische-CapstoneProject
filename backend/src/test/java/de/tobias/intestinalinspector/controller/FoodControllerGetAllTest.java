package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.api.food.FoodDto;
import de.tobias.intestinalinspector.api.food.FoodMapDto;
import de.tobias.intestinalinspector.api.food.FoodMapsDto;
import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.repository.FoodRepository;
import de.tobias.intestinalinspector.service.DateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class FoodControllerGetAllTest {

    @LocalServerPort
    private int port;

    private String url() {
        return "http://localhost:" + port + "/api/food";
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TestAuthorization testAuthorization;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private DateService dateService;

    @BeforeEach
    public void fill() {
        FoodEntity filler = FoodEntity.builder()
                .id(1)
                .foodName("Testtrauben")
                .date(dateService.getDate())
                .userName("Frank")
                .build();
        foodRepository.save(filler);
    }

    @AfterEach
    public void reset() {
        foodRepository.deleteAll();
    }

    @Test
    public void testGetAll(){
        //WHEN
        HttpEntity<FoodDto> httpEntityGet = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<FoodMapsDto> actualResponse = testRestTemplate.exchange(url(),
                HttpMethod.GET,
                httpEntityGet ,
                FoodMapsDto.class);
        //THEN
        long id = foodRepository.findByUserName("Frank").getId();
        FoodDto foodDto= FoodDto.builder()
                .id(id)
                .foodName("Testtrauben")
                .date(dateService.getDate())
                .build();

        List<FoodDto> list = new ArrayList<>();
        list.add(foodDto);

        FoodMapDto foodMapDto = new FoodMapDto();
        foodMapDto.setDate(foodDto.getDate());
        foodMapDto.setFoods(list);

        FoodMapsDto expectedMap = new FoodMapsDto();
        expectedMap.getFoodMaps().add(foodMapDto);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedMap, actualResponse.getBody());
    }
}
