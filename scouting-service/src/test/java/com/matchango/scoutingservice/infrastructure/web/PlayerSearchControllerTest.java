package com.matchango.scoutingservice.infrastructure.web;

import com.matchango.scoutingservice.domain.model.Joueur;
import com.matchango.scoutingservice.domain.model.Position;
import com.matchango.scoutingservice.domain.model.RapportDeScout;
import com.matchango.scoutingservice.domain.model.Scout;
import com.matchango.scoutingservice.domain.repositories.JoueurRepository;
import com.matchango.scoutingservice.domain.repositories.RapportDeScoutRepository;
import com.matchango.scoutingservice.domain.repositories.ScoutRepository;
import com.matchango.scoutingservice.infrastructure.web.dto.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerSearchControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ScoutRepository scoutRepository;

    @Autowired
    private TestRestTemplate restTemplate;
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

        Joueur joueur_0 = new Joueur();
        joueur_0.setNom("Haddouche");
        joueur_0.setPrenom("Houssam eddine");
        joueur_0.setAge(20);
        joueur_0.setPosition(Position.ATTAQUANT);
        joueurRepository.save(joueur_0);

        Joueur joueur_1 = new Joueur();
        joueur_1.setNom("idk");
        joueur_1.setPrenom("Anis");
        joueur_1.setAge(27);
        joueur_1.setPosition(Position.MILIEU);
        joueurRepository.save(joueur_1);

        Scout scout = new Scout();
        scout.setUsername("houssam1337");
        scoutRepository.save(scout);

        RapportDeScout rapportDeScout_0 = new RapportDeScout();
        rapportDeScout_0.setScout(scout);
        rapportDeScout_0.setMatch("PSG VS NM");
        rapportDeScout_0.setNoteTechnique(50);
        rapportDeScout_0.setJoueur(joueur_0);
        rapportDeScoutRepository.save(rapportDeScout_0);

        RapportDeScout rapportDeScout_1 = new RapportDeScout();
        rapportDeScout_1.setScout(scout);
        rapportDeScout_1.setMatch("PSG VS MU");
        rapportDeScout_1.setJoueur(joueur_1);
        rapportDeScoutRepository.save(rapportDeScout_1);
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

    @Test
    void testSearchPlayersByAge() {
        String expectedNom = "Haddouche";
        String expectedPrenom = "Houssam eddine";
        int expectedAge = 20;
        String expectedPosition = "ATTAQUANT";
        double expectedNote = 50.0;
        String url = "http://localhost:" + port + "/players/search?age=20";
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
        assertThat(response.getStatus()).isEqualTo("success");
        assertThat(response.getMessage()).isEqualTo("Players fetched successfully");
        assertThat(response.getData()).isNotNull();
        List<?> players = (List<?>) response.getData();
        assertThat(players).isNotEmpty();
        Map<?, ?> actualPlayer = (Map<?, ?>) players.get(0);
        assertThat(actualPlayer.get("nom")).isEqualTo(expectedNom);
        assertThat(actualPlayer.get("prenom")).isEqualTo(expectedPrenom);
        assertThat(actualPlayer.get("age")).isEqualTo(expectedAge);
        assertThat(actualPlayer.get("position")).isEqualTo(expectedPosition);
        assertThat(actualPlayer.get("noteMoyenne")).isEqualTo(expectedNote);
    }

}
