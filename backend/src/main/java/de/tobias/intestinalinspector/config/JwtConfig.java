package de.tobias.intestinalinspector.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class JwtConfig {

    private String secret;
    private int expiresAfterMinutes;
}
