package de.tobias.intestinalinspector.api;

import lombok.*;


@Data
@Builder
public class UserDto {

    private String userName;
    private String userPassword;

}
