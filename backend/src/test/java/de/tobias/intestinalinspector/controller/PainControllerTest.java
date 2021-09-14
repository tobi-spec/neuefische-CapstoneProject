package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.api.FrontendPainDto;
import de.tobias.intestinalinspector.api.FrontendPainListDto;
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

    @Test
    public void testGetAll(){
        //GIVEN
        FrontendPainDto painToAdd = FrontendPainDto.builder()
                .painLevel(7)
                .build();
        //WHEN
        HttpEntity<FrontendPainDto> httpEntityPost = new HttpEntity<>(painToAdd,
                testAuthorization.Header("Frank", "user")
        );
        testRestTemplate.exchange(url(),
                                HttpMethod.POST,
                                httpEntityPost,
                                FrontendPainDto.class);

        HttpEntity<FrontendPainDto> httpEntityGet = new HttpEntity<>(testAuthorization.Header("Frank", "user"));

        ResponseEntity<FrontendPainListDto> actualResponse = testRestTemplate.exchange(url(),
                                                                                        HttpMethod.GET,
                                                                                        httpEntityGet,
                                                                                        FrontendPainListDto.class);
        //THEN
        FrontendPainDto painDto = FrontendPainDto.builder()
                .id(1)
                .painLevel(7)
                .date("2021")
                .build();

        FrontendPainListDto expectedList = new FrontendPainListDto();
        expectedList.addPain(painDto);

        assertEquals(expectedList, actualResponse.getBody());
    }
}