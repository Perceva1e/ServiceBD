package com.example.servicedb.service;

import com.example.servicedb.model.Review;
import com.example.servicedb.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        log.info("Fetching all reviews from repository");
        List<Review> reviews = reviewRepository.findAll();
        log.debug("Retrieved {} reviews", reviews.size());
        return reviews;
    }

    public Optional<Review> getReviewById(Long id) {
        log.info("Fetching review with ID: {}", id);
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            log.debug("Found review with ID: {}", id);
        } else {
            log.warn("Review with ID {} not found", id);
        }
        return review;
    }

    public Review createReview(Review review) {
        log.info("Creating review with rating: {}", review.getRating());
        Review savedReview = reviewRepository.save(review);
        log.debug("Created review with ID: {}", savedReview.getId());
        return savedReview;
    }

    public Review updateReview(Long id, Review reviewDetails) {
        log.info("Updating review with ID: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Review with ID {} not found for update", id);
                    return new RuntimeException("Review not found with ID: " + id);
                });
        review.setRating(reviewDetails.getRating());
        review.setNumberOfLikes(reviewDetails.getNumberOfLikes());
        review.setNumberOfDislikes(reviewDetails.getNumberOfDislikes());
        review.setText(reviewDetails.getText());
        review.setPublicationDate(reviewDetails.getPublicationDate());
        review.setUser(reviewDetails.getUser());
        review.setFilm(reviewDetails.getFilm());
        Review updatedReview = reviewRepository.save(review);
        log.debug("Updated review with ID: {}", updatedReview.getId());
        return updatedReview;
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
}