package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.AccessTokenDto;
import de.tobias.intestinalinspector.api.AppUserDto;
import de.tobias.intestinalinspector.api.CredentialsDto;
import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.service.AppUserService;
import de.tobias.intestinalinspector.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin
public class LoginController {

    public static final String ACCESS_TOKEN_URL = "/auth/access_token";

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AppUserService appUserService;

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
    public ResponseEntity<AccessTokenDto> getAccessToken(@RequestBody CredentialsDto credentials) {
        String username = credentials.getUserName();
        String password = credentials.getUserPassword();

        if(username == null || username.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(password == null || password.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        try{
            authenticationManager.authenticate(authToken);

            AppUserEntity appUser = appUserService.findUser(username).orElseThrow();
            String token = jwtService.createJwtToken(appUser);

            AccessTokenDto accessToken = AccessTokenDto.builder()
                    .token(token)
                    .build();

            return ok(accessToken);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
