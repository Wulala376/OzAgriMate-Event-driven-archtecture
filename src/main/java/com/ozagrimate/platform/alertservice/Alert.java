package com.ozagrimate.platform.alertservice;

public class Alert {
    public String alertId;
    public String farmId;
    public String type;
    public String severity;
    public String message;
    public String createdAt;

    public Alert(String alertId, String farmId, String type, String severity, String message, String createdAt) {
        this.alertId = alertId;
        this.farmId = farmId;
        this.type = type;
        this.severity = severity;
        this.message = message;
        this.createdAt = createdAt;
    }
}