package com.example.servicedb.controller;

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
    @Autowired
    private FilmService filmService;

    @GetMapping
    @Operation(summary = "Get all films", description = "Retrieves a list of all films")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of films")
    })
    public List<Film> getAllFilms() {
        log.info("Fetching all films");
        List<Film> films = filmService.getAllFilms();
        log.debug("Retrieved {} films", films.size());
        return films;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get film by ID", description = "Retrieves a film by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved film"),
            @ApiResponse(responseCode = "404", description = "Film not found")
    })
    public ResponseEntity<Film> getFilmById(@Parameter(description = "ID of the film") @PathVariable Long id) {
        log.info("Fetching film with ID: {}", id);
        Optional<Film> film = filmService.getFilmById(id);
        if (film.isPresent()) {
            log.debug("Found film: {}", film.get().getTitle());
            return ResponseEntity.ok(film.get());
        } else {
            log.warn("Film with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new film", description = "Creates a new film")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film created successfully")
    })
    public Film createFilm(@RequestBody Film film) {
        log.info("Creating new film: {}", film.getTitle());
        Film createdFilm = filmService.createFilm(film);
        log.debug("Created film with ID: {}", createdFilm.getId());
        return createdFilm;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a film", description = "Updates an existing film by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film updated successfully"),
            @ApiResponse(responseCode = "404", description = "Film not found")
    })
    public ResponseEntity<Film> updateFilm(@Parameter(description = "ID of the film to update") @PathVariable Long id, @RequestBody Film film) {
        log.info("Updating film with ID: {}", id);
        Film updatedFilm = filmService.updateFilm(id, film);
        log.debug("Updated film: {}", updatedFilm.getTitle());
        return ResponseEntity.ok(updatedFilm);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a film", description = "Deletes a film by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Film deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Film not found")
    })
    public ResponseEntity<Void> deleteFilm(@Parameter(description = "ID of the film to delete") @PathVariable Long id) {
        log.info("Deleting film with ID: {}", id);
        filmService.deleteFilm(id);
        log.debug("Deleted film with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}