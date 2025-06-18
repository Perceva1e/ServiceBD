package com.example.servicedb.controller;

import com.example.servicedb.model.Genre;
import com.example.servicedb.service.GenreService;
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
@RequestMapping("/api/genres")
@Slf4j
@Tag(name = "Genre API", description = "Endpoints for managing genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping
    @Operation(summary = "Get all genres", description = "Retrieves a list of all genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of genres")
    })
    public List<Genre> getAllGenres() {
        log.info("Fetching all genres");
        List<Genre> genres = genreService.getAllGenres();
        log.debug("Retrieved {} genres", genres.size());
        return genres;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get genre by ID", description = "Retrieves a genre by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved genre"),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    public ResponseEntity<Genre> getGenreById(@Parameter(description = "ID of the genre") @PathVariable Long id) {
        log.info("Fetching genre with ID: {}", id);
        Optional<Genre> genre = genreService.getGenreById(id);
        if (genre.isPresent()) {
            log.debug("Found genre: {}", genre.get().getName());
            return ResponseEntity.ok(genre.get());
        } else {
            log.warn("Genre with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new genre", description = "Creates a new genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre created successfully")
    })
    public Genre createGenre(@RequestBody Genre genre) {
        log.info("Creating new genre: {}", genre.getName());
        Genre createdGenre = genreService.createGenre(genre);
        log.debug("Created genre with ID: {}", createdGenre.getId());
        return createdGenre;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a genre", description = "Updates an existing genre by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre updated successfully"),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    public ResponseEntity<Genre> updateGenre(@Parameter(description = "ID of the genre to update") @PathVariable Long id, @RequestBody Genre genre) {
        log.info("Updating genre with ID: {}", id);
        Genre updatedGenre = genreService.updateGenre(id, genre);
        log.debug("Updated genre: {}", updatedGenre.getName());
        return ResponseEntity.ok(updatedGenre);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a genre", description = "Deletes a genre by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Genre deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    public ResponseEntity<Void> deleteGenre(@Parameter(description = "ID of the genre to delete") @PathVariable Long id) {
        log.info("Deleting genre with ID: {}", id);
        genreService.deleteGenre(id);
        log.debug("Deleted genre with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}