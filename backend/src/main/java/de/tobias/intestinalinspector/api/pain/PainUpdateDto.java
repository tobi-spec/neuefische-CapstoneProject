package de.tobias.intestinalinspector.api.pain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PainUpdateDto {

    private int newValue;
}
