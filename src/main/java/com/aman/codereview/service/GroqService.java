package com.aman.codereview.service;

import com.aman.codereview.dto.groq.GroqMessage;
import com.aman.codereview.dto.groq.GroqRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GroqService {

    private final RestClient restClient;

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.model}")
    private String model;

    public String reviewCode(
            String code,
            String memorySummary
    ) {

        String prompt = """
You are a senior software engineer,
code reviewer,
security analyst,
and programming mentor.

Developer Memory Profile:

"""
                + memorySummary
                +
                """

Use this memory profile to personalize
your review.

IMPORTANT:

- Detect recurring mistakes.
- Mention repeated issues.
- Adapt recommendations using history.
- Focus on the developer's weakest areas.
- If previous mistakes appear again,
  explicitly mention that they are recurring.

TASKS:

1. Detect programming language.
2. Identify syntax errors.
3. Identify logic errors.
4. Identify security vulnerabilities.
5. Identify performance issues.
6. Identify maintainability issues.
7. Identify code style issues.
8. Suggest improvements.
9. Generate improved code.
10. Generate personalized recommendation.

Supported Languages:

Java
Python
JavaScript
TypeScript
C
C++
C#
Go
Rust
PHP
Kotlin
Swift
Ruby
Dart

Return ONLY valid JSON.

Response format:

{
  "language": "Java",

  "score": 8,

  "issues": [
    {
      "severity": "HIGH",
      "title": "Input Validation Missing",
      "description": "User input is not validated.",
      "line": 15,
      "fix": "Validate user input before processing."
    }
  ],

  "suggestions": [
    "Validate all user input",
    "Use try-catch blocks"
  ],

  "personalizedRecommendation":
  "You have encountered input validation issues multiple times. Focus on defensive programming and validation strategies.",

  "improvedCode":
  "full corrected code"
}

Rules:

- Return ONLY JSON.
- No markdown.
- No code fences.
- No explanations outside JSON.
- score must be between 0 and 10.
- issues maximum 10.
- severity must be:
  CRITICAL
  HIGH
  MEDIUM
  LOW
- title must be concise.
- description must explain the issue.
- fix must provide a practical solution.
- improvedCode must contain corrected code.
- preserve functionality whenever possible.
- personalizedRecommendation is mandatory.

Review Criteria:

- Syntax
- Logic
- Security
- Performance
- Scalability
- Readability
- Maintainability
- Error Handling
- Best Practices

CODE:

"""
                + code;

        GroqRequest request =
                GroqRequest.builder()
                        .model(model)
                        .messages(
                                List.of(
                                        new GroqMessage(
                                                "user",
                                                prompt
                                        )
                                )
                        )
                        .build();

        Map response =
                restClient.post()
                        .uri(apiUrl)
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                "Bearer " + apiKey
                        )
                        .contentType(
                                MediaType.APPLICATION_JSON
                        )
                        .body(request)
                        .retrieve()
                        .body(Map.class);

        List choices =
                (List) response.get(
                        "choices"
                );

        if (
                choices == null
                        || choices.isEmpty()
        ) {

            throw new RuntimeException(
                    "No response received from AI"
            );
        }

        Map firstChoice =
                (Map) choices.get(0);

        Map message =
                (Map) firstChoice.get(
                        "message"
                );

        String content =
                message.get(
                                "content"
                        ).toString()
                        .trim();

        int start =
                content.indexOf("{");

        int end =
                content.lastIndexOf("}");

        if (
                start >= 0
                        && end > start
        ) {

            content =
                    content.substring(
                            start,
                            end + 1
                    );
        }

        return content;
    }
}