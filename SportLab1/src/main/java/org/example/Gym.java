package org.example;
import java.util.*;
import java.util.stream.Collectors;

public class Gym implements JsonSerializable<Gym> {
    private List<Visitor> visitors = new ArrayList<>();
    private List<Trainer> trainers = new ArrayList<>();
    private List<VisitRecord> visitHistory = new ArrayList<>();

    public void addVisitor(Visitor v) {
        if (visitors.stream().anyMatch(vis -> vis.getId() == v.getId()))
            throw new IllegalArgumentException("Visitor with this ID already exists.");
        visitors.add(v);
    }

    public void removeVisitor(int id) {
        visitors.removeIf(v -> v.getId() == id);
    }

    public void addTrainer(Trainer t) {
        if (trainers.stream().anyMatch(tr -> tr.getId() == t.getId()))
            throw new IllegalArgumentException("Trainer with this ID already exists.");
        trainers.add(t);
    }

    public void removeTrainer(int id) {
        trainers.removeIf(t -> t.getId() == id);
    }

    public void recordVisit(int visitorId) {
        Visitor v = getVisitorById(visitorId);
        v.visitGym();
        visitHistory.add(new VisitRecord(visitorId, java.time.LocalDateTime.now()));
    }

    public Visitor getVisitorById(int id) {
        return visitors.stream().filter(v -> v.getId() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Visitor not found"));
    }

    public List<VisitRecord> getVisitHistoryForVisitor(int visitorId) {
        return visitHistory.stream()
                .filter(vr -> vr.getVisitorId() == visitorId)
                .collect(Collectors.toList());
    }

    public List<Visitor> getVisitors() { return visitors; }
    public List<Trainer> getTrainers() { return trainers; }
    public List<VisitRecord> getVisitHistory() { return visitHistory; }


    @Override
    public String toJson() {
        return "{\"visitors\": " + visitors.size() + ", \"trainers\": " + trainers.size() + "}";
    }

    @Override
    public Gym fromJson(String json) {
        throw new UnsupportedOperationException("Manual deserialization not implemented for Gym");
    }
}

