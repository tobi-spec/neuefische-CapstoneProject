package de.tobias.intestinalinspector.api;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodUpdateDto {

    private String newValue;
}
