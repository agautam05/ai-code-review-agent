package com.aman.codereview.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ReviewResponse {

    private String review;

    private String language;

    private Integer score;

    private List<Map<String, Object>> issues;

    private List<String> suggestions;

    private String improvedCode;
    private String personalizedRecommendation;
    private List<Map<String,Object>> recurringIssues;
}