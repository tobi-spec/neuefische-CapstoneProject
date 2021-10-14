package de.tobias.intestinalinspector.api.pain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PainMapsDto {

    private List<PainMapDto> painMaps = new ArrayList<>();

    @Override
    public String toString() {
        return "PainMapsDto{" +
                "painMaps=" + painMaps +
                '}';
    }
}
