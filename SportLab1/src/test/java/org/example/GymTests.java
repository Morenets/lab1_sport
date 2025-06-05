package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GymTests {

    private Visitor visitor;
    private Subscription subscription;
    private Gym gym;

    @BeforeEach
    public void setup() {
        visitor = new Visitor(1, "Ivan Ivanov");
        subscription = new Subscription("Monthly", LocalDate.now().minusDays(1), LocalDate.now().plusDays(10), 5);
        visitor.setSubscription(subscription);
        gym = new Gym();
    }

    @Test
    public void testVisitorVisitGymDecreasesSession() {
        visitor.visitGym();
        assertEquals(4, subscription.getRemainingSessions());
    }

    @Test
    public void testVisitorVisitGymWithInactiveSubscriptionThrows() {
        Subscription inactive = new Subscription("Expired", LocalDate.now().minusDays(10), LocalDate.now().minusDays(1), 5);
        visitor.setSubscription(inactive);
        assertThrows(IllegalStateException.class, visitor::visitGym);
    }

    @Test
    public void testAddAndRemoveVisitor() {
        gym.addVisitor(visitor);
        assertEquals(1, gym.getVisitors().size());

        gym.removeVisitor(visitor.getId());
        assertEquals(0, gym.getVisitors().size());
    }

    @Test
    public void testAddVisitorWithDuplicateIdThrows() {
        gym.addVisitor(visitor);
        Visitor duplicate = new Visitor(1, "Duplicate");
        assertThrows(IllegalArgumentException.class, () -> gym.addVisitor(duplicate));
    }

    @Test
    public void testAddAndRemoveTrainer() {
        Trainer trainer = new Trainer(1, "Petro Petrov", "Yoga");
        gym.addTrainer(trainer);
        assertEquals(1, gym.getTrainers().size());

        gym.removeTrainer(1);
        assertEquals(0, gym.getTrainers().size());
    }

    @Test
    public void testAddTrainerWithDuplicateIdThrows() {
        Trainer trainer1 = new Trainer(1, "A", "X");
        Trainer trainer2 = new Trainer(1, "B", "Y");
        gym.addTrainer(trainer1);
        assertThrows(IllegalArgumentException.class, () -> gym.addTrainer(trainer2));
    }

    @Test
    public void testRecordVisitAddsHistory() {
        gym.addVisitor(visitor);
        gym.recordVisit(visitor.getId());
        List<VisitRecord> history = gym.getVisitHistoryForVisitor(visitor.getId());
        assertEquals(1, history.size());
        assertEquals(visitor.getId(), history.get(0).getVisitorId());
    }

    @Test
    public void testJsonSerializationOfVisitor() {
        String json = visitor.toJson();
        assertTrue(json.contains("\"id\":1"));
        assertTrue(json.contains("\"name\":\"Ivan Ivanov\""));
    }

    @Test
    public void testFromJsonCreatesCorrectVisitor() {
        String json = "{\"id\":2,\"name\":\"Maria\"}";
        Visitor v = visitor.fromJson(json);
        assertEquals(2, v.getId());
        assertEquals("Maria", v.getName());
    }
}
