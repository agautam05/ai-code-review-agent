package com.aman.codereview.controller;

import com.aman.codereview.dto.PublicStatsResponse;
import com.aman.codereview.service.PublicStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final PublicStatsService publicStatsService;

    @GetMapping("/stats")
    public PublicStatsResponse getStats() {

        return publicStatsService.getStats();
    }
}