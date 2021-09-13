package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.FrontendFoodDto;
import de.tobias.intestinalinspector.api.FrontendFoodListDto;
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
        FrontendFoodDto foodToAdd = FrontendFoodDto.builder()
                .foodName("Testtrauben")
                .build();


        //WHEN
        HttpEntity<FrontendFoodDto> httpEntityPost = new HttpEntity<>(foodToAdd,
                testAuthorization.Header("Frank", "user")
        );
        testRestTemplate.exchange(url(),
                HttpMethod.POST,
                httpEntityPost,
                FrontendFoodDto.class);

        HttpEntity<FrontendFoodDto> httpEntityGet = new HttpEntity<>(testAuthorization.Header("Frank", "user"));

        ResponseEntity<FrontendFoodListDto> actualResponse = testRestTemplate.exchange(url(),
                                                                                    HttpMethod.GET,
                                                                                    httpEntityGet ,
                                                                                    FrontendFoodListDto.class);

        //THEN
        FrontendFoodDto foodDto= FrontendFoodDto.builder()
                .id(1)
                .foodName("Testtrauben")
                .date("2021")
                .build();

        FrontendFoodListDto expectedList = new FrontendFoodListDto();
        expectedList.addFood(foodDto);

        assertEquals(expectedList, actualResponse.getBody());
    }

}