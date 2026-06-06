package com.aman.codereview.controller;

import com.aman.codereview.dto.AuthResponse;
import com.aman.codereview.dto.LoginRequest;
import com.aman.codereview.dto.RegisterRequest;
import com.aman.codereview.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(
            @RequestBody
            RegisterRequest request
    ) {

        return authService.register(
                request
        );
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody
            LoginRequest request
    ) {

        return authService.login(
                request
        );
    }
}