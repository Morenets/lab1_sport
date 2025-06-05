package org.example;

import java.time.LocalDate;

public class Subscription {
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private int remainingSessions;

    public Subscription(String type, LocalDate startDate, LocalDate endDate, int remainingSessions) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remainingSessions = remainingSessions;
    }

    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return !today.isBefore(startDate) && !today.isAfter(endDate) && remainingSessions > 0;
    }

    public void useSession() {
        if (remainingSessions <= 0) throw new IllegalStateException("No remaining sessions");
        remainingSessions--;
    }

    // Getters and setters...
    public String getType() { return type; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public int getRemainingSessions() { return remainingSessions; }

    @Override
    public String toString() {
        return type + " from " + startDate + " to " + endDate + ", sessions left: " + remainingSessions;
    }
}
