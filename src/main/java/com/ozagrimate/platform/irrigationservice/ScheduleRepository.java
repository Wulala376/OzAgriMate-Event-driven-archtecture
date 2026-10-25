package com.ozagrimate.platform.irrigationservice;

import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.HashMap;

@Repository
public class ScheduleRepository {
    private Map<String, IrrigationSchedule> scheduleDB = new HashMap<>();
    private int idCounter = 100;
    public String nextId() {
        String newId = "IS" + idCounter;
        idCounter++;
        return newId;
    }

    public void save(IrrigationSchedule schedule) {
        scheduleDB.put(schedule.scheduleId, schedule);
    }

    public IrrigationSchedule findById(String id) {
        return scheduleDB.get(id);
    }
}
