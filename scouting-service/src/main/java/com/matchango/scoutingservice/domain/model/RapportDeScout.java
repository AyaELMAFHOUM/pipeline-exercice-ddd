package com.matchango.scoutingservice.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class RapportDeScout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Joueur joueur;

    @ManyToOne
    private Scout scout;

    private int technicalRating;

    private String observation;

    private String match;
}
