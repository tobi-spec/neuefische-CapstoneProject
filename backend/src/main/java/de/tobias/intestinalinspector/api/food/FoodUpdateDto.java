package de.tobias.intestinalinspector.api.food;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodUpdateDto {

    private String newValue;
}
