package com.matchango.scoutingservice.infrastructure.web.dto;
import com.matchango.scoutingservice.domain.model.Position;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateRapportDto {

    private String nom;
    private String prenom;
    private Integer age;
    private String position;
    private String scoutUsername;
    private String match;
    private String observation;
    private int note;
}
