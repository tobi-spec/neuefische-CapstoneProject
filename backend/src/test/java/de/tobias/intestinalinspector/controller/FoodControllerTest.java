package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.dto.FrontendFoodDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.profiles.active:h2"
)
class FoodControllerTest {

    @LocalServerPort
    int port;

    private String url(){
        return "http:localhost:" + port + "api/food";
    }

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void testAddFoodEntityToDatabase(){

        //GIVEN
        FrontendFoodDto foodToAdd = FrontendFoodDto.builder()
                .foodName("TestTrauben")
                .build();
        //WHEN
        HttpEntity<FrontendFoodDto> httpEntity = new HttpEntity<>(foodToAdd);
        ResponseEntity<FrontendFoodDto> actualResponse = testRestTemplate.exchange(url(),
                                                                            HttpMethod.POST,
                                                                            httpEntity,
                                                                            FrontendFoodDto.class);
        //THEN
        assertThat(actualResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(actualResponse.getBody().getFoodName(), is("TestTrauben"));
    }
}