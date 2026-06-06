package com.aman.codereview.service;

import com.aman.codereview.dto.CreateUserRequest;
import com.aman.codereview.model.User;
import com.aman.codereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(CreateUserRequest request){

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }
}