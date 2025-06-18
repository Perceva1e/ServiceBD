package com.example.servicedb.service;

import com.example.servicedb.dto.ReviewDTO;
import com.example.servicedb.dto.UserDTO;
import com.example.servicedb.dto.FilmDTO;
import com.example.servicedb.model.Review;
import com.example.servicedb.model.User;
import com.example.servicedb.model.Film;
import com.example.servicedb.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<ReviewDTO> getAllReviews() {
        log.info("Fetching all reviews from repository");
        List<Review> reviews = reviewRepository.findAll();
        log.debug("Retrieved {} reviews", reviews.size());
        return reviews.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<ReviewDTO> getReviewById(Long id) {
        log.info("Fetching review with ID: {}", id);
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            log.debug("Found review with ID: {}", id);
        } else {
            log.warn("Review with ID {} not found", id);
        }
        return review.map(this::mapToDTO);
    }

    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        log.info("Creating review with rating: {}", reviewDTO.getRating());
        Review review = mapToEntity(reviewDTO);
        Review savedReview = reviewRepository.save(review);
        log.debug("Created review with ID: {}", savedReview.getId());
        return mapToDTO(savedReview);
    }

    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        log.info("Updating review with ID: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Review with ID {} not found for update", id);
                    return new RuntimeException("Review not found with ID: " + id);
                });
        review.setRating(reviewDTO.getRating());
        review.setNumberOfLikes(reviewDTO.getNumberOfLikes());
        review.setNumberOfDislikes(reviewDTO.getNumberOfDislikes());
        review.setText(reviewDTO.getText());
        review.setPublicationDate(reviewDTO.getPublicationDate());
        User user = new User();
        user.setId(reviewDTO.getUser().getId());
        user.setEmail(reviewDTO.getUser().getEmail());
        user.setName(reviewDTO.getUser().getName());
        review.setUser(user);
        Film film = new Film();
        film.setId(reviewDTO.getFilm().getId());
        film.setTitle(reviewDTO.getFilm().getTitle());
        review.setFilm(film);
        Review updatedReview = reviewRepository.save(review);
        log.debug("Updated review with ID: {}", updatedReview.getId());
        return mapToDTO(updatedReview);
    }

    public void deleteReview(Long id) {
        log.info("Deleting review with ID: {}", id);
        if (!reviewRepository.existsById(id)) {
            log.warn("Review with ID {} not found for deletion", id);
            throw new RuntimeException("Review not found with ID: " + id);
        }
        reviewRepository.deleteById(id);
        log.debug("Deleted review with ID: {}", id);
    }

    private ReviewDTO mapToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setNumberOfLikes(review.getNumberOfLikes());
        dto.setNumberOfDislikes(review.getNumberOfDislikes());
        dto.setText(review.getText());
        dto.setPublicationDate(review.getPublicationDate());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(review.getUser().getId());
        userDTO.setEmail(review.getUser().getEmail());
        userDTO.setName(review.getUser().getName());
        dto.setUser(userDTO);
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setId(review.getFilm().getId());
        filmDTO.setTitle(review.getFilm().getTitle());
        dto.setFilm(filmDTO);
        return dto;
    }

    private Review mapToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setRating(reviewDTO.getRating());
        review.setNumberOfLikes(reviewDTO.getNumberOfLikes());
        review.setNumberOfDislikes(reviewDTO.getNumberOfDislikes());
        review.setText(reviewDTO.getText());
        review.setPublicationDate(reviewDTO.getPublicationDate());
        User user = new User();
        user.setId(reviewDTO.getUser().getId());
        user.setEmail(reviewDTO.getUser().getEmail());
        user.setName(reviewDTO.getUser().getName());
        review.setUser(user);
        Film film = new Film();
        film.setId(reviewDTO.getFilm().getId());
        film.setTitle(reviewDTO.getFilm().getTitle());
        review.setFilm(film);
        return review;
    }
}