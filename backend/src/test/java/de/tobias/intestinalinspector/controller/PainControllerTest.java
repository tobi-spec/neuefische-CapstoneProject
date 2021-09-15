package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.api.PainDto;
import de.tobias.intestinalinspector.api.PainListDto;
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
        PainDto painToAdd = PainDto.builder()
                .painLevel(4)
                .build();
        //WHEN
        HttpEntity<PainDto> httpEntity = new HttpEntity<>(painToAdd,
                                                                    testAuthorization.Header("Frank", "user")
        );
        ResponseEntity<PainDto> actualResponse = testRestTemplate.exchange(url(),
                                                                                    HttpMethod.POST,
                                                                                    httpEntity,
                                                                                    PainDto.class);
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals(4, actualResponse.getBody().getPainLevel());
    }

    @Test
    public void testGetAll(){
        //GIVEN
        PainDto painToAdd = PainDto.builder()
                .painLevel(7)
                .build();
        //WHEN
        HttpEntity<PainDto> httpEntityPost = new HttpEntity<>(painToAdd,
                testAuthorization.Header("Frank", "user")
        );
        testRestTemplate.exchange(url(),
                                HttpMethod.POST,
                                httpEntityPost,
                                PainDto.class);

        HttpEntity<PainDto> httpEntityGet = new HttpEntity<>(testAuthorization.Header("Frank", "user"));

        ResponseEntity<PainListDto> actualResponse = testRestTemplate.exchange(url(),
                                                                                        HttpMethod.GET,
                                                                                        httpEntityGet,
                                                                                        PainListDto.class);
        //THEN
        PainDto painDto = PainDto.builder()
                .id(1)
                .painLevel(7)
                .date("2021")
                .build();

        PainListDto expectedList = new PainListDto();
        expectedList.addPain(painDto);

        assertEquals(expectedList, actualResponse.getBody());
    }
}