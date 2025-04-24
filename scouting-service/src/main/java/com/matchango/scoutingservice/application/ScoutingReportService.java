package com.matchango.scoutingservice.application;

import com.matchango.scoutingservice.domain.model.*;
import com.matchango.scoutingservice.domain.repositories.PlayerRepository;
import com.matchango.scoutingservice.infrastructure.web.dto.PlayerWithRatingDto;
import com.matchango.scoutingservice.domain.repositories.ScoutingReportRepository;
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
public class ScoutingReportService {

    private final PlayerRepository playerRepository;
    private final ScoutingReportRepository scoutingReportRepository;
    private final ScoutRepository scoutRepository;

    public void createReport(String firstName, String name, Integer age, Position position,
                             String scoutUsername, String match, String observation, Double technicalRating) {

        Optional<Scout> scoutOpt = scoutRepository.findByUsername(scoutUsername);
        if (scoutOpt.isEmpty()) {
            throw new IllegalArgumentException("Scout with this username was not found.");
        }

        Optional<Player> playerOpt = playerRepository.findByLastNameAndFirstName(firstName, name);

        Player player;
        if (playerOpt.isEmpty()) {
            if (scoutUsername == null) {
                throw new IllegalArgumentException("Invalid scout username.");
            }
            if (age == null || technicalRating == null || name == null || match == null || position == null) {
                throw new IllegalArgumentException("Player does not exist. Please provide age, position, and other required data.");
            }


            player = new Player();
            player.setLastName(firstName);
            player.setFirstName(name);
            player.setAge(age);
            player.setPosition(position);
            player = playerRepository.save(player);
        } else {
            if (technicalRating == null || name == null || firstName == null || scoutUsername == null || match == null || observation == null) {
                throw new IllegalArgumentException("All information must be provided: technicalRating, firstName, lastName, scoutUsername, match, observation");
            }
            player = playerOpt.get();
        }

        Report rapport = new Report();
        rapport.setPlayer(player);
        long id = scoutOpt.get().getId();
        rapport.setScout(scoutOpt.get());
        rapport.setMatch(match);
        rapport.setObservation(observation);
        rapport.setTechnicalRating(technicalRating);

        try {
            scoutingReportRepository.save(rapport);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while generating the report.");
        }
    }
    public List<PlayerWithRatingDto> findPlayersWithFiltres(Integer age, String positionStr, Double minRating) {
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

        List<PlayerWithRatingDto> allPlayers = scoutingReportRepository.findAllPlayersWithAvgRating();

        return allPlayers.stream()
                .filter(player ->
                        (age == null || player.getAge().equals(age)) &&
                                (position == null || player.getPosition() == position) &&
                                (minRating == null || (player.getAverageRating() != null && player.getAverageRating() >= minRating))
                )
                .collect(Collectors.toList());
    }
}