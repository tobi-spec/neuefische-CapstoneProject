package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.CredentialsDto;
import de.tobias.intestinalinspector.api.NewPassword;
import de.tobias.intestinalinspector.api.UserDto;
import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.service.AppUserService;
import de.tobias.intestinalinspector.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class UserController {

    public static final String CREATE_USER = "/api/user";


    private final AppUserService appUserService;

    @Autowired
    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping(CREATE_USER)
    public ResponseEntity<UserDto> create(@RequestBody CredentialsDto credentialsDto) {
        AppUserEntity newUser = map(credentialsDto);
        Optional<AppUserEntity> foundUser = appUserService.findUser(newUser.getUserName());
        if(foundUser.isEmpty()){
            AppUserEntity createdUser = appUserService.create(newUser);
            UserDto userToReturn = map(createdUser);
            return ok(userToReturn);
        } else {
            throw new EntityExistsException("Username already exists");
        }

    }

    @PutMapping("/api/user/password")
    public ResponseEntity<UserDto> newPassword (@AuthenticationPrincipal AppUserEntity appUserEntity,
                                               @RequestBody NewPassword password){
        String userName = appUserEntity.getUserName();
        String newPassword = password.getNewPassword();
        AppUserEntity userWithNewPassword = appUserService.updatePassword(userName, newPassword);
        UserDto userToReturn = map(userWithNewPassword);
        return ok(userToReturn);
    }

    @DeleteMapping("api/user/deleteAccount")
    public ResponseEntity<UserDto> deleteAccount (@AuthenticationPrincipal AppUserEntity appUserEntity){
        String userName = appUserEntity.getUserName();
        AppUserEntity userToDelete = appUserService.deleteUser(userName);
        UserDto deletedUser = map(userToDelete);
        return ok(deletedUser);
    }

    private AppUserEntity map( CredentialsDto credentialsDto) {
        return AppUserEntity.builder()
                .userName(credentialsDto.getUserName())
                .userPassword(credentialsDto.getUserPassword())
                .build();
    }

    private UserDto map(AppUserEntity createdUser) {
        return UserDto.builder()
                .userName(createdUser.getUserName())
                .build();
    }
}
