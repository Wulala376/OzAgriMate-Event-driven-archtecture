package com.ozagrimate.platform.alertservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/alerts")
public class AlertController {
    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public ResponseEntity<?> getAlerts(@RequestParam(name = "farmId", required = false) String farmId) {
        String error = alertService.checkFarmId(farmId);
        if (error != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", error));
        }

        if (!alertService.farmExists(farmId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Farm " + farmId + " not found."));
        }

        return ResponseEntity.ok(alertService.findAlerts(farmId));
    }
}