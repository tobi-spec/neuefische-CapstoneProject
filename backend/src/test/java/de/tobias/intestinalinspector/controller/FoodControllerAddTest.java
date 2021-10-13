package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.food.FoodDto;
import de.tobias.intestinalinspector.api.food.FoodMapDto;
import de.tobias.intestinalinspector.api.food.FoodMapsDto;
import de.tobias.intestinalinspector.api.food.FoodUpdateDto;
import de.tobias.intestinalinspector.TestAuthorization;
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
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class FoodControllerAddTest {

    @LocalServerPort
    private int port;

    private String url(){
        return "http://localhost:"+port+"/api/food";
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TestAuthorization testAuthorization;

    @Autowired
    private DateService dateService;


    @Test
    public void testAddFood(){
        FoodDto foodToAdd = FoodDto.builder()
                .foodName("Testtrauben")
                .build();
        //WHEN
        HttpEntity<FoodDto> httpEntity = new HttpEntity<>(foodToAdd,
                testAuthorization.Header("Frank", "user")
        );
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url(),
                HttpMethod.POST,
                httpEntity,
                FoodDto.class);
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("Testtrauben", actualResponse.getBody().getFoodName());
    }

    @Test
    public void testAddFoodWithoutText(){
        FoodDto foodToAdd = FoodDto.builder()
                .foodName("")
                .build();
        //WHEN
        HttpEntity<FoodDto> httpEntity = new HttpEntity<>(foodToAdd,
                testAuthorization.Header("Frank", "user")
        );
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url(),
                HttpMethod.POST,
                httpEntity,
                FoodDto.class);
        //THEN
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
    }

    @Test
    public void testAddFoodTextIsNull(){
        //GIVEN
        FoodDto foodToAdd = FoodDto.builder()
                .foodName(null)
                .build();
        //WHEN
        HttpEntity<FoodDto> httpEntity = new HttpEntity<>(foodToAdd,
                testAuthorization.Header("Frank", "user")
        );
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url(),
                HttpMethod.POST,
                httpEntity,
                FoodDto.class);
        //THEN
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
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
        FoodDto foodDto= FoodDto.builder()
                .id(1)
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