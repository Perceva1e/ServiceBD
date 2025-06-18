package com.example.servicedb.service;

import com.example.servicedb.model.Film;
import com.example.servicedb.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    private final FilmRepository filmRepository;

    public List<Film> getAllFilms() {
        log.info("Fetching all films from repository");
        List<Film> films = filmRepository.findAll();
        log.debug("Retrieved {} films", films.size());
        return films;
    }

    public Optional<Film> getFilmById(Long id) {
        log.info("Fetching film with ID: {}", id);
        Optional<Film> film = filmRepository.findById(id);
        if (film.isPresent()) {
            log.debug("Found film: {}", film.get().getTitle());
        } else {
            log.warn("Film with ID {} not found", id);
        }
        return film;
    }

    public Film createFilm(Film film) {
        log.info("Creating film: {}", film.getTitle());
        Film savedFilm = filmRepository.save(film);
        log.debug("Created film with ID: {}", savedFilm.getId());
        return savedFilm;
    }

    public Film updateFilm(Long id, Film filmDetails) {
        log.info("Updating film with ID: {}", id);
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Film with ID {} not found for update", id);
                    return new RuntimeException("Film not found with ID: " + id);
                });
        film.setTitle(filmDetails.getTitle());
        film.setReleaseYear(filmDetails.getReleaseYear());
        film.setOriginalLanguage(filmDetails.getOriginalLanguage());
        film.setDuration(filmDetails.getDuration());
        film.setRating(filmDetails.getRating());
        film.setFilmData(filmDetails.getFilmData());
        film.setGenres(filmDetails.getGenres());
        film.setPersonnel(filmDetails.getPersonnel());
        Film updatedFilm = filmRepository.save(film);
        log.debug("Updated film: {}", updatedFilm.getTitle());
        return updatedFilm;
    }

    public void deleteFilm(Long id) {
        log.info("Deleting film with ID: {}", id);
        if (!filmRepository.existsById(id)) {
            log.warn("Film with ID {} not found for deletion", id);
            throw new RuntimeException("Film not found with ID: " + id);
        }
        filmRepository.deleteById(id);
        log.debug("Deleted film with ID: {}", id);
    }
}