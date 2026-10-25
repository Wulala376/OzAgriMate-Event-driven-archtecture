package com.ozagrimate.platform.alertservice;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//  In-memory stand-in for the alert store. In the real architecture the
//  Alert & Anomaly Consumers write alerts here after reading events from Kafka.

@Repository
public class AlertRepository {
    private final Map<String, List<Alert>> alertDB = new HashMap<>();

    public AlertRepository() {
        List<Alert> farm1 = new ArrayList<>();
        farm1.add(new Alert("A200", "F001", "LOW_SOIL_MOISTURE", "HIGH",
                "Field1 soil moisture below 15%", "2026-10-25T08:15:00Z"));
        farm1.add(new Alert("A201", "F001", "SENSOR_OFFLINE", "MEDIUM",
                "Field1 camera has been offline for 10 minutes", "2026-10-25T09:40:00Z"));
        alertDB.put("F001", farm1);

        List<Alert> farm2 = new ArrayList<>();
        farm2.add(new Alert("A300", "F002", "HIGH_TEMPERATURE", "MEDIUM",
                "Field3 temperature above 38C", "2026-10-25T07:05:00Z"));
        alertDB.put("F002", farm2);
    }

    public boolean farmExists(String farmId) {
        return alertDB.containsKey(farmId);
    }

    public List<Alert> findByFarmId(String farmId) {
        return alertDB.getOrDefault(farmId, new ArrayList<>());
    }
}