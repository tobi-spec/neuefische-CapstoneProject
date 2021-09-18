package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.UserDto;
import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping()
public class UserController {

    public static final String CREATE_USER = "/api/user";


    private final AppUserService appUserService;

    @Autowired
    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping(CREATE_USER)
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        AppUserEntity newUser = map(userDto);
        Optional<AppUserEntity> checkForEntity = appUserService.findUser(userDto.getUserName());
        if(checkForEntity.isEmpty()){
            AppUserEntity createdUser = appUserService.create(newUser);
            UserDto userToReturn = map(createdUser);
            return ok(userToReturn);
        } else {
            throw new EntityExistsException("Username already exists");
        }

    }

    private AppUserEntity map(UserDto userDto) {
        return AppUserEntity.builder()
                .userName(userDto.getUserName())
                .userPassword(userDto.getUserPassword())
                .build();
    }

    private UserDto map(AppUserEntity createdUser) {
        return UserDto.builder()
                .userName(createdUser.getUserName())
                .build();
    }
}
