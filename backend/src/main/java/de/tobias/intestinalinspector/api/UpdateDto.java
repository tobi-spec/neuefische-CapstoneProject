package de.tobias.intestinalinspector.api;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDto {

    private String newName;
    private int newNumber;
}
