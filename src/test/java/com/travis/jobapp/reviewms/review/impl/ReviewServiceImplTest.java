package com.travis.jobapp.reviewms.review.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.travis.jobapp.reviewms.review.Review;
import com.travis.jobapp.reviewms.review.ReviewRepository;

public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private UUID companyId;
    private UUID reviewId;
    private Review review;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        companyId = UUID.randomUUID();
        reviewId = UUID.randomUUID();
        review = new Review();
        review.setId(reviewId);
        review.setCompanyId(companyId);
    }

    @Test
    public void testGetAll() {
        when(reviewRepository.findByCompanyId(companyId)).thenReturn(Arrays.asList(review));
        List<Review> reviews = reviewService.getAll(companyId);
        assertEquals(1, reviews.size());
        assertEquals(reviewId, reviews.get(0).getId());
    }

    @Test
    public void testCreate() {
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        UUID createdReviewId = reviewService.create(new Review(), companyId);
        assertEquals(reviewId, createdReviewId);
    }

    @Test
    public void testCreateWithExistingId() {
        Review newReview = new Review();
        newReview.setId(UUID.randomUUID());
        assertThrows(IllegalArgumentException.class, () -> reviewService.create(newReview, companyId));
    }

    @Test
    public void testGetByIdFound() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        Review foundReview = reviewService.getById(reviewId);
        assertEquals(reviewId, foundReview.getId());
    }

    @Test
    public void testGetByIdNotFound() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> reviewService.getById(reviewId));
    }

    @Test
    public void testDelete() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        doNothing().when(reviewRepository).delete(review);
        reviewService.delete(reviewId);
        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    public void testUpdate() {
        Review updatedReview = new Review();
        updatedReview.setId(reviewId);
        updatedReview.setCompanyId(companyId);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(updatedReview);
        Review result = reviewService.update(reviewId, updatedReview);
        assertEquals(reviewId, result.getId());
    }

    @Test
    public void testUpdateNotFound() {
        Review updatedReview = new Review();
        updatedReview.setId(reviewId);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> reviewService.update(reviewId, updatedReview));
    }
}