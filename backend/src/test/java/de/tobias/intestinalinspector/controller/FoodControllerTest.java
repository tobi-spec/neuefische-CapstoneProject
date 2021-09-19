package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.FoodDto;
import de.tobias.intestinalinspector.api.FoodListDto;
import de.tobias.intestinalinspector.api.FoodUpdateDto;
import de.tobias.intestinalinspector.TestAuthorization;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FoodControllerTest {

    // Runs as combined test, methods are chained together

    @LocalServerPort
    private int port;

    private String url(){
        return "http://localhost:"+port+"/api/food";
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TestAuthorization testAuthorization;


    @Test
    @Order(1)
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
    @Order(2)
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
    @Order(3)
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
    @Order(4)
    public void testGetAll(){
        //WHEN
        HttpEntity<FoodDto> httpEntityGet = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<FoodListDto> actualResponse = testRestTemplate.exchange(url(),
                HttpMethod.GET,
                httpEntityGet ,
                FoodListDto.class);
        //THEN
        FoodDto foodDto= FoodDto.builder()
                .id(1)
                .foodName("Testtrauben")
                .date("Placeholder")
                .build();

        FoodListDto expectedList = new FoodListDto();
        expectedList.addFood(foodDto);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedList, actualResponse.getBody());
    }

    @Test
    @Order(5)
    public void testUpdate(){
        //GIVEN
        String id = "1";
        FoodUpdateDto update = FoodUpdateDto.builder()
                .newValue("ErsatzErbse").build();
        //WHEN
        HttpEntity<FoodUpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url()+"/update/" + id,
                HttpMethod.PUT,
                httpEntityPut,
                FoodDto.class);
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("ErsatzErbse", actualResponse.getBody().getFoodName());
    }

    @Test
    @Order(6)
    public void testUpdateBadId(){
        //GIVEN
        String id = "99";
        FoodUpdateDto update = FoodUpdateDto.builder()
                .newValue("ErsatzErbse").build();
        //WHEN
        HttpEntity<FoodUpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url()+"/update/" + id,
                HttpMethod.PUT,
                httpEntityPut,
                FoodDto.class);
        //THEN
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    @Order(7)
    public void testDelete(){
        //GIVEN
        String id = "1";
        //WHEN
        HttpEntity<Void> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url()+"/delete/" + id,
                HttpMethod.DELETE,
                httpEntityDelete,
                FoodDto.class) ;
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("ErsatzErbse", actualResponse.getBody().getFoodName());
    }

    @Test
    @Order(8)
    public void testDeleteBadId(){
        //GIVEN
        String id = "99";
        //WHEN
        HttpEntity<Void> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url()+"/delete/" + id,
                HttpMethod.DELETE,
                httpEntityDelete,
                FoodDto.class) ;
        //THEN
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

}