package org.example;
import java.util.*;

public class Visitor implements JsonSerializable<Visitor> {
    private int id;
    private String name;
    private Subscription subscription;
    private List<VisitRecord> visits = new ArrayList<>();

    public Visitor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void visitGym() {
        if (subscription == null || !subscription.isActive()) {
            throw new IllegalStateException("Subscription is not active");
        }
        subscription.useSession();
        visits.add(new VisitRecord(id, java.time.LocalDateTime.now()));
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public List<VisitRecord> getVisits() {
        return visits;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Subscription getSubscription() { return subscription; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return id == visitor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toJson() {
        return String.format("{\"id\":%d,\"name\":\"%s\"}", id, name);
    }

    @Override
    public Visitor fromJson(String json) {
        int idStart = json.indexOf("\"id\":") + 5;
        int idEnd = json.indexOf(',', idStart);
        int nameStart = json.indexOf("\"name\":\"") + 8;
        int nameEnd = json.indexOf('"', nameStart);
        int id = Integer.parseInt(json.substring(idStart, idEnd));
        String name = json.substring(nameStart, nameEnd);
        return new Visitor(id, name);
    }
}