package com.ozagrimate.platform.deviceservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    private final Map<String, Map<String, Object>> sensorDB = new HashMap<>();
    // Last reading timestamp per sensor (stand-in for the time-series database)
    private final Map<String, String> latestReadingDB = new HashMap<>();
    private int idCounter = 101;

    public SensorController() {
        // Seed data so GET and DELETE have something to work with
        sensorDB.put("S100", Map.of(
                "sensorId", "S100",
                "name", "Field1_Camera",
                "status", "online"));
        latestReadingDB.put("S100", "2026-10-22T09:30:00Z");
    }

    // Pair sensor
    @PostMapping
    public ResponseEntity<?> pairSensor(@RequestBody Map<String, String> request) {
        String newId = "S" + (idCounter++);
        sensorDB.put(newId, Map.of(
                "sensorId", newId,
                "name", request.get("name"),
                "status", "paired"));
        return ResponseEntity.ok(sensorDB.get(newId));
    }

    // Retrieve Sensor status
    @GetMapping("/{id}")
    public ResponseEntity<?> getSensor(@PathVariable("id") String id) {
        Map<String, Object> sensor = sensorDB.get(id);

        if (sensor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Sensor " + id + " not found."));
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("sensorId", sensor.get("sensorId"));
        response.put("status", sensor.get("status"));

        String timestamp = latestReadingDB.get(id);
        response.put("latestReading", timestamp == null ? null : Map.of("timestamp", timestamp));

        return ResponseEntity.ok(response);
    }

    // Delete Sensor
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSensor(@PathVariable("id") String id) {
        if (sensorDB.remove(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Sensor " + id + " not found."));
        }

        latestReadingDB.remove(id);
        return ResponseEntity.noContent().build();
    }
}