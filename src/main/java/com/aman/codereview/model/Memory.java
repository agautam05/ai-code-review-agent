package com.aman.codereview.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "memory")
@CompoundIndex(
        name = "user_issue_idx",
        def = "{'userId':1,'issue':1}"
)
public class Memory {

    @Id
    private String id;

    private String userId;

    private String issue;

    @Builder.Default
    private Integer frequency = 1;

    @Builder.Default
    private LocalDateTime firstSeen =
            LocalDateTime.now();

    @Builder.Default
    private LocalDateTime lastSeen =
            LocalDateTime.now();

    @Builder.Default
    private Integer recentOccurrences = 1;

    @Builder.Default
    private String severity = "MEDIUM";
}