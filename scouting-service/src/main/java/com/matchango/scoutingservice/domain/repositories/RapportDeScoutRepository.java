package com.matchango.scoutingservice.domain.repositories;

import com.matchango.scoutingservice.domain.model.RapportDeScout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RapportDeScoutRepository extends JpaRepository<RapportDeScout, UUID> {
}
