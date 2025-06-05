package org.example;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GymIOTests {

    private Gym gym;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        gym = new Gym();
        gym.addVisitor(new Visitor(1, "Alex"));
        gym.addVisitor(new Visitor(2, "Bob"));
        tempFile = File.createTempFile("visitors", ".json"); // Мок-об'єкт
    }

    @AfterEach
    void tearDown() {
        tempFile.delete();
    }

    @Test
    void testExportVisitorsSortedByName() throws IOException {
        GymIO.exportVisitorsToFile(gym.getVisitors(), tempFile);

        List<String> lines = java.nio.file.Files.readAllLines(tempFile.toPath());
        assertTrue(lines.stream().anyMatch(l -> l.contains("\"name\":\"Alex\"")));
        assertTrue(lines.stream().anyMatch(l -> l.contains("\"name\":\"Bob\"")));
        assertTrue(lines.get(1).contains("Alex")); // Перевірка сортування
    }

    @Test
    void testImportVisitors() throws IOException {
        // Спочатку експортуємо
        GymIO.exportVisitorsToFile(gym.getVisitors(), tempFile);

        // Імпортуємо в новий Gym
        Gym importedGym = new Gym();
        GymIO.importVisitorsFromFile(importedGym, tempFile);

        assertEquals(2, importedGym.getVisitors().size());
        assertEquals("Alex", importedGym.getVisitorById(1).getName());
        assertEquals("Bob", importedGym.getVisitorById(2).getName());
    }
}
