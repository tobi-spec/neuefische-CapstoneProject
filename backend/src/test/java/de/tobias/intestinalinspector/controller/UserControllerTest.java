package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.TestAuthorization;
import de.tobias.intestinalinspector.api.AccessTokenDto;
import de.tobias.intestinalinspector.api.NewPassword;
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
        UserDto userToCreate = UserDto.builder()
                .userName("Maria")
                .userPassword("Maria123")
                .build();
        NewPassword newPassword = new NewPassword("54321");

        UserDto credentialsWithNewPassword = UserDto.builder()
                .userName("Frank")
                .userPassword(newPassword.getNewPassword())
                .build();

        //WHEN
        //create User
        HttpEntity<UserDto> httpEntityCreate = new HttpEntity<>(userToCreate);
        ResponseEntity<UserDto> actualResponseCreateUser = testRestTemplate.exchange(url()+"/api/user",
                HttpMethod.POST,
                httpEntityCreate,
                UserDto.class);

        //change password
        HttpEntity<NewPassword> httpEntityUpdate = new HttpEntity<>(newPassword, testAuthorization.Header("Frank", "user"));
        ResponseEntity<UserDto> actualResponseSetPassword = testRestTemplate.exchange(url()+"/api/user/password",
                                                                            HttpMethod.PUT,
                                                                            httpEntityUpdate,
                                                                            UserDto.class);

        //login in with new password
        ResponseEntity<AccessTokenDto> actualResponseLogin = testRestTemplate.postForEntity(
                url()+"/auth/access_token",
                credentialsWithNewPassword,
                AccessTokenDto.class);

        //THEN
        assertEquals(HttpStatus.OK, actualResponseCreateUser.getStatusCode());

        assertEquals(HttpStatus.OK, actualResponseSetPassword.getStatusCode());

        assertEquals(HttpStatus.OK, actualResponseLogin.getStatusCode());
        assertThat(actualResponseLogin.getBody().getToken(), is(not("")));

    }

    @Test
    @Order(4)
    public void testDeleteAccount(){
        //GIVEN
        UserDto userToCreate = UserDto.builder()
                .userName("Julia")
                .userPassword("Julia123")
                .build();
        //WHEN
        //create User
        HttpEntity<UserDto> httpEntityCreate = new HttpEntity<>(userToCreate);
        ResponseEntity<UserDto> actualResponseCreateUser = testRestTemplate.exchange(url()+"/api/user",
                                                                                    HttpMethod.POST,
                                                                                    httpEntityCreate,
                                                                                    UserDto.class);
        //delete User
        HttpEntity<UserDto> httpEntityDelete = new HttpEntity<>(testAuthorization.Header("Julia", "user"));
        ResponseEntity<UserDto> actualResponseDeleteAccount = testRestTemplate.exchange( url() + "/api/user/deleteAccount",
                                                                                        HttpMethod.DELETE,
                                                                                        httpEntityDelete,
                                                                                        UserDto.class);
        //THEN
        assertEquals(HttpStatus.OK, actualResponseCreateUser.getStatusCode());
        assertEquals(HttpStatus.OK, actualResponseDeleteAccount.getStatusCode());
        assertThat(actualResponseDeleteAccount.getBody(), is(not("")));
        assertEquals(userToCreate.getUserName(), actualResponseDeleteAccount.getBody().getUserName());
    }



}