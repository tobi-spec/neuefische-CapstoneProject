package de.tobias.intestinalinspector.api;


import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FrontendPainListDto {

    List<FrontendPainDto> painList= new ArrayList();

    public void addPain(FrontendPainDto frontendPainDto){
        painList.add(frontendPainDto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrontendPainListDto that = (FrontendPainListDto) o;
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
