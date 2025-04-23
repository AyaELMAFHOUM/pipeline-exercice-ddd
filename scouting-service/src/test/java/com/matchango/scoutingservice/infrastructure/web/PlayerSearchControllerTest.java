package com.matchango.scoutingservice.infrastructure.web;

import com.matchango.scoutingservice.domain.model.Joueur;
import com.matchango.scoutingservice.domain.model.Position;
import com.matchango.scoutingservice.domain.model.RapportDeScout;
import com.matchango.scoutingservice.domain.model.Scout;
import com.matchango.scoutingservice.domain.repositories.JoueurRepository;
import com.matchango.scoutingservice.domain.repositories.RapportDeScoutRepository;
import com.matchango.scoutingservice.domain.repositories.ScoutRepository;
import com.matchango.scoutingservice.infrastructure.web.dto.ApiResponse;
import com.matchango.scoutingservice.infrastructure.web.dto.CreateRapportDto;
import com.matchango.scoutingservice.infrastructure.web.dto.JoueurWithNoteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerSearchControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ScoutRepository scoutRepository;

    @Autowired
    private TestRestTemplate restTemplate;
    private String baseUrl;
    @Autowired
    private RapportDeScoutRepository rapportDeScoutRepository;
    @Autowired
    private JoueurRepository joueurRepository;


    @BeforeEach
    void setUp() {

        // Clean database and insert a Scout
        rapportDeScoutRepository.deleteAll();
        joueurRepository.deleteAll();
        scoutRepository.deleteAll();

        Joueur joueur = new Joueur();
        joueur.setNom("Joueur");
        joueur.setPrenom("Joueur");
        joueur.setAge(20);
        joueur.setPosition(Position.ATTAQUANT);
        joueurRepository.save(joueur);

        Scout scout = new Scout();
        scout.setUsername("houssam1337");
        scoutRepository.save(scout);

        RapportDeScout rapportDeScout = new RapportDeScout();
        rapportDeScout.setScout(scout);
        rapportDeScout.setMatch("IDK");
        rapportDeScout.setJoueur(joueur);
        rapportDeScoutRepository.save(rapportDeScout);
    }

    @Test
    void testSearchPlayersReturnsResults() {
        // Act: make a GET request to /player/search (assuming no filters yet)
        String url = "http://localhost:" + port + "/players/search";
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
        assertThat(response.getStatus()).isEqualTo("success");
        assertThat(response.getData()).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Players fetched successfully");

    }

}
