package com.aman.codereview.service;

import com.aman.codereview.dto.DashboardResponse;
import com.aman.codereview.dto.LeaderboardEntry;
import com.aman.codereview.model.Memory;
import com.aman.codereview.model.Review;
import com.aman.codereview.model.User;
import com.aman.codereview.repository.MemoryRepository;
import com.aman.codereview.repository.ReviewRepository;
import com.aman.codereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ReviewRepository reviewRepository;
    private final MemoryRepository memoryRepository;
    private final UserRepository userRepository;

    public DashboardResponse getDashboard(
            String userId
    ) {

        List<Review> reviews =
                reviewRepository.findByUserIdOrderByReviewedAtDesc(
                        userId
                );

        List<Memory> memories =
                memoryRepository.findByUserId(
                        userId
                );
        System.out.println(
                "Dashboard User ID = "
                        + userId
        );

        System.out.println(
                "Reviews Count = "
                        + reviews.size()
        );

        System.out.println(
                "Memory Count = "
                        + memories.size()
        );

        long totalReviews =
                reviews.size();

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

        Map<String, Integer> topIssues =
                memories.stream()

                        .sorted(
                                (a, b) ->
                                        Integer.compare(
                                                b.getFrequency(),
                                                a.getFrequency()
                                        )
                        )

                        .collect(
                                Collectors.toMap(
                                        Memory::getIssue,
                                        Memory::getFrequency,
                                        (a, b) -> a,
                                        LinkedHashMap::new
                                )
                        );

        String strongestWeakness =
                topIssues.entrySet()
                        .stream()
                        .max(
                                Map.Entry.comparingByValue()
                        )
                        .map(
                                Map.Entry::getKey
                        )
                        .orElse(
                                "NONE"
                        );
        double improvementTrend = 0;

        if (reviews.size() >= 2) {

            int splitPoint = reviews.size() / 2;

            double oldAverage =
                    reviews.subList(0, splitPoint)
                            .stream()
                            .filter(r -> r.getScore() != null)
                            .mapToInt(Review::getScore)
                            .average()
                            .orElse(0);

            double newAverage =
                    reviews.subList(splitPoint, reviews.size())
                            .stream()
                            .filter(r -> r.getScore() != null)
                            .mapToInt(Review::getScore)
                            .average()
                            .orElse(0);

            improvementTrend = newAverage - oldAverage;
        }

        return DashboardResponse.builder()
                .totalReviews(totalReviews)
                .averageScore(averageScore)
                .topIssues(topIssues)
                .strongestWeakness(strongestWeakness)
                .improvementTrend(improvementTrend)
                .build();
    }

    public List<LeaderboardEntry>
    getLeaderboard() {

        List<Review> reviews =
                reviewRepository.findAll();

        return reviews.stream()

                .collect(
                        Collectors.groupingBy(
                                Review::getUserId
                        )
                )

                .entrySet()

                .stream()

                .map(entry -> {

                    double avgScore =
                            entry.getValue()
                                    .stream()

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

                    User user =
                            userRepository
                                    .findById(
                                            entry.getKey()
                                    )
                                    .orElse(null);

                    String userName =
                            user != null
                                    ? user.getName()
                                    : "Unknown";

                    return LeaderboardEntry
                            .builder()
                            .userName(
                                    user != null
                                            ? user.getName()
                                            : "Unknown User"
                            )
                            .averageScore(
                                    avgScore
                            )
                            .totalReviews(
                                    entry.getValue().size()
                            )
                            .build();

                })

                .sorted(
                        (a, b) ->
                                Double.compare(
                                        b.getAverageScore(),
                                        a.getAverageScore()
                                )
                )

                .toList();
    }
}