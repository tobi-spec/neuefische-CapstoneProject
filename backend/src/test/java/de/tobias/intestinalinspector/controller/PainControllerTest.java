package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.api.*;
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
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(
        properties = "spring.profiles.active:2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
    public void testGetAll(){
        //WHEN
        HttpEntity<PainDto> httpEntityGet = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<PainMapsDto> actualResponse = testRestTemplate.exchange(url(),
                HttpMethod.GET,
                httpEntityGet,
                PainMapsDto.class);
        //THEN
        PainDto painDto = PainDto.builder()
                .id(1)
                .painLevel(7)
                .date("Placeholder")
                .build();

        List<PainDto> list = new ArrayList<>();
        list.add(painDto);

        PainMapDto painMapDto = new PainMapDto();
        painMapDto.setDate(painDto.getDate());
        painMapDto.setPains(list);

        PainMapsDto expectedMap = new PainMapsDto();
        expectedMap.getPainMaps().add(painMapDto);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedMap, actualResponse.getBody());
    }

    @Test
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
    public void testDelete(){
        //GIVEN
        String id = "1";
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
        HttpEntity<Void> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Frank", "user"));
        ResponseEntity<PainDto> actualResponse = testRestTemplate.exchange(url()+"/" + id,
                HttpMethod.DELETE,
                httpEntityDelete,
                PainDto.class) ;
        //THEN
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }
}