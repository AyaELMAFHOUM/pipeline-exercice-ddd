package com.matchango.scoutingservice.domain.repositories;

import com.matchango.scoutingservice.domain.model.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface JoueurRepository extends JpaRepository<Joueur, UUID> {
    List<Joueur> findByAgeBetweenAndPosition(int minAge, int maxAge, String position);}
