package com.example.servicedb.service;

import com.example.servicedb.model.Review;
import com.example.servicedb.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() { return reviewRepository.findAll(); }
    public Optional<Review> getReviewById(Long id) { return reviewRepository.findById(id); }
    public Review createReview(Review review) { return reviewRepository.save(review); }
    public Review updateReview(Long id, Review reviewDetails) {
        Review review = reviewRepository.findById(id).orElseThrow();
        review.setRating(reviewDetails.getRating());
        review.setNumberOfLikes(reviewDetails.getNumberOfLikes());
        review.setNumberOfDislikes(reviewDetails.getNumberOfDislikes());
        review.setText(reviewDetails.getText());
        review.setPublicationDate(reviewDetails.getPublicationDate());
        review.setUser(reviewDetails.getUser());
        review.setFilm(reviewDetails.getFilm());
        return reviewRepository.save(review);
    }
    public void deleteReview(Long id) { reviewRepository.deleteById(id); }
}
