package de.tobias.intestinalinspector.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppUserDto {

    private String userName;
    private String userRole;
}
