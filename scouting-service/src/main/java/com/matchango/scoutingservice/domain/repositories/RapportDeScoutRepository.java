package com.matchango.scoutingservice.domain.repositories;


import com.matchango.scoutingservice.domain.model.RapportDeScout;
import com.matchango.scoutingservice.infrastructure.web.dto.JoueurWithNoteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RapportDeScoutRepository extends JpaRepository<RapportDeScout, Long> {
    @Query("SELECT new com.matchango.scoutingservice.infrastructure.web.dto.JoueurWithNoteDto(" +
            "j.lastName, j.name, j.age, j.position, AVG(r.technicalRating)) " +
            "FROM RapportDeScout r JOIN r.joueur j " +
            "GROUP BY j.id, j.lastName, j.name, j.age, j.position")
    List<JoueurWithNoteDto> findAllJoueursWithAvgNote();

}