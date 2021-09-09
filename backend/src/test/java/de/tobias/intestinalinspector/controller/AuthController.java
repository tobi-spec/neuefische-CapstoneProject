package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.api.AccessTokenDto;
import de.tobias.intestinalinspector.api.CredentialsDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class AuthController {

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

        CredentialsDto credentials = CredentialsDto.builder()
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
    }
}
