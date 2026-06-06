package com.aman.codereview.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewRequest {

    @NotBlank(message = "Code cannot be empty")
    private String code;

    private String language;
}