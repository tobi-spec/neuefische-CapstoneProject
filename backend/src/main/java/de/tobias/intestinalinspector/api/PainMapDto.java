package de.tobias.intestinalinspector.api;


import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PainMapDto {

    Map<String, List<PainDto>> painMap= new HashMap<>();

    public void putAll(Map<String,List<PainDto>> map){
        painMap.putAll(map);
    }


}
