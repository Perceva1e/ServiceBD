package com.example.servicedb.service;

import com.example.servicedb.model.FilmData;
import com.example.servicedb.repository.FilmDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmDataService {
    private final FilmDataRepository filmDataRepository;

    public List<FilmData> getAllFilmData() {
        log.info("Fetching all film data from repository");
        List<FilmData> filmDataList = filmDataRepository.findAll();
        log.debug("Retrieved {} film data entries", filmDataList.size());
        return filmDataList;
    }

    public Optional<FilmData> getFilmDataById(Long id) {
        log.info("Fetching film data with ID: {}", id);
        Optional<FilmData> filmData = filmDataRepository.findById(id);
        if (filmData.isPresent()) {
            log.debug("Found film data with ID: {}", id);
        } else {
            log.warn("Film data with ID {} not found", id);
        }
        return filmData;
    }

    public FilmData createFilmData(FilmData filmData) {
        log.info("Creating film data with rating: {}", filmData.getRating());
        FilmData savedFilmData = filmDataRepository.save(filmData);
        log.debug("Created film data with ID: {}", savedFilmData.getId());
        return savedFilmData;
    }

    public FilmData updateFilmData(Long id, FilmData filmDataDetails) {
        log.info("Updating film data with ID: {}", id);
        FilmData filmData = filmDataRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Film data with ID {} not found for update", id);
                    return new RuntimeException("Film data not found with ID: " + id);
                });
        filmData.setRating(filmDataDetails.getRating());
        filmData.setBudget(filmDataDetails.getBudget());
        filmData.setPoster(filmDataDetails.getPoster());
        filmData.setTrailer(filmDataDetails.getTrailer());
        filmData.setRevenue(filmDataDetails.getRevenue());
        FilmData updatedFilmData = filmDataRepository.save(filmData);
        log.debug("Updated film data with ID: {}", updatedFilmData.getId());
        return updatedFilmData;
    }

    public void deleteFilmData(Long id) {
        log.info("Deleting film data with ID: {}", id);
        if (!filmDataRepository.existsById(id)) {
            log.warn("Film data with ID {} not found for deletion", id);
            throw new RuntimeException("Film data not found with ID: " + id);
        }
        filmDataRepository.deleteById(id);
        log.debug("Deleted film data with ID: {}", id);
    }
}