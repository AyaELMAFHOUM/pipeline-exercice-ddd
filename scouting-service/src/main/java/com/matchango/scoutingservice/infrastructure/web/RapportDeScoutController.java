package com.matchango.scoutingservice.infrastructure.web;

import com.matchango.scoutingservice.application.RapportService;
import com.matchango.scoutingservice.domain.model.Position;
import com.matchango.scoutingservice.infrastructure.web.dto.JoueurWithNoteDto;
import com.matchango.scoutingservice.infrastructure.web.dto.ApiResponse;
import com.matchango.scoutingservice.infrastructure.web.dto.CreateRapportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class RapportDeScoutController {

    private final RapportService rapportService;

    @PostMapping("/reports")
    public ResponseEntity<ApiResponse> createReport(@RequestBody CreateRapportDto createRapportDto) {
        try {
            // TODO: Ref - move this check and throw error to Service
            String positionString = createRapportDto.getPosition();
            Position position;
            if (positionString != null) {
                positionString = positionString.toUpperCase();

                try {
                    position = Position.valueOf(positionString);
                } catch (IllegalArgumentException e) {
                    position = null;
                }
            }
            else {
                position = null;
            }

            rapportService.creerRapport(
                    createRapportDto.getLastName(),
                    createRapportDto.getName(),
                    createRapportDto.getAge(),
                    position,
                    createRapportDto.getScoutUsername(),
                    createRapportDto.getMatch(),
                    createRapportDto.getObservation(),
                    createRapportDto.getNote()
            );

            ApiResponse response = ApiResponse.builder()
                    .status("success")
                    .message("Rapport créé avec succès.")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ApiResponse response = ApiResponse.builder()
                    .status("error")
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/players/search")
    public ResponseEntity<ApiResponse> searchPlayers(
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) Integer noteMin
    ) {
        List<JoueurWithNoteDto> result = rapportService.chercherJoueursAvecFiltres(age, position, noteMin);
        ApiResponse response = ApiResponse.builder()
                .status("success")
                .message("Players fetched successfully")
                .data(result)
                .build();
        return ResponseEntity.ok(response);
    }

}
