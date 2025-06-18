package com.example.servicedb.controller;

import com.example.servicedb.dto.FilmDTO;
import com.example.servicedb.model.Film;
import com.example.servicedb.service.FilmService;
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
@RequestMapping("/api/films")
@Slf4j
@Tag(name = "Film API", description = "Endpoints for managing films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    @Operation(summary = "Get all films", description = "Retrieves a list of all films")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of films")
    })
    public List<FilmDTO> getAllFilms() {
        log.info("Fetching all films");
        List<FilmDTO> films = filmService.getAllFilms();
        log.debug("Retrieved {} films", films.size());
        return films;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get film by ID", description = "Retrieves a film by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved film"),
            @ApiResponse(responseCode = "404", description = "Film not found")
    })
    public ResponseEntity<FilmDTO> getFilmById(@Parameter(description = "ID of the film") @PathVariable Long id) {
        log.info("Fetching film with ID: {}", id);
        Optional<FilmDTO> film = filmService.getFilmById(id);
        return film.map(filmDTO -> {
                    log.debug("Found film: {}", filmDTO.getTitle());
                    return ResponseEntity.ok(filmDTO);
                })
                .orElseGet(() -> {
                    log.warn("Film with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    @Operation(summary = "Create a new film", description = "Creates a new film with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film created successfully")
    })
    public FilmDTO createFilm(@Parameter(description = "Film details to create") @RequestBody Film film) {
        log.info("Creating new film: {}", film.getTitle());
        FilmDTO createdFilm = filmService.createFilm(film);
        log.debug("Created film with ID: {}", createdFilm.getId());
        return createdFilm;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a film", description = "Updates an existing film by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film updated successfully"),
            @ApiResponse(responseCode = "404", description = "Film not found")
    })
    public ResponseEntity<FilmDTO> updateFilm(
            @Parameter(description = "ID of the film to update") @PathVariable Long id,
            @Parameter(description = "Updated film details") @RequestBody Film filmDetails) {
        log.info("Updating film with ID: {}", id);
        try {
            FilmDTO updatedFilm = filmService.updateFilm(id, filmDetails);
            log.debug("Updated film: {}", updatedFilm.getTitle());
            return ResponseEntity.ok(updatedFilm);
        } catch (RuntimeException e) {
            log.error("Failed to update film with ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a film", description = "Deletes a film by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Film deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Film not found")
    })
    public ResponseEntity<Void> deleteFilm(@Parameter(description = "ID of the film to delete") @PathVariable Long id) {
        log.info("Deleting film with ID: {}", id);
        try {
            filmService.deleteFilm(id);
            log.debug("Deleted film with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Failed to delete film with ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}