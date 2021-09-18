package de.tobias.intestinalinspector.api;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userName;
    private String userPassword;

}
