package de.tobias.intestinalinspector.api;


import lombok.*;

@Data
@Builder
public class UpdateDto {

    private String newName;
    private int newNumber;
}
