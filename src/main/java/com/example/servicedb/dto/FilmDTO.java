package com.example.servicedb.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilmDTO {
    private Long id;
    private String title;
    private int releaseYear;
    private String originalLanguage;
    private Integer duration;
    private Double rating;
    private FilmDataDTO filmData;
    private List<GenreDTO> genres;
}