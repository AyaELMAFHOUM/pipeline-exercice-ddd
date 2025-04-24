package com.matchango.scoutingservice.infrastructure.web.dto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateScoutingReportDto {

    private String lastName;
    private String firstName;
    private Integer age;
    private String position;
    private String scoutUsername;
    private String match;
    private String observation;
    private Integer technicalRating;
}
