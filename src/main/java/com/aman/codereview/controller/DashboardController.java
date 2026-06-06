package com.aman.codereview.controller;

import com.aman.codereview.dto.DashboardResponse;
import com.aman.codereview.dto.LeaderboardEntry;
import com.aman.codereview.model.User;
import com.aman.codereview.repository.UserRepository;
import com.aman.codereview.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Dashboard APIs",
        description = "Developer analytics dashboard"
)
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserRepository userRepository;

    @Operation(
            summary = "Get my dashboard"
    )
    @GetMapping("/me")
    public DashboardResponse getMyDashboard(
            Authentication authentication
    ) {

        System.out.println(
                "AUTH EMAIL = "
                        + authentication.getName()
        );

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

        System.out.println(
                "USER ID = "
                        + user.getId()
        );

        return dashboardService
                .getDashboard(
                        user.getId()
                );
    }

    @Operation(
            summary = "Get leaderboard"
    )
    @GetMapping("/leaderboard")
    public List<LeaderboardEntry>
    getLeaderboard() {

        return dashboardService
                .getLeaderboard();
    }
//    @GetMapping("/leaderboard")
//    public List<LeaderboardResponse> leaderboard() {
//        return dashboardService.getLeaderboard();
//    }
}