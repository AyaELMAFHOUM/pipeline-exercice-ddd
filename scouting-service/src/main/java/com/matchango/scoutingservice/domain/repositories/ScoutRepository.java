package com.matchango.scoutingservice.domain.repositories;

import com.matchango.scoutingservice.domain.model.Scout;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ScoutRepository extends JpaRepository<Scout, Long> {
    Optional<Scout> findByUsername(String username);
}