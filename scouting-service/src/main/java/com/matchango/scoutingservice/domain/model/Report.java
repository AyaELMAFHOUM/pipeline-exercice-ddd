package com.matchango.scoutingservice.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Scout scout;

    private int technicalRating;

    private String observation;

    private String match;
}
