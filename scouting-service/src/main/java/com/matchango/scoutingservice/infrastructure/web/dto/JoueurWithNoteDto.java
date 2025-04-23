package com.matchango.scoutingservice.infrastructure.web.dto;
import com.matchango.scoutingservice.domain.model.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoueurWithNoteDto {
    private String nom;
    private String prenom;
    private Integer age;
    private Position position;
    private Double noteMoyenne;
}
