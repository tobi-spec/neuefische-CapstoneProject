package de.tobias.intestinalinspector.api;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PainDto {

    private int painLevel;
    private long id;
    private String date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PainDto painDto = (PainDto) o;
        return painLevel == painDto.painLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(painLevel);
    }
}
