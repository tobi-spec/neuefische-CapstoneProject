package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.FoodDto;
import de.tobias.intestinalinspector.api.FoodMapDto;
import de.tobias.intestinalinspector.api.FoodMapsDto;
import de.tobias.intestinalinspector.api.FoodUpdateDto;
import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.repository.FoodRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FoodControllerTest {

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
    private FoodRepository foodRepository;

    @BeforeEach
    public void fill(){
        FoodEntity filler = FoodEntity.builder()
                .id(1)
                .foodName("Testtrauben")
                .date("Placeholder")
                .userName("Frank")
                .build();
        foodRepository.save(filler);
    }

    @AfterEach
    public void reset(){
        foodRepository.deleteAll();
    }

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
                .date("Placeholder")
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

    @Test
    public void testUpdate(){
        //GIVEN
        String id = "1";
        FoodUpdateDto update = FoodUpdateDto.builder()
                .newValue("ErsatzErbse").build();
        //WHEN
        HttpEntity<FoodUpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url()+"/" + id,
                HttpMethod.PUT,
                httpEntityPut,
                FoodDto.class);
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("ErsatzErbse", actualResponse.getBody().getFoodName());
    }

    @Test
    public void testUpdateBadId(){
        //GIVEN
        String id = "99";
        FoodUpdateDto update = FoodUpdateDto.builder()
                .newValue("ErsatzErbse").build();
        //WHEN
        HttpEntity<FoodUpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url()+"/" + id,
                HttpMethod.PUT,
                httpEntityPut,
                FoodDto.class);
        //THEN
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    public void testDelete(){
        //GIVEN
        String id = "1";
        //WHEN
        HttpEntity<Void> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url()+"/" + id,
                HttpMethod.DELETE,
                httpEntityDelete,
                FoodDto.class) ;
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("Testtrauben", actualResponse.getBody().getFoodName());
    }

    @Test
    public void testDeleteBadId(){
        //GIVEN
        String id = "99";
        //WHEN
        HttpEntity<Void> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url()+"/" + id,
                HttpMethod.DELETE,
                httpEntityDelete,
                FoodDto.class) ;
        //THEN
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

}