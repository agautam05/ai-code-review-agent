package com.aman.codereview.service;

import com.aman.codereview.dto.ReviewRequest;
import com.aman.codereview.dto.ReviewResponse;
import com.aman.codereview.model.Memory;
import com.aman.codereview.model.Review;
import com.aman.codereview.repository.MemoryRepository;
import com.aman.codereview.repository.ReviewRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.aman.codereview.model.User;
import com.aman.codereview.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final GroqService groqService;
    private final ReviewRepository reviewRepository;
    private final MemoryService memoryService;
    private final MemoryRepository memoryRepository;
    private final UserRepository userRepository;

    private static final Logger log =
            LoggerFactory.getLogger(
                    ReviewService.class
            );

    public ReviewResponse reviewCode(
            ReviewRequest request,
            String email) throws JsonProcessingException {
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )
                        );

        List<Memory> memories =
                memoryRepository.findByUserId(
                        user.getId()
                );

        StringBuilder memorySummary =
                new StringBuilder();

        memorySummary.append(
                "Developer Memory Profile:\n"
        );

        for (Memory memory : memories) {

            memorySummary.append("- Issue: ")
                    .append(memory.getIssue())
                    .append("\n")

                    .append("  Frequency: ")
                    .append(memory.getFrequency())
                    .append("\n")

                    .append("  Severity: ")
                    .append(memory.getSeverity())
                    .append("\n")

                    .append("  First Seen: ")
                    .append(memory.getFirstSeen())
                    .append("\n")

                    .append("  Last Seen: ")
                    .append(memory.getLastSeen())
                    .append("\n\n");
        }

        String personalizedContext =
                """
                You are an AI Code Review Agent.
        
                Analyze the code and return ONLY valid JSON.
        
                Use the developer memory profile below.
        
                Requirements:
        
                1. Detect recurring mistakes.
        
                2. If frequency > 1,
                   mention that the issue has appeared before.
        
                3. Compare current review with memory.
        
                4. Generate a field called:
        
                   personalizedRecommendation
        
                Example:
        
                "You repeatedly struggle with
                CLASS_NAMING_CONVENTION (3 times)
                and MISSING_MAIN_METHOD (3 times).
                Focus on fixing these first."
        
                5. Generate a field called:
        
                   recurringIssues
        
                Example:
        
                [
                  {
                    "issue":"CLASS_NAMING_CONVENTION",
                    "frequency":3
                  }
                ]
        
                Return JSON format:
        
                {
                  "language":"",
                  "score":0,
                  "issues":[],
                  "suggestions":[],
                  "improvedCode":"",
                  "personalizedRecommendation":"",
                  "recurringIssues":[]
                }
        
                Developer Memory:
                """
                        + memorySummary;
        System.out.println("BEFORE GROQ");

        String review =
                groqService.reviewCode(
                        request.getCode(),
                        personalizedContext
                );

        System.out.println("AFTER GROQ");
        System.out.println("================================");
        System.out.println("GROQ RESPONSE:");
        System.out.println(review);
        System.out.println("================================");

        ObjectMapper objectMapper =
                new ObjectMapper();

        JsonNode jsonNode;

        try {
            System.out.println("STARTING JSON PARSE");
            jsonNode =
                    objectMapper.readTree(
                            review
                    );System.out.println("JSON PARSE SUCCESS");

        } catch (Exception e) {

            e.printStackTrace();

            throw e;
        }

        int score = 0;

        if (jsonNode.has("score")
                && !jsonNode.get("score").isNull()) {

            score =
                    jsonNode.get("score")
                            .asInt();
        }

        String language =
                "Unknown";

        if (jsonNode.has("language")
                && !jsonNode.get("language")
                .isNull()) {

            language =
                    jsonNode.get("language")
                            .asText();
        }

        List<String> suggestions =
                new ArrayList<>();
        String personalizedRecommendation =
                "No personalized recommendation available.";
        List<Map<String, Object>> recurringIssues =
                new ArrayList<>();
        JsonNode suggestionsNode =
                jsonNode.get(
                        "suggestions"
                );
        if (
                jsonNode.has(
                        "personalizedRecommendation"
                )
        ) {

            personalizedRecommendation =
                    jsonNode
                            .get(
                                    "personalizedRecommendation"
                            )
                            .asText();
        }
        JsonNode recurringNode =
                jsonNode.get("recurringIssues");

        if (recurringNode != null &&
                recurringNode.isArray()) {

            for (JsonNode item : recurringNode) {

                Map<String, Object> issueMap =
                        new HashMap<>();

                issueMap.put(
                        "issue",
                        item.has("issue")
                                ? item.get("issue").asText()
                                : "Unknown"
                );

                issueMap.put(
                        "frequency",
                        item.has("frequency")
                                ? item.get("frequency").asInt()
                                : 0
                );

                recurringIssues.add(issueMap);
            }
        }

        if (suggestionsNode != null && suggestionsNode.isArray()){

            for (JsonNode suggestion :
                    suggestionsNode) {

                suggestions.add(
                        suggestion.asText()
                );
            }
        }

        List<Map<String, Object>>
                issues =
                new ArrayList<>();

        JsonNode issuesNode =
                jsonNode.get("issues");

        if (issuesNode != null && issuesNode.isArray()) {

            for (JsonNode issue :
                    issuesNode) {

                Map<String, Object>
                        item =
                        new HashMap<>();

                issue.fields()
                        .forEachRemaining(field ->

                                item.put(
                                        field.getKey(),

                                        field.getValue()
                                                .isNumber()

                                                ? field.getValue()
                                                  .numberValue()

                                                : field.getValue()
                                                  .asText()
                                )

                        );

                issues.add(item);

                if (issue.has("title")) {

                    memoryService.trackIssue(
                            user.getId(),
                            issue.get("title").asText(),
                            issue.has("severity")
                                    ? issue.get("severity").asText()
                                    : "MEDIUM"
                    );
                }
            }
        }

        String improvedCode = "";

        if (jsonNode.has("improvedCode")
                && !jsonNode.get("improvedCode")
                .isNull()) {

            improvedCode =
                    jsonNode.get(
                            "improvedCode"
                    ).asText();
        }

        Review reviewEntity =
                Review.builder()
                        .userId(
                                user.getId()
                        )
                        .language(
                                language
                        )
                        .code(
                                request.getCode()
                        )
                        .reviewResult(
                                review
                        )
                        .score(
                                score
                        )
                        .reviewedAt(
                                LocalDateTime.now()
                        )
                        .build();

        Review saved = reviewRepository.save(reviewEntity);

        System.out.println("Saved Review ID = " + saved.getId());

        log.info(
                "Saved review {}",
                saved.getId()
        );System.out.println(
                "PERSONALIZED RECOMMENDATION = "
                        + personalizedRecommendation
        );

        return ReviewResponse.builder()
                .review(review)
                .language(language)
                .score(score)
                .issues(issues)
                .suggestions(suggestions)
                .improvedCode(improvedCode)
                .personalizedRecommendation(
                        personalizedRecommendation
                )
                .recurringIssues(
                        recurringIssues
                )
                .build();
    }
}