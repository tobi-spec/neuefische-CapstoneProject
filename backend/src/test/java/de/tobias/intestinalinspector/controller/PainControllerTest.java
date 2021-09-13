package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.api.FrontendPainDto;
import de.tobias.intestinalinspector.repository.PainRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(
        properties = "spring.profiles.active:2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class PainControllerTest {

    @LocalServerPort
    private int port;

    private String url(){
        return "http://localhost:"+port+"/api/pain";
    }

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    TestAuthorization testAuthorization;

    @Autowired
    PainRepository painRepository;

    @AfterEach
    public void clear(){
        painRepository.deleteAll();
    }

    @Test
    public void testAddPain(){
        //GIVEN
        FrontendPainDto painToAdd = FrontendPainDto.builder()
                .painLevel(4)
                .build();
        //WHEN
        HttpEntity<FrontendPainDto> httpEntity = new HttpEntity<>(painToAdd,
                                                                    testAuthorization.Header("Frank", "user")
        );
        ResponseEntity<FrontendPainDto> actualResponse = testRestTemplate.exchange(url(),
                                                                                    HttpMethod.POST,
                                                                                    httpEntity,
                                                                                    FrontendPainDto.class);
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals(4, actualResponse.getBody().getPainLevel());
    }
}