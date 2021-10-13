package de.tobias.intestinalinspector.api.pain;


import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PainMapDto implements Comparable<PainMapDto>{

    private String date;
    private List<PainDto> pains = new ArrayList<>();

    @Override
    public int compareTo(PainMapDto painMapDto){
        if(getDate() == null || painMapDto.getDate() == null){
            return 0;
        }
        return getDate().compareTo(painMapDto.getDate());
    }

    @Override
    public String toString() {
        return "PainMapDto{" +
                "date='" + date + '\'' +
                ", pains=" + pains +
                '}';
    }
}
