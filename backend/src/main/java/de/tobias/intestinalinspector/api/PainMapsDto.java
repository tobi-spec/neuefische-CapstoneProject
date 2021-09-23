package de.tobias.intestinalinspector.api;

import io.swagger.annotations.ApiKeyAuthDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PainMapsDto {

    private List<PainMapDto> painMaps = new ArrayList<>();

    @Override
    public String toString() {
        return "PainMapsDto{" +
                "painMaps=" + painMaps +
                '}';
    }
}
