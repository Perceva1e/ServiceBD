package com.example.servicedb.service;

import com.example.servicedb.model.Film;
import com.example.servicedb.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    @Autowired
    private FilmRepository filmRepository;

    public List<Film> getAllFilms() { return filmRepository.findAll(); }
    public Optional<Film> getFilmById(Long id) { return filmRepository.findById(id); }
    public Film createFilm(Film film) { return filmRepository.save(film); }
    public Film updateFilm(Long id, Film filmDetails) {
        Film film = filmRepository.findById(id).orElseThrow();
        film.setTitle(filmDetails.getTitle());
        film.setReleaseYear(filmDetails.getReleaseYear());
        film.setOriginalLanguage(filmDetails.getOriginalLanguage());
        film.setDuration(filmDetails.getDuration());
        film.setRating(filmDetails.getRating());
        film.setFilmData(filmDetails.getFilmData());
        film.setGenres(filmDetails.getGenres());
        film.setPersonnel(filmDetails.getPersonnel());
        return filmRepository.save(film);
    }
    public void deleteFilm(Long id) { filmRepository.deleteById(id); }
}
