package com.aman.codereview.controller;

import com.aman.codereview.dto.ReviewRequest;
import com.aman.codereview.dto.ReviewResponse;
import com.aman.codereview.model.Review;
import com.aman.codereview.model.User;
import com.aman.codereview.repository.ReviewRepository;
import com.aman.codereview.repository.UserRepository;
import com.aman.codereview.service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Review APIs",
        description = "Secure code review APIs"
)
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    @Operation(
            summary = "Review source code"
    )
    @PostMapping
    public ReviewResponse reviewCode(
            @Valid
            @RequestBody
            ReviewRequest request,

            Authentication authentication
    ) throws JsonProcessingException {

        return reviewService.reviewCode(
                request,
                authentication.getName()
        );
    }

    @Operation(
            summary = "Get my review history"
    )
    @GetMapping("/me")
    public List<Review> getMyReviews(
            Authentication authentication
    ) {

        User user =
                userRepository
                        .findByEmail(
                                authentication.getName()
                        )
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )
                        );

        return reviewRepository
                .findByUserIdOrderByReviewedAtDesc(
                        user.getId()
                );
    }
}