package de.tobias.intestinalinspector.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CredentialsDto {

    private String userName;
    private String userPassword;
}
