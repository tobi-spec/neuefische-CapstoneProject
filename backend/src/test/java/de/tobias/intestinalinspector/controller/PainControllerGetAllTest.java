package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.api.pain.PainDto;
import de.tobias.intestinalinspector.api.pain.PainMapDto;
import de.tobias.intestinalinspector.api.pain.PainMapsDto;
import de.tobias.intestinalinspector.model.PainEntity;
import de.tobias.intestinalinspector.repository.PainRepository;
import de.tobias.intestinalinspector.service.DateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        properties = "spring.profiles.active:2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class PainControllerGetAllTest {

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

    @Autowired
    DateService dateService;

    @BeforeEach
    public void fill(){
        PainEntity filler = PainEntity.builder()
                .id(1)
                .painLevel(7)
                .date(dateService.getDate())
                .userName("Frank")
                .build();
        painRepository.save(filler);
    }

    @AfterEach
    public void reset(){
        painRepository.deleteAll();
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
        long id = painRepository.findByUserName("Frank").getId();
        PainDto painDto = PainDto.builder()
                .id(id)
                .painLevel(7)
                .date(dateService.getDate())
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
}
