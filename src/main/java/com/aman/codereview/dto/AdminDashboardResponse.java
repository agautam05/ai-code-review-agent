package com.aman.codereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {

    private long totalUsers;

    private long totalAdmins;

    private long totalNormalUsers;

    private long totalReviews;

    private long totalMemories;

    private long reviewsToday;

    private double averagePlatformScore;
}