package com.example.servicedb.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rating;

    @Column(name = "number_of_likes")
    private int numberOfLikes;

    @Column(name = "number_of_dislikes")
    private int numberOfDislikes;

    private String text;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public int getNumberOfLikes() { return numberOfLikes; }
    public void setNumberOfLikes(int numberOfLikes) { this.numberOfLikes = numberOfLikes; }
    public int getNumberOfDislikes() { return numberOfDislikes; }
    public void setNumberOfDislikes(int numberOfDislikes) { this.numberOfDislikes = numberOfDislikes; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }
}