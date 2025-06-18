package com.example.servicedb.controller;

import com.example.servicedb.dto.ReviewDTO;
import com.example.servicedb.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@Slf4j
@Tag(name = "Review API", description = "Endpoints for managing reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    @Operation(summary = "Get all reviews", description = "Retrieves a list of all reviews")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of reviews")
    })
    public List<ReviewDTO> getAllReviews() {
        log.info("Fetching all reviews");
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        log.debug("Retrieved {} reviews", reviews.size());
        return reviews;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get review by ID", description = "Retrieves a review by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved review"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    public ResponseEntity<ReviewDTO> getReviewById(@Parameter(description = "ID of the review") @PathVariable Long id) {
        log.info("Fetching review with ID: {}", id);
        Optional<ReviewDTO> review = reviewService.getReviewById(id);
        if (review.isPresent()) {
            log.debug("Found review with ID: {}", id);
            return ResponseEntity.ok(review.get());
        } else {
            log.warn("Review with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new review", description = "Creates a new review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid review data")
    })
    public ReviewDTO createReview(@RequestBody ReviewDTO reviewDTO) {
        log.info("Creating new review");
        ReviewDTO createdReview = reviewService.createReview(reviewDTO);
        log.debug("Created review with ID: {}", createdReview.getId());
        return createdReview;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a review", description = "Updates an existing review by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review updated successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found"),
            @ApiResponse(responseCode = "400", description = "Invalid review data")
    })
    public ResponseEntity<ReviewDTO> updateReview(@Parameter(description = "ID of the review to update") @PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        log.info("Updating review with ID: {}", id);
        ReviewDTO updatedReview = reviewService.updateReview(id, reviewDTO);
        log.debug("Updated review with ID: {}", updatedReview.getId());
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review", description = "Deletes a review by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    public ResponseEntity<Void> deleteReview(@Parameter(description = "ID of the review to delete") @PathVariable Long id) {
        log.info("Deleting review with ID: {}", id);
        reviewService.deleteReview(id);
        log.debug("Deleted review with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}