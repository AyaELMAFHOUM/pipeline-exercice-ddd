package com.matchango.scoutingservice.infrastructure.web;

import com.matchango.scoutingservice.domain.model.Scout;
import com.matchango.scoutingservice.domain.repositories.JoueurRepository;
import com.matchango.scoutingservice.domain.repositories.RapportDeScoutRepository;
import com.matchango.scoutingservice.domain.repositories.ScoutRepository;
import com.matchango.scoutingservice.infrastructure.web.dto.CreateRapportDto;
import com.matchango.scoutingservice.infrastructure.web.dto.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RapportDeScoutControllerTest {

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

        Scout scout = new Scout();
        scout.setUsername("houssam1337");
        scoutRepository.save(scout);
    }

    @Test
    void testCreateReport() {
        // Prepare CreateRapportDto for the POST request
        CreateRapportDto createRapportDto = new CreateRapportDto();
        createRapportDto.setNom("Houssam");
        createRapportDto.setPrenom("Eddine");
        createRapportDto.setAge(22);
        createRapportDto.setPosition("ATTAQUANT");
        createRapportDto.setScoutUsername("houssam1337");
        createRapportDto.setMatch("Match 1");
        createRapportDto.setObservation("Good performance");
        createRapportDto.setNote(8);

        // Send POST request to /reports
        String url = "http://localhost:" + port + "/reports";
        ApiResponse response = restTemplate.postForObject(url, createRapportDto, ApiResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("success");
        assertThat(response.getMessage()).isEqualTo("Rapport créé avec succès.");
    }
    @Test
    void testCreateReportWithoutScout() {
        CreateRapportDto createRapportDto = new CreateRapportDto();
        createRapportDto.setNom("Anis");
        createRapportDto.setPrenom("Bmjk");
        createRapportDto.setAge(23);
        createRapportDto.setPosition("ATTAQUANT");
        createRapportDto.setScoutUsername("nonExistentScout");
        createRapportDto.setMatch("Match 1");
        createRapportDto.setObservation("Good performance");
        createRapportDto.setNote(8);

        // Send POST request to /reports
        String url = "http://localhost:" + port + "/reports";
        ApiResponse response = restTemplate.postForObject(url, createRapportDto, ApiResponse.class);

        // Assertions: Expecting a BAD_REQUEST response since the scout does not exist
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("error");
        assertThat(response.getMessage()).contains("Scout avec ce nom d'utilisateur non trouvé.");
    }
}
