package de.tobias.intestinalinspector.api;


import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PainMapDto {

    private String date;
    private List<PainDto> pains = new ArrayList<>();

    @Override
    public String toString() {
        return "PainMapDto{" +
                "date='" + date + '\'' +
                ", pains=" + pains +
                '}';
    }
}
