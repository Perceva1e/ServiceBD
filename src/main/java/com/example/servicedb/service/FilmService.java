package com.example.servicedb.service;

import com.example.servicedb.dto.*;
import com.example.servicedb.model.Film;
import com.example.servicedb.model.Genre;
import com.example.servicedb.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    private final FilmRepository filmRepository;

    @Transactional(readOnly = true)
    public List<FilmDTO> getAllFilms() {
        log.info("Fetching all films from repository");
        List<Film> films = filmRepository.findAll();
        List<FilmDTO> filmDTOs = films.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        log.debug("Retrieved {} films", filmDTOs.size());
        return filmDTOs;
    }

    @Transactional(readOnly = true)
    public Optional<FilmDTO> getFilmById(Long id) {
        log.info("Fetching film with ID: {}", id);
        Optional<Film> film = filmRepository.findById(id);
        if (film.isPresent()) {
            log.debug("Found film: {}", film.get().getTitle());
            return Optional.of(convertToDTO(film.get()));
        } else {
            log.warn("Film with ID {} not found", id);
            return Optional.empty();
        }
    }

    @Transactional
    public FilmDTO createFilm(Film film) {
        log.info("Creating film: {}", film.getTitle());
        Film savedFilm = filmRepository.save(film);
        log.debug("Created film with ID: {}", savedFilm.getId());
        return convertToDTO(savedFilm);
    }

    @Transactional
    public FilmDTO updateFilm(Long id, Film filmDetails) {
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
        Film updatedFilm = filmRepository.save(film);
        log.debug("Updated film: {}", updatedFilm.getTitle());
        return convertToDTO(updatedFilm);
    }

    @Transactional
    public void deleteFilm(Long id) {
        log.info("Deleting film with ID: {}", id);
        if (!filmRepository.existsById(id)) {
            log.warn("Film with ID {} not found for deletion", id);
            throw new RuntimeException("Film not found with ID: " + id);
        }
        filmRepository.deleteById(id);
        log.debug("Deleted film with ID: {}", id);
    }

    private FilmDTO convertToDTO(Film film) {
        FilmDTO dto = new FilmDTO();
        dto.setId(film.getId());
        dto.setTitle(film.getTitle());
        dto.setReleaseYear(film.getReleaseYear());
        dto.setOriginalLanguage(film.getOriginalLanguage());
        dto.setDuration(film.getDuration());
        dto.setRating(film.getRating());

        if (film.getFilmData() != null) {
            FilmDataDTO filmDataDTO = new FilmDataDTO();
            filmDataDTO.setId(film.getFilmData().getId());
            filmDataDTO.setRating(film.getFilmData().getRating());
            filmDataDTO.setBudget(film.getFilmData().getBudget());
            filmDataDTO.setPoster(film.getFilmData().getPoster());
            filmDataDTO.setTrailer(film.getFilmData().getTrailer());
            filmDataDTO.setRevenue(film.getFilmData().getRevenue());
            dto.setFilmData(filmDataDTO);
        }

        if (film.getGenres() != null) {
            List<GenreDTO> genreDTOs = film.getGenres().stream()
                    .map(this::convertToGenreDTO)
                    .collect(Collectors.toList());
            dto.setGenres(genreDTOs);
        }

        if (film.getPersonnel() != null) {
            List<PersonnelDTO> personnelDTOs = film.getPersonnel().stream()
                    .map(personnel -> {
                        PersonnelDTO personnelDTO = new PersonnelDTO();
                        personnelDTO.setId(personnel.getId());
                        personnelDTO.setPosition(personnel.getPosition());
                        PersonDTO personDTO = new PersonDTO();
                        personDTO.setId(personnel.getPerson().getId());
                        personDTO.setName(personnel.getPerson().getName());
                        personDTO.setBiography(personnel.getPerson().getBiography());
                        personDTO.setPhotograph(personnel.getPerson().getPhotograph());
                        personnelDTO.setPerson(personDTO);
                        return personnelDTO;
                    })
                    .collect(Collectors.toList());
            dto.setPersonnel(personnelDTOs);
        }

        return dto;
    }

    private GenreDTO convertToGenreDTO(Genre genre) {
        GenreDTO dto = new GenreDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setDescription(genre.getDescription());
        return dto;
    }
}