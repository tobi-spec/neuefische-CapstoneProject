package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.api.AccessTokenDto;
import de.tobias.intestinalinspector.api.UserDto;
import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class LoginControllerTest {

    @LocalServerPort
    private int port;

    private String url(){
        return "http://localhost:"+port+"/auth/access_token";
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void testCorrectCredentials(){
        //GIVEN
        String username = "ThomasTester";
        String password = "erfcd?881";
        String role = "user";
        String hashPassword = passwordEncoder.encode(password);

        AppUserEntity user = AppUserEntity.builder()
                .userName(username)
                .userRole(role)
                .userPassword(hashPassword)
                .build();

        appUserRepository.save(user);

        UserDto credentials = UserDto.builder()
                .userName(username)
                .userPassword(password)
                .build();
        //WHEN
        ResponseEntity<AccessTokenDto> responseEntity = testRestTemplate.postForEntity(
                                                                                url(),
                                                                                credentials,
                                                                                AccessTokenDto.class);
        //THEN
        HttpStatus expected = HttpStatus.OK;
        assertEquals(expected, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody().getToken(), is(not("")));
    }

    @Test
    public void testFalseCredentials(){
        //GIVEN
        String username = "FalscherFrank";
        String password = "1";

        UserDto credentials = UserDto.builder()
                .userName(username)
                .userPassword(password)
                .build();

        //WHEN
        ResponseEntity<AccessTokenDto> responseEntity = testRestTemplate.postForEntity(
                                                                                url(),
                                                                                credentials,
                                                                                AccessTokenDto.class);
        //THEN
        HttpStatus expected = HttpStatus.UNAUTHORIZED;
        assertEquals(expected, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody(), nullValue());
    }

    @Test
    public void testNullCredentials(){
        //GIVEN
        UserDto noCredentials = new UserDto();
        //WHEN
        ResponseEntity<AccessTokenDto> responseEntity = testRestTemplate.postForEntity(url(),
                                                                                    noCredentials,
                                                                                    AccessTokenDto.class);
        //THEN
        HttpStatus expected = HttpStatus.BAD_REQUEST;
        assertEquals(expected, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody().getToken(), nullValue());
    }

    @Test
    public void testEmptyCredentials(){
        //GIVEN
        String username = "";
        String password = "";

        UserDto emptyCredentials = UserDto.builder()
                .userName(username)
                .userPassword(password)
                .build();

        //WHEN
        ResponseEntity<AccessTokenDto> responseEntity = testRestTemplate.postForEntity(url(),
                                                                                        emptyCredentials,
                                                                                        AccessTokenDto.class);
        //THEN
        HttpStatus expected = HttpStatus.BAD_REQUEST;
        assertEquals(expected, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody().getToken(), nullValue());
    }
}
