package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.api.pain.PainDto;
import de.tobias.intestinalinspector.api.pain.PainUpdateDto;
import de.tobias.intestinalinspector.model.PainEntity;
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
class PainControllerChangeTest {

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

    @BeforeEach
    public void fill(){
        PainEntity filler = PainEntity.builder()
                .id(1)
                .painLevel(7)
                .date("Placeholder")
                .userName("Frank")
                .build();
        painRepository.save(filler);
    }

    @AfterEach
    public void reset(){
        painRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testUpdate(){
        //GIVEN
        String id = "1";
        PainUpdateDto update = PainUpdateDto.builder()
                .newValue(1).build();
        //WHEN
        HttpEntity<PainUpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<PainDto> actualResponse = testRestTemplate.exchange(url()+"/" + id,
                HttpMethod.PUT,
                httpEntityPut,
                PainDto.class);
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals(1, actualResponse.getBody().getPainLevel());
    }

    @Test
    @Order(2)
    public void testUpdateBadId(){
        //GIVEN
        String id = "99";
        PainUpdateDto update = PainUpdateDto.builder()
                .newValue(1).build();
        //WHEN
        HttpEntity<PainUpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<PainDto> actualResponse = testRestTemplate.exchange(url()+"/" + id,
                HttpMethod.PUT,
                httpEntityPut,
                PainDto.class);
        //THEN
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    @Order(3)
    public void testDelete(){
        //GIVEN
        String id = "3";
        //WHEN
        HttpEntity<Void> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<PainDto> actualResponse = testRestTemplate.exchange(url()+"/" + id,
                HttpMethod.DELETE,
                httpEntityDelete,
                PainDto.class) ;
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals(7, actualResponse.getBody().getPainLevel());
    }

    @Test
    @Order(4)
    public void testDeleteBadId(){
        //GIVEN
        String id = "99";
        //WHEN
        HttpEntity<Void> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<PainDto> actualResponse = testRestTemplate.exchange(url()+"/" + id,
                HttpMethod.DELETE,
                httpEntityDelete,
                PainDto.class) ;
        //THEN
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }
}
