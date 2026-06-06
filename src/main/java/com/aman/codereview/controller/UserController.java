package com.aman.codereview.controller;

import com.aman.codereview.dto.CreateUserRequest;
import com.aman.codereview.model.User;
import com.aman.codereview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(
            @RequestBody CreateUserRequest request){

        return userService.createUser(request);
    }
}