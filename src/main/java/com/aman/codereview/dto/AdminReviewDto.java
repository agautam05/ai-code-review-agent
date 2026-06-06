package com.aman.codereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminReviewDto {

    private String reviewId;

    private String userId;

    private String userName;

    private String userEmail;

    private String language;

    private Integer score;

    private LocalDateTime reviewedAt;
}