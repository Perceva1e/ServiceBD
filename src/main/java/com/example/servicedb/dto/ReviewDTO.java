package com.example.servicedb.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {
    private Long id;
    private int rating;
    private int numberOfLikes;
    private int numberOfDislikes;
    private String text;
    private LocalDate publicationDate;
    private UserDTO user;
    private FilmDTO film;
}