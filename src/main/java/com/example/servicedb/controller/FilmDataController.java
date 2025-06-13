package com.example.servicedb.controller;

import com.example.servicedb.model.FilmData;
import com.example.servicedb.service.FilmDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/filmdata")
public class FilmDataController {
    @Autowired
    private FilmDataService filmDataService;

    @GetMapping
    public List<FilmData> getAllFilmData() { return filmDataService.getAllFilmData(); }
    @GetMapping("/{id}")
    public ResponseEntity<FilmData> getFilmDataById(@PathVariable Long id) {
        Optional<FilmData> filmData = filmDataService.getFilmDataById(id);
        return filmData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public FilmData createFilmData(@RequestBody FilmData filmData) { return filmDataService.createFilmData(filmData); }
    @PutMapping("/{id}")
    public ResponseEntity<FilmData> updateFilmData(@PathVariable Long id, @RequestBody FilmData filmData) {
        return ResponseEntity.ok(filmDataService.updateFilmData(id, filmData));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilmData(@PathVariable Long id) {
        filmDataService.deleteFilmData(id);
        return ResponseEntity.noContent().build();
    }
}
