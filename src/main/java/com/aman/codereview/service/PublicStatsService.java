package com.aman.codereview.service;

import com.aman.codereview.dto.PublicStatsResponse;
import com.aman.codereview.repository.ReviewRepository;
import com.aman.codereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicStatsService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public PublicStatsResponse getStats() {

        return PublicStatsResponse
                .builder()
                .totalUsers(
                        userRepository.count()
                )
                .totalReviews(
                        reviewRepository.count()
                )
                .supportedLanguages(15)
                .build();
    }
}