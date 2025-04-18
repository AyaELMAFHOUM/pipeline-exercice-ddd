package com.matchango.scoutingservice.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RapportDeScout {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private Joueur joueur;

    @Embedded
    private NoteTechnique noteTechnique;

    private String observations;
    private String matchReference;

    public RapportDeScout(Joueur joueur, NoteTechnique noteTechnique, String observations, String matchReference) {
        this(UUID.randomUUID(), joueur, noteTechnique, observations, matchReference);
    }
}
