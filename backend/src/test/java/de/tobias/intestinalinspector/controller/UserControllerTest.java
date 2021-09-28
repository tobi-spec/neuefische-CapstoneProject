package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.api.AccessTokenDto;
import de.tobias.intestinalinspector.api.CredentialsDto;
import de.tobias.intestinalinspector.api.NewPassword;
import de.tobias.intestinalinspector.api.UserDto;
import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.repository.AppUserRepository;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
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
        return "http://localhost:"+port;
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TestAuthorization testAuthorization;

    @Autowired
    private AppUserRepository appUserRepository;


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
        ResponseEntity<UserDto> actualResponse = testRestTemplate.exchange(url()+"/api/user",
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
        ResponseEntity<UserDto> actualResponse = testRestTemplate.exchange(url()+"/api/user",
                HttpMethod.POST,
                httpEntity,
                UserDto.class);
        //THEN
        assertEquals(HttpStatus.CONFLICT, actualResponse.getStatusCode());

    }

    @Test
    @Order(3)
    public void testChangePassword() {
        //GIVEN
        String username = "Frank";
        AppUserEntity user = AppUserEntity.builder()
                .userName(username)
                .userRole("user")
                .userPassword("12345")
                .id(1)
                .build();
        appUserRepository.save(user);
        NewPassword newPassword = new NewPassword("54321");

        CredentialsDto credentialsWithNewPassword = CredentialsDto.builder()
                .userName(username)
                .userPassword(newPassword.getNewPassword())
                .build();

        //WHEN
        //change password
        HttpEntity<NewPassword> httpEntity = new HttpEntity<>(newPassword, testAuthorization.Header("Frank", "user"));
        ResponseEntity<UserDto> actualResponseSetPassword = testRestTemplate.exchange(url()+"/api/user/password",
                                                                            HttpMethod.PUT,
                                                                            httpEntity,
                                                                            UserDto.class);

        //login in with new password
        ResponseEntity<AccessTokenDto> actualResponseLogin = testRestTemplate.postForEntity(
                url()+"/auth/access_token",
                credentialsWithNewPassword,
                AccessTokenDto.class);

        //THEN
        assertEquals(HttpStatus.OK, actualResponseSetPassword.getStatusCode());

        assertEquals(HttpStatus.OK, actualResponseLogin.getStatusCode());
        assertThat(actualResponseLogin.getBody().getToken(), is(not("")));

    }



}