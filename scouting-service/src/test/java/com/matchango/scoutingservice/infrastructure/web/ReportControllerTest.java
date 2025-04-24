package com.matchango.scoutingservice.infrastructure.web;

import com.matchango.scoutingservice.domain.model.Player;
import com.matchango.scoutingservice.domain.model.Scout;
import com.matchango.scoutingservice.domain.repositories.PlayerRepository;
import com.matchango.scoutingservice.domain.repositories.ScoutingReportRepository;
import com.matchango.scoutingservice.domain.repositories.ScoutRepository;
import com.matchango.scoutingservice.infrastructure.web.dto.CreateScoutingReportDto;
import com.matchango.scoutingservice.infrastructure.web.dto.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ScoutRepository scoutRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;
    @Autowired
    private ScoutingReportRepository scoutingReportRepository;
    @Autowired
    private PlayerRepository joueurRepository;

    @BeforeEach
    void setUp() {

        // Clean database and insert a Scout
        scoutingReportRepository.deleteAll();
        joueurRepository.deleteAll();
        scoutRepository.deleteAll();

        Scout scout = new Scout();
        scout.setUsername("houssam1337");
        scoutRepository.save(scout);
    }
    void createPlayer(){
        Player player = new Player();
        player.setLastName("ExistedPlayer");
        player.setFirstName("Player");
        joueurRepository.save(player);
    }

    @Test
    void testCreateReport() {
        // Prepare CreateRapportDto for the POST request
        CreateScoutingReportDto createScoutingReportDto = new CreateScoutingReportDto();
        createScoutingReportDto.setLastName("Houssam");
        createScoutingReportDto.setFirstName("Eddine");
        createScoutingReportDto.setAge(22);
        createScoutingReportDto.setPosition("FORWARD");
        createScoutingReportDto.setScoutUsername("houssam1337");
        createScoutingReportDto.setMatch("Match 1");
        createScoutingReportDto.setObservation("Good performance");
        createScoutingReportDto.setTechnicalRating(8);

        // Send POST request to /reports
        String url = "http://localhost:" + port + "/reports";
        ApiResponse response = restTemplate.postForObject(url, createScoutingReportDto, ApiResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("success");
        assertThat(response.getMessage()).isEqualTo("Rapport créé avec succès.");
    }
    @Test
    void testCreateReportWithoutScout() {
        CreateScoutingReportDto createScoutingReportDto = new CreateScoutingReportDto();
        createScoutingReportDto.setLastName("Anis");
        createScoutingReportDto.setFirstName("Bmjk");
        createScoutingReportDto.setAge(23);
        createScoutingReportDto.setPosition("FORWARD");
        createScoutingReportDto.setScoutUsername("nonExistentScout");
        createScoutingReportDto.setMatch("Match 1");
        createScoutingReportDto.setObservation("Good performance");
        createScoutingReportDto.setTechnicalRating(8);

        // Send POST request to /reports
        String url = "http://localhost:" + port + "/reports";
        ApiResponse response = restTemplate.postForObject(url, createScoutingReportDto, ApiResponse.class);

        // Assertions: Expecting a BAD_REQUEST response since the scout does not exist
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("error");
        assertThat(response.getMessage()).contains("Scout avec ce username d'utilisateur non trouvé.");
    }
    @Test
    void testCreateReportWithMissingFields() {
        // Add player to the TestDB first
        this.createPlayer();
        // Create a CreateRapportDto with missing 'technicalRating'
        CreateScoutingReportDto createScoutingReportDto = new CreateScoutingReportDto();
        createScoutingReportDto.setLastName("ExistedPlayer");
        createScoutingReportDto.setFirstName("Player");
        createScoutingReportDto.setAge(22);
        createScoutingReportDto.setPosition("FORWARD");
        createScoutingReportDto.setScoutUsername("houssam1337");
        createScoutingReportDto.setMatch("Match 1");
        createScoutingReportDto.setObservation("Good performance");
        // 'technicalRating' is not set

        // Send POST request to /reports
        String url = "http://localhost:" + port + "/reports";
        ApiResponse response = restTemplate.postForObject(url, createScoutingReportDto, ApiResponse.class);
        System.out.println(response);

        // Assertions: Expecting a BAD_REQUEST response since 'technicalRating' is missing
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("error");
        assertThat(response.getMessage()).contains("Tous les renseignements doivent être fournis : technicalRating, name, lastName, scoutUsername, match, observation");
    }
    @Test
    void testCreateRapportWithoutPlayer() {
        CreateScoutingReportDto createScoutingReportDto = new CreateScoutingReportDto();
        createScoutingReportDto.setLastName("NewPlayer");
        createScoutingReportDto.setFirstName("Player");
        createScoutingReportDto.setScoutUsername("houssam1337");
        createScoutingReportDto.setMatch("Match 1");
        createScoutingReportDto.setObservation("Good performance");
        createScoutingReportDto.setTechnicalRating(9);
        String url = "http://localhost:" + port + "/reports";
        ApiResponse response = restTemplate.postForObject(url, createScoutingReportDto, ApiResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("error");
        assertThat(response.getMessage()).isEqualTo("player n'existe pas. Merci de fournir l'âge, la position, et les autres données.");
    }
}
