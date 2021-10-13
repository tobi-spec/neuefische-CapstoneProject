package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.api.food.FoodDto;
import de.tobias.intestinalinspector.api.food.FoodUpdateDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FoodControllerChangeTest {

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

    @BeforeEach
    public void fill() {
        FoodEntity filler = FoodEntity.builder()
                .id(1)
                .foodName("Testtrauben")
                .date("Placeholder")
                .userName("Frank")
                .build();
        foodRepository.save(filler);
    }

    @AfterEach
    public void reset() {
        foodRepository.deleteAll();
    }


    @Test
    @Order(1)
    public void testUpdate() {
        //GIVEN
        String id = "1";
        FoodUpdateDto update = FoodUpdateDto.builder()
                .newValue("ErsatzErbse").build();
        //WHEN
        HttpEntity<FoodUpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url() + "/" + id,
                HttpMethod.PUT,
                httpEntityPut,
                FoodDto.class);
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("ErsatzErbse", actualResponse.getBody().getFoodName());
    }

    @Test
    @Order(2)
    public void testUpdateBadId() {
        //GIVEN
        String id = "99";
        FoodUpdateDto update = FoodUpdateDto.builder()
                .newValue("ErsatzErbse").build();
        //WHEN
        HttpEntity<FoodUpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url() + "/" + id,
                HttpMethod.PUT,
                httpEntityPut,
                FoodDto.class);
        //THEN
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    @Order(3)
    public void testDelete() {
        //GIVEN
        String id = "3";
        //WHEN
        HttpEntity<Void> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url() + "/" + id,
                HttpMethod.DELETE,
                httpEntityDelete,
                FoodDto.class);
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("Testtrauben", actualResponse.getBody().getFoodName());
    }

    @Test
    @Order(4)
    public void testDeleteBadId() {
        //GIVEN
        String id = "99";
        //WHEN
        HttpEntity<Void> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url() + "/" + id,
                HttpMethod.DELETE,
                httpEntityDelete,
                FoodDto.class);
        //THEN
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }
}

