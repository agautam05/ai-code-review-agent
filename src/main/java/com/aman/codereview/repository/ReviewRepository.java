package com.aman.codereview.repository;

import com.aman.codereview.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository
        extends MongoRepository<Review, String> {

    List<Review> findByUserId(
            String userId
    );

    List<Review> findByUserIdOrderByReviewedAtDesc(
            String userId
    );

    List<Review> findByLanguage(
            String language
    );

    long countByUserId(
            String userId
    );
}