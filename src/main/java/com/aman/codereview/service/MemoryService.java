package com.aman.codereview.service;

import com.aman.codereview.model.Memory;
import com.aman.codereview.repository.MemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemoryService {

    private final MemoryRepository memoryRepository;

    public void trackIssue(
            String userId,
            String issue,
            String severity) {

        if (issue == null || issue.isBlank()) {
            return;
        }

        String normalizedIssue = normalizeIssue(issue);

        Memory memory = memoryRepository
                .findByUserIdAndIssue(
                        userId,
                        normalizedIssue
                )
                .orElse(null);

        if (memory == null) {

            memory = Memory.builder()
                    .userId(userId)
                    .issue(normalizedIssue)
                    .frequency(1)
                    .severity(severity)
                    .firstSeen(LocalDateTime.now())
                    .lastSeen(LocalDateTime.now())
                    .recentOccurrences(1)
                    .build();

        } else {

            memory.setFrequency(
                    memory.getFrequency() + 1
            );

            memory.setRecentOccurrences(
                    memory.getRecentOccurrences() + 1
            );

            memory.setLastSeen(
                    LocalDateTime.now()
            );

            memory.setSeverity(severity);
        }

        memoryRepository.save(memory);
    }

    private String normalizeIssue(
            String issue) {

        String normalized =
                issue.trim()
                        .toUpperCase();

        normalized =
                normalized.replaceAll(
                        "\\s+",
                        "_"
                );

        return normalized;
    }
}