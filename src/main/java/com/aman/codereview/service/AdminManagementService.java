package com.aman.codereview.service;

import com.aman.codereview.dto.AdminReviewDto;
import com.aman.codereview.dto.AdminUserDto;
import com.aman.codereview.model.Memory;
import com.aman.codereview.model.Review;
import com.aman.codereview.model.User;
import com.aman.codereview.repository.MemoryRepository;
import com.aman.codereview.repository.ReviewRepository;
import com.aman.codereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminManagementService {

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    private final MemoryRepository memoryRepository;

    public List<AdminUserDto> getAllUsers() {

        return userRepository
                .findAll()
                .stream()

                .map(user ->

                        AdminUserDto
                                .builder()

                                .id(user.getId())
                                .name(user.getName())
                                .email(user.getEmail())
                                .role(user.getRole())

                                .totalReviews(
                                        reviewRepository
                                                .findByUserId(
                                                        user.getId()
                                                )
                                                .size()
                                )

                                .createdAt(
                                        user.getCreatedAt()
                                )

                                .build()
                )

                .toList();
    }

    public List<AdminReviewDto> getAllReviews() {

        return reviewRepository
                .findAll()
                .stream()

                .map(review -> {

                    User user =
                            userRepository
                                    .findById(
                                            review.getUserId()
                                    )
                                    .orElse(null);

                    return AdminReviewDto
                            .builder()

                            .reviewId(
                                    review.getId()
                            )

                            .userId(
                                    review.getUserId()
                            )

                            .userName(
                                    user != null
                                            ? user.getName()
                                            : "Deleted User"
                            )

                            .userEmail(
                                    user != null
                                            ? user.getEmail()
                                            : "N/A"
                            )

                            .language(
                                    review.getLanguage()
                            )

                            .score(
                                    review.getScore()
                            )

                            .reviewedAt(
                                    review.getReviewedAt()
                            )

                            .build();
                })

                .toList();
    }

    public void deleteUser(
            String userId,
            Authentication authentication
    ) {

        User currentAdmin =
                userRepository
                        .findByEmail(
                                authentication.getName()
                        )
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Admin not found"
                                        )
                        );

        if (
                currentAdmin.getId()
                        .equals(userId)
        ) {

            throw new RuntimeException(
                    "You cannot delete your own account"
            );
        }

        reviewRepository
                .findByUserId(
                        userId
                )
                .forEach(
                        review ->
                                reviewRepository.deleteById(
                                        review.getId()
                                )
                );

        memoryRepository
                .findByUserId(
                        userId
                )
                .forEach(
                        memory ->
                                memoryRepository.deleteById(
                                        memory.getId()
                                )
                );

        userRepository.deleteById(
                userId
        );
    }

    public void deleteReview(
            String reviewId
    ) {

        reviewRepository.deleteById(
                reviewId
        );
    }

    public void promoteToAdmin(
            String userId
    ) {

        User user =
                userRepository
                        .findById(
                                userId
                        )
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )
                        );

        if (
                "ADMIN".equals(
                        user.getRole()
                )
        ) {

            throw new RuntimeException(
                    "User is already admin"
            );
        }

        user.setRole(
                "ADMIN"
        );

        userRepository.save(
                user
        );
    }

    public void demoteToUser(
            String userId
    ) {

        User user =
                userRepository
                        .findById(
                                userId
                        )
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )
                        );

        if (
                !"ADMIN".equals(
                        user.getRole()
                )
        ) {

            throw new RuntimeException(
                    "User is not admin"
            );
        }

        long adminCount =
                userRepository
                        .findAll()
                        .stream()

                        .filter(
                                u ->
                                        "ADMIN".equals(
                                                u.getRole()
                                        )
                        )

                        .count();

        if (
                adminCount <= 1
        ) {

            throw new RuntimeException(
                    "Cannot remove the last admin"
            );
        }

        user.setRole(
                "USER"
        );

        userRepository.save(
                user
        );
    }
}