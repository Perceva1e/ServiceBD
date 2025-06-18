package com.example.servicedb.service;

import com.example.servicedb.model.Genre;
import com.example.servicedb.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {
    private final GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        log.info("Fetching all genres from repository");
        List<Genre> genres = genreRepository.findAll();
        log.debug("Retrieved {} genres", genres.size());
        return genres;
    }

    public Optional<Genre> getGenreById(Long id) {
        log.info("Fetching genre with ID: {}", id);
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            log.debug("Found genre: {}", genre.get().getName());
        } else {
            log.warn("Genre with ID {} not found", id);
        }
        return genre;
    }

    public Genre createGenre(Genre genre) {
        log.info("Creating genre: {}", genre.getName());
        Genre savedGenre = genreRepository.save(genre);
        log.debug("Created genre with ID: {}", savedGenre.getId());
        return savedGenre;
    }

    public Genre updateGenre(Long id, Genre genreDetails) {
        log.info("Updating genre with ID: {}", id);
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Genre with ID {} not found for update", id);
                    return new RuntimeException("Genre not found with ID: " + id);
                });
        genre.setName(genreDetails.getName());
        genre.setDescription(genreDetails.getDescription());
        Genre updatedGenre = genreRepository.save(genre);
        log.debug("Updated genre: {}", updatedGenre.getName());
        return updatedGenre;
    }

    public void deleteGenre(Long id) {
        log.info("Deleting genre with ID: {}", id);
        if (!genreRepository.existsById(id)) {
            log.warn("Genre with ID {} not found for deletion", id);
            throw new RuntimeException("Genre not found with ID: " + id);
        }
        genreRepository.deleteById(id);
        log.debug("Deleted genre with ID: {}", id);
    }
}