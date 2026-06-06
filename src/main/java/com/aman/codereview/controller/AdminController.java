package com.aman.codereview.controller;

import com.aman.codereview.dto.AdminDashboardResponse;
import com.aman.codereview.dto.AdminReviewDto;
import com.aman.codereview.dto.AdminUserDto;
import com.aman.codereview.service.AdminManagementService;
import com.aman.codereview.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final AdminManagementService
            adminManagementService;

    @GetMapping("/dashboard")
    public AdminDashboardResponse getDashboard() {

        return adminService.getDashboard();
    }

    @GetMapping("/users")
    public List<AdminUserDto> getAllUsers() {

        return adminManagementService
                .getAllUsers();
    }

    @GetMapping("/reviews")
    public List<AdminReviewDto> getAllReviews() {

        return adminManagementService
                .getAllReviews();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(
            @PathVariable String id,
            Authentication authentication
    ) {

        adminManagementService
                .deleteUser(
                        id,
                        authentication
                );
    }

    @DeleteMapping("/reviews/{id}")
    public void deleteReview(
            @PathVariable String id
    ) {

        adminManagementService
                .deleteReview(id);
    }

    @PutMapping("/users/{id}/promote")
    public void promoteToAdmin(
            @PathVariable String id
    ) {

        adminManagementService
                .promoteToAdmin(id);
    }

    @PutMapping("/users/{id}/demote")
    public void demoteToUser(
            @PathVariable String id
    ) {

        adminManagementService
                .demoteToUser(id);
    }
}