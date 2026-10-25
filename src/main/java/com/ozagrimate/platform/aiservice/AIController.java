package com.ozagrimate.platform.aiservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/ai/recommendations")
public class AIController {
    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping
    public ResponseEntity<?> requestRecommendation(@RequestBody AIRecommendationRequest request) {
        String error = aiService.checkRequest(request);

        if (error != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", error));
        }

        return ResponseEntity.ok(aiService.recommend(request));
    }
}