package com.example.servicedb.controller;

import com.example.servicedb.model.FilmData;
import com.example.servicedb.service.FilmDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/filmdata")
@Slf4j
@Tag(name = "FilmData API", description = "Endpoints for managing film data")
public class FilmDataController {
    @Autowired
    private FilmDataService filmDataService;

    @GetMapping
    @Operation(summary = "Get all film data", description = "Retrieves a list of all film data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of film data")
    })
    public List<FilmData> getAllFilmData() {
        log.info("Fetching all film data");
        List<FilmData> filmDataList = filmDataService.getAllFilmData();
        log.debug("Retrieved {} film data entries", filmDataList.size());
        return filmDataList;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get film data by ID", description = "Retrieves film data by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved film data"),
            @ApiResponse(responseCode = "404", description = "Film data not found")
    })
    public ResponseEntity<FilmData> getFilmDataById(@Parameter(description = "ID of the film data") @PathVariable Long id) {
        log.info("Fetching film data with ID: {}", id);
        Optional<FilmData> filmData = filmDataService.getFilmDataById(id);
        if (filmData.isPresent()) {
            log.debug("Found film data with ID: {}", id);
            return ResponseEntity.ok(filmData.get());
        } else {
            log.warn("Film data with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create new film data", description = "Creates a new film data entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film data created successfully")
    })
    public FilmData createFilmData(@RequestBody FilmData filmData) {
        log.info("Creating new film data");
        FilmData createdFilmData = filmDataService.createFilmData(filmData);
        log.debug("Created film data with ID: {}", createdFilmData.getId());
        return createdFilmData;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update film data", description = "Updates an existing film data by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film data updated successfully"),
            @ApiResponse(responseCode = "404", description = "Film data not found")
    })
    public ResponseEntity<FilmData> updateFilmData(@Parameter(description = "ID of the film data to update") @PathVariable Long id, @RequestBody FilmData filmData) {
        log.info("Updating film data with ID: {}", id);
        FilmData updatedFilmData = filmDataService.updateFilmData(id, filmData);
        log.debug("Updated film data with ID: {}", updatedFilmData.getId());
        return ResponseEntity.ok(updatedFilmData);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete film data", description = "Deletes a film data entry by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Film data deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Film data not found")
    })
    public ResponseEntity<Void> deleteFilmData(@Parameter(description = "ID of the film data to delete") @PathVariable Long id) {
        log.info("Deleting film data with ID: {}", id);
        filmDataService.deleteFilmData(id);
        log.debug("Deleted film data with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}