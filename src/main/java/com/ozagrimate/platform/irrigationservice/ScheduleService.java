package com.ozagrimate.platform.irrigationservice;

import org.springframework.stereotype.Service;

@Service 

public class ScheduleService {
    private ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public String checkNewSchedule(IrrigationSchedule request) {
        if (request.farmId == null || request.zone == null || request.startTime == null || request.durationMinutes == null) {
            return "Invalid schedule request: Missing required fields.";
        }

        if (request.durationMinutes == null || request.durationMinutes <= 0) {
            return "Invalid schedule request: Duration must be a positive integer.";
        }

        return null;
    }

    public IrrigationSchedule createSchedule(IrrigationSchedule request) {
        request.scheduleId = repository.nextId();
        request.status = "Scheduled";
        repository.save(request);
        return request;
    }

    public IrrigationSchedule findSchedule(String id) {
        return repository.findById(id);
    }

    public String checkChanges(IrrigationSchedule changes) {
        if (changes.durationMinutes != null && changes.durationMinutes <= 0) {
            return "Invalid schedule update: Duration must be a positive integer.";
        }
        return null;
    }

    public IrrigationSchedule updateSchedule(IrrigationSchedule existing, IrrigationSchedule changes) {
        if (changes.startTime != null) {
            existing.startTime = changes.startTime;
        }
        if (changes.durationMinutes != null) {
            existing.durationMinutes = changes.durationMinutes;
        }

        existing.status = "Updated";
        repository.save(existing);
        return existing;
    }
}
