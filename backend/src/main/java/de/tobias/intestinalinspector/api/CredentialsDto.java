package de.tobias.intestinalinspector.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CredentialsDto {

    private String userName;
    private String userPassword;
}
