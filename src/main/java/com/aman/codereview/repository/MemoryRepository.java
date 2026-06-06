package com.aman.codereview.repository;

import com.aman.codereview.model.Memory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MemoryRepository
        extends MongoRepository<Memory, String> {

    Optional<Memory> findByUserIdAndIssue(
            String userId,
            String issue
    );

    List<Memory> findByUserId(
            String userId
    );

    long countByUserId(
            String userId
    );
}