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
public class Joueur {

    @Id
    private UUID id = UUID.randomUUID();

    private String nom;
    private int age;
    private String position;

    public Joueur(String nom, int age, String position) {
        this(UUID.randomUUID(), nom, age, position);
    }
}
