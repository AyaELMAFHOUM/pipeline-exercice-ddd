package com.matchango.scoutingservice.infrastructure.web;

import com.matchango.scoutingservice.domain.model.RapportDeScout;
import com.matchango.scoutingservice.infrastructure.web.dto.CreateRapportDto;
import com.matchango.scoutingservice.application.RapportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rapports")
public class RapportDeScoutController {

    private final RapportService rapportService;

    public RapportDeScoutController(RapportService rapportService) {
        this.rapportService = rapportService;
    }

    @PostMapping
    public ResponseEntity<?> ajouterRapport(@RequestBody CreateRapportDto createRapportDto) {
        try {
            RapportDeScout rapport = rapportService.ajouterRapport(createRapportDto);
            return new ResponseEntity<>(rapport, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating rapport: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
