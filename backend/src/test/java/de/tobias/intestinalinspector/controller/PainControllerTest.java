package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.api.PainDto;
import de.tobias.intestinalinspector.api.PainListDto;
import de.tobias.intestinalinspector.api.UpdateDto;
import de.tobias.intestinalinspector.repository.PainRepository;
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
        properties = "spring.profiles.active:2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PainControllerTest {

    // Runs as combined test, methods are chained together

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

    @Test
    @Order(1)
    public void testAddPain(){
        //GIVEN
        PainDto painToAdd = PainDto.builder()
                .painLevel(7)
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
        assertEquals(7, actualResponse.getBody().getPainLevel());
    }


    @Test
    @Order(2)
    public void testGetAll(){
        //WHEN
        HttpEntity<PainDto> httpEntityGet = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<PainListDto> actualResponse = testRestTemplate.exchange(url(),
                                                                                HttpMethod.GET,
                                                                                httpEntityGet,
                                                                                PainListDto.class);
        //THEN
        PainDto painDto = PainDto.builder()
                .id(1)
                .painLevel(7)
                .date("Placeholder")
                .build();

        PainListDto expectedList = new PainListDto();
        expectedList.addPain(painDto);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedList, actualResponse.getBody());
    }

    @Test
    @Order(3)
    public void testUpdate(){
        //GIVEN
        String id = "1";
        UpdateDto update = UpdateDto.builder()
                .newNumber(1).build();
        //WHEN
        HttpEntity<UpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<PainDto> actualResponse = testRestTemplate.exchange(url()+"/update/" + id,
                HttpMethod.PUT,
                httpEntityPut,
                PainDto.class);
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals(1, actualResponse.getBody().getPainLevel());

    }

    @Test
    @Order(4)
    public void testUpdateBadId(){
        //GIVEN
        String id = "99";
        UpdateDto update = UpdateDto.builder()
                .newNumber(1).build();
        //WHEN
        HttpEntity<UpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<PainDto> actualResponse = testRestTemplate.exchange(url()+"/update/" + id,
                HttpMethod.PUT,
                httpEntityPut,
                PainDto.class);
        //THEN
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());

    }

    @Test
    @Order(5)
    public void testDelete(){
        //GIVEN
        String id = "1";
        //WHEN
        HttpEntity<Void> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<PainDto> actualResponse = testRestTemplate.exchange(url()+"/delete/" + id,
                                                                                HttpMethod.DELETE,
                                                                                httpEntityDelete,
                                                                                PainDto.class) ;
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals(7, actualResponse.getBody().getPainLevel());
    }

    @Test
    @Order(8)
    public void testDeleteBadId(){
        //GIVEN
        String id = "99";
        //WHEN
        HttpEntity<Void> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<PainDto> actualResponse = testRestTemplate.exchange(url()+"/delete/" + id,
                HttpMethod.DELETE,
                httpEntityDelete,
                PainDto.class) ;
        //THEN
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }
}