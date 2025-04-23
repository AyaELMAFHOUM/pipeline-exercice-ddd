package com.matchango.scoutingservice.domain.repositories;

import com.matchango.scoutingservice.domain.model.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JoueurRepository extends JpaRepository<Joueur, Long> {
    Optional<Joueur> findByLastNameAndName(String lastName, String name);

}
