package de.tobias.intestinalinspector;

import de.tobias.intestinalinspector.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TestAuthorization {

    JwtConfig jwtConfig;

    @Autowired
    public TestAuthorization(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public HttpHeaders Header(String username, String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        Instant now = Instant.now();
        Date iat = Date.from(now);
        Date exp = Date.from(now.plus(Duration.ofMinutes(jwtConfig.getExpiresAfterMinutes())));
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(iat)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();

        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(token);

        return header;
    }
}
