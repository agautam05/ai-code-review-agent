package com.aman.codereview.repository;

import com.aman.codereview.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository
        extends MongoRepository<User, String> {

    Optional<User> findByEmail(
            String email
    );

    boolean existsByEmail(
            String email
    );
}