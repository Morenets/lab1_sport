package org.example;

import java.time.LocalDateTime;

public class VisitRecord {
    private int visitorId;
    private LocalDateTime dateTime;

    public VisitRecord(int visitorId, LocalDateTime dateTime) {
        this.visitorId = visitorId;
        this.dateTime = dateTime;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Visitor ID: " + visitorId + ", Date: " + dateTime.toString();
    }
}