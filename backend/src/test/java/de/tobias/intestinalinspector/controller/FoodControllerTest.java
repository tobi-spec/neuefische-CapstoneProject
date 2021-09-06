package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.dto.FrontendFoodDto;
import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.repository.FoodRepository;
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

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FoodControllerTest {

    @LocalServerPort
    int port;

    private String url(){
        return "http://localhost:"+port+"/api/food";
    }

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    FoodRepository foodRepository;

    @AfterEach
    public void clear(){
        FoodEntity toDelete = foodRepository.findFirstByOrderByIdDesc();
        foodRepository.deleteById(toDelete.getId());
    }


    @Test
    public void testAddFood(){
        //GIVEN
        FrontendFoodDto foodToAdd = FrontendFoodDto.builder()
                .foodName("Testtrauben")
                .build();
        //WHEN
        HttpEntity<FrontendFoodDto> httpEntity = new HttpEntity<>(foodToAdd);
        ResponseEntity<FrontendFoodDto> actualResponse = testRestTemplate.exchange(url(),
                                                                            HttpMethod.POST,
                                                                            httpEntity,
                                                                            FrontendFoodDto.class);
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals("Testtrauben", actualResponse.getBody().getFoodName());
    }



}