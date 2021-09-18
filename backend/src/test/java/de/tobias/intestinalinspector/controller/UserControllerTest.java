package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.api.UserDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @LocalServerPort
    private int port;

    private String url(){
        return "http://localhost:"+port+"/api/user";
    }

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    @Order(1)
    public void testCreateUser(){
        //GIVEN
        UserDto userToCreate = UserDto.builder()
                .userName("Frank")
                .userPassword("Frank123")
                .build();
        //WHEN
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(userToCreate);
        ResponseEntity<UserDto> actualResponse = testRestTemplate.exchange(url(),
                                                                            HttpMethod.POST,
                                                                            httpEntity,
                                                                            UserDto.class);
        //THEN
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("Frank", actualResponse.getBody().getUserName());
    }

    @Test
    @Order(2)
    public void testCreateUserAlreadyExists(){
        //GIVEN
        UserDto userToCreate = UserDto.builder()
                .userName("Frank")
                .userPassword("Frank123")
                .build();
        //WHEN
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(userToCreate);
        ResponseEntity<UserDto> actualResponse = testRestTemplate.exchange(url(),
                HttpMethod.POST,
                httpEntity,
                UserDto.class);
        //THEN
        assertEquals(HttpStatus.CONFLICT, actualResponse.getStatusCode());

    }

}