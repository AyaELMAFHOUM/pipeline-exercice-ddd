package com.matchango.scoutingservice.infrastructure.web.dto;

import com.matchango.scoutingservice.domain.model.NoteTechnique;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRapportDto {

    private String joueurId;
    private NoteTechnique noteTechnique;
    private String observations;
    private String matchReference;
}
