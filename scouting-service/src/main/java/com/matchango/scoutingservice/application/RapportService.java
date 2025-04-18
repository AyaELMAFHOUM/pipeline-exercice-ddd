package com.matchango.scoutingservice.application;

import com.matchango.scoutingservice.domain.model.*;
import com.matchango.scoutingservice.domain.repositories.JoueurRepository;
import com.matchango.scoutingservice.domain.repositories.RapportDeScoutRepository;
import com.matchango.scoutingservice.domain.repositories.ScoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RapportService {

    private final JoueurRepository joueurRepository;
    private final RapportDeScoutRepository rapportRepository;
    private final ScoutRepository scoutRepository;

    public void creerRapport(String nom, String prenom, Integer age, Position position,
                             String scoutUsername, String match, String observation, Integer note) {

        System.out.println("creerRapport");
        Optional<Scout> scoutOpt = scoutRepository.findByUsername(scoutUsername);
        if (scoutOpt.isEmpty()) {
            throw new IllegalArgumentException("Scout avec ce nom d'utilisateur non trouvé.");
        }

        Optional<Joueur> joueurOpt = joueurRepository.findByNomAndPrenom(nom, prenom);

        Joueur joueur;
        if (joueurOpt.isEmpty()) {
            if (position == null) {
                throw new IllegalArgumentException("Position non valide.");
            }
            if (scoutUsername == null) {
                throw new IllegalArgumentException("Scout username non valide.");
            }
            if (age == null || note == null || prenom == null || match == null) {
                throw new IllegalArgumentException("Le joueur n'existe pas. Merci de fournir l'âge, la position, et les autres données.");
            }


            joueur = new Joueur();
            joueur.setNom(nom);
            joueur.setPrenom(prenom);
            joueur.setAge(age);
            joueur.setPosition(position);
            joueur = joueurRepository.save(joueur);
        } else {
            if (note == null || prenom == null || nom == null || scoutUsername == null || match == null || observation == null) {
                throw new IllegalArgumentException("Tous les renseignements doivent être fournis : note, prénom, nom, scoutUsername, match, observation.");
            }
            joueur = joueurOpt.get();
        }

        RapportDeScout rapport = new RapportDeScout();
        rapport.setJoueur(joueur);
        long id = scoutOpt.get().getId();
        rapport.setScout(scoutOpt.get());
        rapport.setMatch(match);
        rapport.setObservation(observation);
        rapport.setNoteTechnique(note);

        try {
            rapportRepository.save(rapport);
        } catch (Exception e) {
            System.err.println("Error occurred while saving rapport: " + e.getMessage());
            throw new RuntimeException("An error occurred while creating the report.");
        }
    }
}