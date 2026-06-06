package com.aman.codereview.service;

import com.aman.codereview.dto.AdminDashboardResponse;
import com.aman.codereview.model.Review;
import com.aman.codereview.model.User;
import com.aman.codereview.repository.MemoryRepository;
import com.aman.codereview.repository.ReviewRepository;
import com.aman.codereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    private final MemoryRepository memoryRepository;

    public AdminDashboardResponse getDashboard() {

        List<User> users =
                userRepository.findAll();

        List<Review> reviews =
                reviewRepository.findAll();

        long totalAdmins =
                users.stream()

                        .filter(
                                user ->
                                        "ADMIN".equals(
                                                user.getRole()
                                        )
                        )

                        .count();

        long totalNormalUsers =
                users.stream()

                        .filter(
                                user ->
                                        "USER".equals(
                                                user.getRole()
                                        )
                        )

                        .count();

        long reviewsToday =
                reviews.stream()

                        .filter(
                                review ->
                                        review.getReviewedAt() != null
                                                &&
                                                review.getReviewedAt()
                                                        .toLocalDate()
                                                        .equals(
                                                                LocalDate.now()
                                                        )
                        )

                        .count();

        double averageScore =
                reviews.stream()

                        .filter(
                                review ->
                                        review.getScore()
                                                != null
                        )

                        .mapToInt(
                                Review::getScore
                        )

                        .average()

                        .orElse(0);

        return AdminDashboardResponse
                .builder()

                .totalUsers(
                        users.size()
                )

                .totalAdmins(
                        totalAdmins
                )

                .totalNormalUsers(
                        totalNormalUsers
                )

                .totalReviews(
                        reviews.size()
                )

                .totalMemories(
                        memoryRepository.count()
                )

                .reviewsToday(
                        reviewsToday
                )

                .averagePlatformScore(
                        averageScore
                )

                .build();
    }
}