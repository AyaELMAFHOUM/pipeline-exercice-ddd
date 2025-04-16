package com.matchango.scoutingservice.application;

import com.matchango.scoutingservice.domain.model.RapportDeScout;
import com.matchango.scoutingservice.domain.model.Joueur;
import com.matchango.scoutingservice.domain.model.NoteTechnique;
import com.matchango.scoutingservice.domain.repositories.RapportDeScoutRepository;
import com.matchango.scoutingservice.domain.repositories.JoueurRepository;
import com.matchango.scoutingservice.infrastructure.web.dto.CreateRapportDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RapportService {

    private final RapportDeScoutRepository rapportDeScoutRepository;
    private final JoueurRepository joueurRepository;

    public RapportService(RapportDeScoutRepository rapportDeScoutRepository, JoueurRepository joueurRepository) {
        this.rapportDeScoutRepository = rapportDeScoutRepository;
        this.joueurRepository = joueurRepository;
    }

    @Transactional
    public RapportDeScout ajouterRapport(CreateRapportDto createRapportDto) {
        Joueur joueur = joueurRepository.findById(UUID.fromString(createRapportDto.getJoueurId()))
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        RapportDeScout rapportDeScout = new RapportDeScout(
                joueur,
                createRapportDto.getNoteTechnique(),
                createRapportDto.getObservations(),
                createRapportDto.getMatchReference()
        );

        return rapportDeScoutRepository.save(rapportDeScout);
    }
}
