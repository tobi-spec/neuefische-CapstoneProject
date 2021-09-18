package de.tobias.intestinalinspector.api;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userName;
    private String userPassword;

}
