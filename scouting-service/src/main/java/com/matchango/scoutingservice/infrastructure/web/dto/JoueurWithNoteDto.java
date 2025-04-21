package com.matchango.scoutingservice.infrastructure.web.dto;
import com.matchango.scoutingservice.domain.model.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoueurWithNoteDto {
    private String nom;
    private String prenom;
    private Integer age;
    private Position position;
    private Double noteMoyenne;
}
