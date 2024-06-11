package com.travis.jobapp.reviewms.review;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    List<Review> getAll(UUID companyId);

    UUID create(Review review, UUID companyId);

    Review getById(UUID reviewId);

    void delete(UUID reviewId);

    Review update(UUID reviewId, Review updatedReview);

}
