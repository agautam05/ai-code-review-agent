package com.aman.codereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private long totalReviews;

    private double averageScore;

    private Map<String, Integer> topIssues;

    private String strongestWeakness;

    private Double improvementTrend;
}