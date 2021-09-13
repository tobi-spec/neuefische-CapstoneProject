package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.FrontendFoodDto;
import de.tobias.intestinalinspector.api.FrontendFoodListDto;
import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.repository.FoodRepository;
import de.tobias.intestinalinspector.TestAuthorization;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class FoodControllerTest {

    @LocalServerPort
    private int port;

    private String url(){
        return "http://localhost:"+port+"/api/food";
    }

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    TestAuthorization testAuthorization;

    @AfterEach
    public void clear(){
        foodRepository.deleteAll();
    }


    @Test
    public void testAddFood(){
        //GIVEN
        FrontendFoodDto foodToAdd = FrontendFoodDto.builder()
                .foodName("Testtrauben")
                .build();
        //WHEN
        HttpEntity<FrontendFoodDto> httpEntity = new HttpEntity<>(foodToAdd,
                                                                testAuthorization.Header("Frank", "user")
        );
        ResponseEntity<FrontendFoodDto> actualResponse = testRestTemplate.exchange(url(),
                                                                            HttpMethod.POST,
                                                                            httpEntity,
                                                                            FrontendFoodDto.class);
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("Testtrauben", actualResponse.getBody().getFoodName());
    }

    @Test
    public void testGetAll(){
        //GIVEN
        Date date = new Date();

        FoodEntity food1 = FoodEntity.builder()
                .userName("Tester")
                .foodName("Gurke")
                .date(date)
                .build();
        FoodEntity food2 = FoodEntity.builder()
                .userName("Tester")
                .foodName("Tomato")
                .date(date)
                .build();

        foodRepository.save(food1);
        foodRepository.save(food2);

        //WHEN
        ResponseEntity<FrontendFoodListDto> actualResponse = testRestTemplate.getForEntity(url(),
                                                                                            FrontendFoodListDto.class);

        //THEN
        FrontendFoodDto food1Dto= FrontendFoodDto.builder()
                .foodName("Gurke")
                .date(date)
                .build();
        FrontendFoodDto food2Dto = FrontendFoodDto.builder()
                .foodName("Tomato")
                .date(date)
                .build();

        FrontendFoodListDto expectedList = new FrontendFoodListDto();
        expectedList.addFood(food1Dto);
        expectedList.addFood(food2Dto);
        assertEquals(expectedList, actualResponse.getBody());
    }

}