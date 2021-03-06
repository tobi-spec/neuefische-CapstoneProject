package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.AccessTokenDto;
import de.tobias.intestinalinspector.api.AppUserDto;
import de.tobias.intestinalinspector.api.UserDto;
import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.service.AppUserService;
import de.tobias.intestinalinspector.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin
public class LoginController {

    public static final String ACCESS_TOKEN_URL = "/auth/access_token";

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AppUserService appUserService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtService jwtService, AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.appUserService = appUserService;
    }

    @GetMapping("auth/me")
    public ResponseEntity<AppUserDto> getLoggedInUser(@AuthenticationPrincipal AppUserEntity appUser){
        return ok(AppUserDto.builder()
                .userName(appUser.getUserName())
                .userRole(appUser.getUserRole())
                .build());
    }

    @PostMapping(ACCESS_TOKEN_URL)
    public ResponseEntity<AccessTokenDto> getAccessToken(@RequestBody UserDto userDto){
        String username = userDto.getUserName();
        String password = userDto.getUserPassword();

        if(username == null || username.isEmpty()){
            throw new IllegalArgumentException("username must not be empty");
        }

        if(password == null || password.isEmpty()){
            throw new IllegalArgumentException("password must not be empty");
        }


        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        try{
            authenticationManager.authenticate(authToken);

            AppUserEntity appUser = appUserService.findUser(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            String token = jwtService.createJwtToken(appUser);

            AccessTokenDto accessToken = AccessTokenDto.builder()
                    .token(token)
                    .build();

            return ok(accessToken);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
    }
}
