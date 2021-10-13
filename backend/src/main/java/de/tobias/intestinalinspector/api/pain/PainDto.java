package de.tobias.intestinalinspector.api.pain;

import de.tobias.intestinalinspector.api.BasicDtoInterface;
import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PainDto implements BasicDtoInterface {

    private int painLevel;
    private long id;
    private String date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PainDto painDto = (PainDto) o;
        return painLevel == painDto.painLevel && id == painDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(painLevel, id);
    }

    @Override
    public String toString() {
        return "PainDto{" +
                "painLevel=" + painLevel +
                ", id=" + id +
                ", date='" + date + '\'' +
                '}';
    }
}
