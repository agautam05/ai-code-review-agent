package com.aman.codereview.controller;

import com.aman.codereview.model.Memory;
import com.aman.codereview.model.User;
import com.aman.codereview.repository.MemoryRepository;
import com.aman.codereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memory")
@RequiredArgsConstructor
public class MemoryController {

    private final MemoryRepository memoryRepository;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public List<Memory> getMyMemory(
            Authentication authentication
    ) {

        User user =
                userRepository
                        .findByEmail(
                                authentication.getName()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        return memoryRepository.findByUserId(
                user.getId()
        );

    }
}