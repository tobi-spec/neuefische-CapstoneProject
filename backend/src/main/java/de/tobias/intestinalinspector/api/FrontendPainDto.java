package de.tobias.intestinalinspector.api;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrontendPainDto {

    int painLevel;
    long id;
    String date;


}
