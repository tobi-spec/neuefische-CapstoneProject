package de.tobias.intestinalinspector.api;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FrontendFoodDto {

    String foodName;
    Date date;
}
