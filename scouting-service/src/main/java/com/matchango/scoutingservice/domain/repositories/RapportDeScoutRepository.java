package com.matchango.scoutingservice.domain.repositories;


import com.matchango.scoutingservice.domain.model.RapportDeScout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RapportDeScoutRepository extends JpaRepository<RapportDeScout, Long> {
}