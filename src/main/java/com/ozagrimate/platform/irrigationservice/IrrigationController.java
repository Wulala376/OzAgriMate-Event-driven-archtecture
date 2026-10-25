package com.ozagrimate.platform.irrigationservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController

@RequestMapping ("/irrigation/schedules")

public class IrrigationController {
    private ScheduleService scheduleService;

    public IrrigationController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping 
    public ResponseEntity<?> createSchedule(@RequestBody IrrigationSchedule request) {
        String error = scheduleService.checkNewSchedule(request);

        if (error != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", error));
        }

        IrrigationSchedule created = scheduleService.createSchedule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping ("/{id}")

    public ResponseEntity<?> updateSchedule(@PathVariable String id, @RequestBody IrrigationSchedule changes) {
        IrrigationSchedule existing = scheduleService.findSchedule(id);

        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Schedule " + id + " not found."));
        }

        String error = scheduleService.checkChanges(changes);
        if (error != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", error));
        }

        IrrigationSchedule updated = scheduleService.updateSchedule(existing, changes);
        return ResponseEntity.ok(updated);
    }
}
