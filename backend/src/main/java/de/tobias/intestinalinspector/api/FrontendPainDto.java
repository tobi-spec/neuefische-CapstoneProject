package de.tobias.intestinalinspector.api;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrontendPainDto {

    private int painLevel;
    private long id;
    private String date;


}
