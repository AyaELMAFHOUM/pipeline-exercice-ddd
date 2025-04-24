package com.matchango.scoutingservice.infrastructure.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateScoutingReportDto {

    @NotBlank(message = "Last name is required.")
    @Size(min = 2, max = 15, message = "Last name must be between 2 and 15 characters.")
    private String lastName;

    @NotBlank(message = "First name is required.")
    @Size(min = 2, max = 15, message = "First name must be between 2 and 15 characters.")
    private String firstName;

    @Min(value = 0, message = "Age must be at least 0.")
    @Max(value = 50, message = "Age must be 50 or less.")
    private Integer age;

    private String position; // You can validate this in the controller or service as enum

    @NotBlank(message = "Scout username is required.")
    private String scoutUsername;

    @NotBlank(message = "Match is required.")
    private String match;

    @NotBlank(message = "Observation is required.")
    private String observation;

    @NotNull(message = "Technical rating is required.")
    @Min(value = 0, message = "Technical rating must be at least 0.")
    @Max(value = 100, message = "Technical rating must be 100 or less.")
    private Double technicalRating;
}