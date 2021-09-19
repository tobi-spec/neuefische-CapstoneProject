package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.FoodDto;
import de.tobias.intestinalinspector.api.FoodListDto;
import de.tobias.intestinalinspector.api.UpdateDto;
import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.repository.FoodRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

    @Autowired
    private FoodRepository foodRepository;

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
        //GIVEN
        FoodDto foodToAdd = FoodDto.builder()
                .foodName("Testtrauben")
                .build();
        //WHEN
        //setup
        HttpEntity<FoodDto> httpEntity = new HttpEntity<>(foodToAdd, testAuthorization.Header("Frank", "user"));
        ResponseEntity<FoodDto> addResponse = testRestTemplate.exchange(url(),
                HttpMethod.POST,
                httpEntity,
                FoodDto.class);
        //methode to test
        HttpEntity<FoodDto> httpEntityGet = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<FoodListDto> actualResponse = testRestTemplate.exchange(url(),
                                                                                    HttpMethod.GET,
                                                                                    httpEntityGet ,
                                                                                    FoodListDto.class);
        //THEN
        long id = addResponse.getBody().getId();
        String name = addResponse.getBody().getFoodName();
        String date = addResponse.getBody().getDate();
        FoodDto foodDto= FoodDto.builder()
                .id(id)
                .foodName(name)
                .date(date)
                .build();

        FoodListDto expectedList = new FoodListDto();
        expectedList.addFood(foodDto);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedList, actualResponse.getBody());
    }

    @Test
    public void testUpdate(){
        //GIVEN
        FoodDto foodToAdd = FoodDto.builder()
                .foodName("Testtrauben")
                .build();
        String id = "1";
        UpdateDto update = UpdateDto.builder()
                .newName("ErsatzErbse")
                .build();
        //WHEN
        HttpEntity<UpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
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
    public void testUpdateBadId(){
        //GIVEN
        String id = "99";
        UpdateDto update = UpdateDto.builder()
                .newName("ErsatzErbse").build();
        //WHEN
        HttpEntity<UpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url()+"/update/" + id,
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
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url()+"/delete/" + id,
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
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url()+"/delete/" + id,
                HttpMethod.DELETE,
                httpEntityDelete,
                FoodDto.class) ;
        //THEN
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

}