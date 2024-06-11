package com.travis.jobapp.reviewms.review.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.travis.jobapp.reviewms.review.Review;
import com.travis.jobapp.reviewms.review.ReviewRepository;
import com.travis.jobapp.reviewms.review.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAll(UUID companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public UUID create(Review review, UUID companyId) {
        if (review.getId() != null) {
            throw new IllegalArgumentException("A new review cannot already have an ID");
        }
        if (review.getCompanyId() == null) {
            throw new IllegalArgumentException("A review must have a company");
        }
        return reviewRepository.save(review).getId();
    }

    @Override
    public Review getById(UUID reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Review not found with id " + reviewId));
    }

    @Override
    public void delete(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Review not found with id " + reviewId));
        reviewRepository.delete(review);
    }

    @Override
    public Review update(UUID reviewId, Review updatedReview) {
        if (updatedReview.getId() == null) {
            throw new IllegalArgumentException("An updated review must have an ID");
        }
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Review not found with id " + reviewId));
        review.updateFrom(updatedReview);
        return reviewRepository.save(review);
    }

}
