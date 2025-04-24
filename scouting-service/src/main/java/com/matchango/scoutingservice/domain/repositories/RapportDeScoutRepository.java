package com.matchango.scoutingservice.domain.repositories;


import com.matchango.scoutingservice.domain.model.Report;
import com.matchango.scoutingservice.infrastructure.web.dto.PlayerWithRatingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RapportDeScoutRepository extends JpaRepository<Report, Long> {
    @Query("SELECT new com.matchango.scoutingservice.infrastructure.web.dto.PlayerWithRatingDto(" +
            "j.lastName, j.name, j.age, j.position, AVG(r.technicalRating)) " +
            "FROM Report r JOIN r.player j " +
            "GROUP BY j.id, j.lastName, j.name, j.age, j.position")
    List<PlayerWithRatingDto> findAllPlayersWithAvgNote();

}