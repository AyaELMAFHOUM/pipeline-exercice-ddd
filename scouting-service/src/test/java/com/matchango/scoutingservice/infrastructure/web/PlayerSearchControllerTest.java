package com.matchango.scoutingservice.infrastructure.web;

import com.matchango.scoutingservice.domain.model.Player;
import com.matchango.scoutingservice.domain.model.Position;
import com.matchango.scoutingservice.domain.model.Report;
import com.matchango.scoutingservice.domain.model.Scout;
import com.matchango.scoutingservice.domain.repositories.PlayerRepository;
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
    private PlayerRepository joueurRepository;


    @BeforeEach
    void setUp() {

        // Clean database and insert a Scout
        rapportDeScoutRepository.deleteAll();
        joueurRepository.deleteAll();
        scoutRepository.deleteAll();

        Player player_0 = new Player();
        player_0.setLastName("Haddouche");
        player_0.setName("Houssam eddine");
        player_0.setAge(20);
        player_0.setPosition(Position.ATTAQUANT);
        joueurRepository.save(player_0);

        Player player_1 = new Player();
        player_1.setLastName("idk");
        player_1.setName("Anis");
        player_1.setAge(27);
        player_1.setPosition(Position.MILIEU);
        joueurRepository.save(player_1);

        Scout scout = new Scout();
        scout.setUsername("houssam1337");
        scoutRepository.save(scout);

        Report report_0 = new Report();
        report_0.setScout(scout);
        report_0.setMatch("PSG VS NM");
        report_0.setTechnicalRating(50);
        report_0.setPlayer(player_0);
        rapportDeScoutRepository.save(report_0);

        Report report_1 = new Report();
        report_1.setScout(scout);
        report_1.setMatch("PSG VS MU");
        report_1.setPlayer(player_1);
        rapportDeScoutRepository.save(report_1);
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
        String expectedLastName = "Haddouche";
        String expectedname = "Houssam eddine";
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
        assertThat(actualPlayer.get("firstName")).isEqualTo(expectedLastName);
        assertThat(actualPlayer.get("name")).isEqualTo(expectedname);
        assertThat(actualPlayer.get("age")).isEqualTo(expectedAge);
        assertThat(actualPlayer.get("position")).isEqualTo(expectedPosition);
        assertThat(actualPlayer.get("averageRating")).isEqualTo(expectedNote);
    }

}
