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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
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
    public void testUpdate() {
        //GIVEN
        long id = foodRepository.findByUserName("Frank").getId();
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
    public void testUpdateBadId() {
        //GIVEN
        String id = "99";
        FoodUpdateDto update = FoodUpdateDto.builder()
                .newValue("ErsatzErbse").build();
        //WHEN
        String foodName = foodRepository.findByUserName("Frank").getFoodName();
        HttpEntity<FoodUpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url() + "/" + id,
                HttpMethod.PUT,
                httpEntityPut,
                FoodDto.class);
        //THEN
        assertThat(foodName, is(not(nullValue())));
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    public void testDelete() {
        //GIVEN
        long id = foodRepository.findByUserName("Frank").getId();
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
    public void testDeleteBadId() {
        //GIVEN
        String id = "99";
        //WHEN
        String foodName = foodRepository.findByUserName("Frank").getFoodName();
        HttpEntity<Void> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<FoodDto> actualResponse = testRestTemplate.exchange(url() + "/" + id,
                HttpMethod.DELETE,
                httpEntityDelete,
                FoodDto.class);
        //THEN
        assertThat(foodName, is(not(nullValue())));
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }
}

