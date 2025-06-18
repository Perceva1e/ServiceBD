package com.example.servicedb.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FilmData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double rating;
    private double budget;
    private String poster;
    private String trailer;
    private double revenue;

    @OneToOne(mappedBy = "filmData")
    private Film film;
}