package com.aman.codereview.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    private String userId;

    private String language;

    private String code;

    private String reviewResult;

    private String improvedCode;

    private Integer score;

    @Builder.Default
    private LocalDateTime reviewedAt =
            LocalDateTime.now();
}