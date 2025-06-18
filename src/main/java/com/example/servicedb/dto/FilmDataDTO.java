package com.example.servicedb.dto;

import lombok.Data;

@Data
public class FilmDataDTO {
    private Long id;
    private double rating;
    private double budget;
    private String poster;
    private String trailer;
    private double revenue;
}