package de.tobias.intestinalinspector.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessTokenDto {

    private String token;
}
