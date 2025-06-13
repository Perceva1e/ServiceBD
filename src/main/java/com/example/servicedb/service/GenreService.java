package com.example.servicedb.service;

import com.example.servicedb.model.Genre;
import com.example.servicedb.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() { return genreRepository.findAll(); }
    public Optional<Genre> getGenreById(Long id) { return genreRepository.findById(id); }
    public Genre createGenre(Genre genre) { return genreRepository.save(genre); }
    public Genre updateGenre(Long id, Genre genreDetails) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        genre.setName(genreDetails.getName());
        genre.setDescription(genreDetails.getDescription());
        return genreRepository.save(genre);
    }
    public void deleteGenre(Long id) { genreRepository.deleteById(id); }
}