package de.tobias.intestinalinspector.api;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDto {

    private String newName;
    private int newNumber;
}
