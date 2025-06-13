package com.example.servicedb.service;

import com.example.servicedb.model.FilmData;
import com.example.servicedb.repository.FilmDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmDataService {
    @Autowired
    private FilmDataRepository filmDataRepository;

    public List<FilmData> getAllFilmData() { return filmDataRepository.findAll(); }
    public Optional<FilmData> getFilmDataById(Long id) { return filmDataRepository.findById(id); }
    public FilmData createFilmData(FilmData filmData) { return filmDataRepository.save(filmData); }
    public FilmData updateFilmData(Long id, FilmData filmDataDetails) {
        FilmData filmData = filmDataRepository.findById(id).orElseThrow();
        filmData.setRating(filmDataDetails.getRating());
        filmData.setBudget(filmDataDetails.getBudget());
        filmData.setPoster(filmDataDetails.getPoster());
        filmData.setTrailer(filmDataDetails.getTrailer());
        filmData.setRevenue(filmDataDetails.getRevenue());
        return filmDataRepository.save(filmData);
    }
    public void deleteFilmData(Long id) { filmDataRepository.deleteById(id); }
}