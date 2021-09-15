package de.tobias.intestinalinspector.api;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDto {

    private long id;
    private String newName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateDto updateDto = (UpdateDto) o;
        return id == updateDto.id && Objects.equals(newName, updateDto.newName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, newName);
    }

    @Override
    public String toString() {
        return "UpdateDto{" +
                "id=" + id +
                ", newName='" + newName + '\'' +
                '}';
    }
}
