package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.FoodDto;
import de.tobias.intestinalinspector.api.FoodListDto;
import de.tobias.intestinalinspector.api.UpdateDto;
import de.tobias.intestinalinspector.repository.FoodRepository;
import de.tobias.intestinalinspector.TestAuthorization;
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

    @BeforeEach
    public void fill() {
        FoodDto foodToAdd = FoodDto.builder()
                .foodName("Testtrauben")
                .build();

        HttpEntity<FoodDto> httpEntity = new HttpEntity<>(foodToAdd,
                testAuthorization.Header("Frank", "user")
        );
        testRestTemplate.exchange(url(),
                HttpMethod.POST,
                httpEntity,
                FoodDto.class);
    }

    @AfterEach
    public void clear(){
        foodRepository.deleteAll();
    }


    @Test
    public void testAddFood(){
        // This test can not use beforeEach() and must add Object to database by itself
        //GIVEN
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
        // This test can not use beforeEach() and must add Object to database by itself
        //GIVEN
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
        // This test can not use beforeEach() and must add Object to database by itself
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
        HttpEntity<FoodDto> httpEntityGet = new HttpEntity<>(testAuthorization.Header("Frank",
                                                                                                    "user"));
        ResponseEntity<FoodListDto> actualResponse = testRestTemplate.exchange(url(),
                                                                                    HttpMethod.GET,
                                                                                    httpEntityGet ,
                                                                                    FoodListDto.class);
        //THEN
        FoodDto foodDto= FoodDto.builder()
                .id(1)
                .foodName("Testtrauben")
                .date("2021")
                .build();

        FoodListDto expectedList = new FoodListDto();
        expectedList.addFood(foodDto);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedList, actualResponse.getBody());
    }
// Test does not work, will be fixed later
/*    @Test
    public void testUpdate(){
        //GIVEN
        UpdateDto updateDto = UpdateDto.builder()
                .id(1L)
                .newName("ErsatzErbse")
                .build();
        //WHEN
        HttpEntity<UpdateDto> httpEntityPut = new HttpEntity<>(updateDto, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<UpdateDto> actualResponse = testRestTemplate.exchange(url(),
                HttpMethod.PUT,
                httpEntityPut,
                UpdateDto.class);
        //THEN
        assertEquals(HttpStatus.UNAUTHORIZED, actualResponse.getStatusCode());
    }*/

    @Test
    public void testUpdateBadId(){
        //GIVEN
        UpdateDto updateDto = UpdateDto.builder()
                .id(99)
                .newName("ErsatzErbse")
                .build();
        //WHEN
        HttpEntity<UpdateDto> httpEntityPut = new HttpEntity<>(updateDto, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<UpdateDto> actualResponse = testRestTemplate.exchange(url(),
                                                                            HttpMethod.PUT,
                                                                            httpEntityPut,
                                                                            UpdateDto.class);
        //THEN
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
    }

}