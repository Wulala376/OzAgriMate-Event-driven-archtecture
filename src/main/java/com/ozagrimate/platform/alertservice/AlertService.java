package com.ozagrimate.platform.alertservice;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlertService {
    private final AlertRepository repository;

    public AlertService(AlertRepository repository) {
        this.repository = repository;
    }

    public String checkFarmId(String farmId) {
        if (farmId == null || farmId.isBlank()) {
            return "Invalid alert request: farmId query parameter is required.";
        }
        return null;
    }

    public boolean farmExists(String farmId) {
        return repository.farmExists(farmId);
    }

    public List<Alert> findAlerts(String farmId) {
        return repository.findByFarmId(farmId);
    }
}