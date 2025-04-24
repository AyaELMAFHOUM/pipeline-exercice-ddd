package com.matchango.scoutingservice.application;

import com.matchango.scoutingservice.domain.model.*;
import com.matchango.scoutingservice.domain.repositories.JoueurRepository;
import com.matchango.scoutingservice.infrastructure.web.dto.JoueurWithNoteDto;
import com.matchango.scoutingservice.domain.repositories.RapportDeScoutRepository;
import com.matchango.scoutingservice.domain.repositories.ScoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RapportService {

    private final JoueurRepository joueurRepository;
    private final RapportDeScoutRepository rapportRepository;
    private final ScoutRepository scoutRepository;

    public void creerRapport(String firstName, String name, Integer age, Position position,
                             String scoutUsername, String match, String observation, Integer technicalRating) {

        System.out.println("creerRapport");
        Optional<Scout> scoutOpt = scoutRepository.findByUsername(scoutUsername);
        if (scoutOpt.isEmpty()) {
            throw new IllegalArgumentException("Scout avec ce username d'utilisateur non trouvé.");
        }

        Optional<Joueur> joueurOpt = joueurRepository.findByLastNameAndName(firstName, name);

        Joueur joueur;
        if (joueurOpt.isEmpty()) {
            if (scoutUsername == null) {
                throw new IllegalArgumentException("Scout username non valide.");
            }
            if (age == null || technicalRating == null || name == null || match == null || position == null) {
                throw new IllegalArgumentException("Le joueur n'existe pas. Merci de fournir l'âge, la position, et les autres données.");
            }


            joueur = new Joueur();
            joueur.setLastName(firstName);
            joueur.setName(name);
            joueur.setAge(age);
            joueur.setPosition(position);
            joueur = joueurRepository.save(joueur);
        } else {
            if (technicalRating == null || name == null || firstName == null || scoutUsername == null || match == null || observation == null) {
                throw new IllegalArgumentException("Tous les renseignements doivent être fournis : technicalRating, name, lastName, scoutUsername, match, observation.");
            }
            joueur = joueurOpt.get();
        }

        RapportDeScout rapport = new RapportDeScout();
        rapport.setJoueur(joueur);
        long id = scoutOpt.get().getId();
        rapport.setScout(scoutOpt.get());
        rapport.setMatch(match);
        rapport.setObservation(observation);
        rapport.setTechnicalRating(technicalRating);

        try {
            rapportRepository.save(rapport);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the report.");
        }
    }
    public List<JoueurWithNoteDto> chercherJoueursAvecFiltres(Integer age, String positionStr, Integer noteMin) {
        final Position position;

        if (positionStr != null) {
            try {
                position = Position.valueOf(positionStr.toUpperCase()); // Convert string to enum
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid position value: " + positionStr, e);
            }
        } else {
            position = null;
        }

        List<JoueurWithNoteDto> allPlayers = rapportRepository.findAllJoueursWithAvgNote();

        return allPlayers.stream()
                .filter(joueur ->
                        (age == null || joueur.getAge().equals(age)) &&
                                (position == null || joueur.getPosition() == position) &&
                                (noteMin == null || (joueur.getNoteMoyenne() != null && joueur.getNoteMoyenne() >= noteMin))
                )
                .collect(Collectors.toList());
    }
}