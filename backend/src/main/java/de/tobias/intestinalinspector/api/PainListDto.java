package de.tobias.intestinalinspector.api;


import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PainListDto {

    List<PainDto> painList= new ArrayList<>();

    public void addPain(PainDto painDto){
        painList.add(painDto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PainListDto that = (PainListDto) o;
        return Objects.equals(painList, that.painList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(painList);
    }

    @Override
    public String toString() {
        return "FrontendPainListDto{" +
                "painList=" + painList +
                '}';
    }
}
