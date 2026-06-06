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
public class AdminUserDto {

    private String id;

    private String name;

    private String email;

    private String role;

    private long totalReviews;

    private LocalDateTime createdAt;
}