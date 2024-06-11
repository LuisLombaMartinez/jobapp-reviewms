package com.travis.jobapp.reviewms.review;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam UUID companyId) {
        return ResponseEntity.ok(reviewService.getAll(companyId));
    }

    @PostMapping
    public ResponseEntity<UUID> createReview(@RequestParam UUID companyId, @Valid @RequestBody Review review) {
        UUID id = reviewService.create(review, companyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getById(@RequestParam UUID companyId, @PathVariable UUID reviewId) {
        return ResponseEntity.ok(reviewService.getById(reviewId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@RequestParam UUID companyId, @PathVariable UUID reviewId) {
        reviewService.delete(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@RequestParam UUID companyId, @PathVariable UUID reviewId,
            @RequestBody Review updatedReview) {
        return ResponseEntity.ok(reviewService.update(reviewId, updatedReview));
    }

}
