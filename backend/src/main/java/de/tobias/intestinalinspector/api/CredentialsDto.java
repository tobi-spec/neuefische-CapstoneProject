package de.tobias.intestinalinspector.api;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialsDto {

    private String userName;
    private String userPassword;
}
