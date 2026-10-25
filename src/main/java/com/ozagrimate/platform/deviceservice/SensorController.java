package com.ozagrimate.platform.deviceservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    private final Map<String, Map<String, Object>> sensorDB = new HashMap<>();
    private int idCounter = 101;

    @PostMapping
    public ResponseEntity<?> pairSensor(@RequestBody Map<String, String> request) {
        String newId = "S" + (idCounter++);
        sensorDB.put(newId, Map.of(
            "sensorId", newId,
            "name", request.get("name"),
            "status", "paired"
        ));
        return ResponseEntity.ok(sensorDB.get(newId));
    }
  }