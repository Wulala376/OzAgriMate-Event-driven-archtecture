package com.ozagrimate.platform.aiservice;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

/**
 * Keeps issued recommendations so an agronomist can review them later (ASR22).
 */
@Repository
public class AIRecommendationRepository {
    private final Map<String, AIRecommendation> recommendationDB = new HashMap<>();
    private int idCounter = 300;

    public String nextId() {
        String newId = "R" + idCounter;
        idCounter++;
        return newId;
    }

    public void save(AIRecommendation recommendation) {
        recommendationDB.put(recommendation.recommendationId, recommendation);
    }
}