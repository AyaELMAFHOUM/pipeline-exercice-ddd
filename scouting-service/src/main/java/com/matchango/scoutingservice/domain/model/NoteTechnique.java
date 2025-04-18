package com.matchango.scoutingservice.domain.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoteTechnique {
    private int vitesse;
    private int passe;
    private int tir;

    public int getMoyenne() {
        return (vitesse + passe + tir) / 3;
    }
}
