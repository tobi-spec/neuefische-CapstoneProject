package de.tobias.intestinalinspector.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class AccessTokenDto {

    private String token;
}
