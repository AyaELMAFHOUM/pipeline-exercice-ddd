package com.matchango.scoutingservice.domain.repositories;

import com.matchango.scoutingservice.domain.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JoueurRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByLastNameAndName(String lastName, String name);

}
