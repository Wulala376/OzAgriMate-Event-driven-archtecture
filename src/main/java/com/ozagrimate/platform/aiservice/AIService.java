package com.ozagrimate.platform.aiservice;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AIService {
    private final AIRecommendationRepository repository;

    public AIService(AIRecommendationRepository repository) {
        this.repository = repository;
    }

    public String checkRequest(AIRecommendationRequest request) {
        if (request == null || request.farmId == null || request.sensorId == null
                || request.soilMoisture == null || request.temperature == null) {
            return "Invalid recommendation request: farmId, sensorId, soilMoisture and temperature are required.";
        }

        if (request.soilMoisture < 0 || request.soilMoisture > 100) {
            return "Invalid recommendation request: soilMoisture must be between 0 and 100.";
        }

        return null;
    }

    /**
     * Mock rule-based model standing in for a real AI model (ADR-07).
     * Every recommendation carries the readings it was based on (ASR21).
     */
    public AIRecommendation recommend(AIRecommendationRequest request) {
        AIRecommendation result = new AIRecommendation();
        result.recommendationId = repository.nextId();

        if (request.soilMoisture < 15) {
            result.action = "Irrigate for 30 minutes";
            result.confidence = 0.87;
        } else if (request.soilMoisture < 30) {
            result.action = "Irrigate for 15 minutes";
            result.confidence = 0.74;
        } else {
            result.action = "No irrigation needed";
            result.confidence = 0.91;
        }

        result.supportingData = List.of(
                new SupportingData("soilMoisture", request.soilMoisture),
                new SupportingData("temperature", request.temperature),
                new SupportingData("rainForecastMm", 0.0));

        repository.save(result);
        return result;
    }
}