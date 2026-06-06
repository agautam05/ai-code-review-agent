package com.aman.codereview.config;

import com.aman.codereview.model.User;
import com.aman.codereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer
        implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(
            String... args
    ) {

        String adminEmail =
                "amangautam639807@gmail.com";

        if (
                userRepository.existsByEmail(
                        adminEmail
                )
        ) {

            return;
        }

        User admin =
                User.builder()
                        .name(
                                "Aman Gautam"
                        )
                        .email(
                                adminEmail
                        )
                        .password(
                                passwordEncoder.encode(
                                        "Aman@05ag"
                                )
                        )
                        .role(
                                "ADMIN"
                        )
                        .build();

        userRepository.save(
                admin
        );

        System.out.println(
                "Admin account created successfully."
        );
    }
}