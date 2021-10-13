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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        properties = "spring.profiles.active:2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
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

    @Test
    public void testUpdate(){
        //GIVEN
        long id = painRepository.findByUserName("Frank").getId();
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
    public void testUpdateBadId(){
        //GIVEN
        String id = "99";
        PainUpdateDto update = PainUpdateDto.builder()
                .newValue(1).build();
        //WHEN
        int painLevel = painRepository.findByUserName("Frank").getPainLevel();
        HttpEntity<PainUpdateDto> httpEntityPut = new HttpEntity<>(update, testAuthorization.Header("Frank",
                "user"));
        ResponseEntity<PainDto> actualResponse = testRestTemplate.exchange(url()+"/" + id,
                HttpMethod.PUT,
                httpEntityPut,
                PainDto.class);
        //THEN
        assertThat(painLevel, is(not(nullValue())));
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    public void testDelete(){
        //GIVEN
        long id = painRepository.findByUserName("Frank").getId();
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
    public void testDeleteBadId(){
        //GIVEN
        String id = "99";
        //WHEN
        int painLevel = painRepository.findByUserName("Frank").getPainLevel();
        HttpEntity<Void> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<PainDto> actualResponse = testRestTemplate.exchange(url()+"/" + id,
                HttpMethod.DELETE,
                httpEntityDelete,
                PainDto.class) ;
        //THEN
        assertThat(painLevel, is(not(nullValue())));
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }
}
