package org.example;

import java.io.*;
import java.util.Comparator;
import java.util.List;

public class GymIO {

    // Експортуємо відвідувачів у JSON-файл, попередньо відсортувавши за ім’ям
    public static void exportVisitorsToFile(List<Visitor> visitors, File file) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("[\n");
            for (int i = 0; i < visitors.size(); i++) {
                writer.write("  " + visitors.get(i).toJson());
                if (i < visitors.size() - 1) writer.write(",");
                writer.write("\n");
            }
            writer.write("]");
        }
    }

    // Імпортуємо лише імена та ID відвідувачів (згідно з toJson Visitor)
    public static void importVisitorsFromFile(Gym gym, File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("{")) {
                    Visitor v = new Visitor(0, ""); // тимчасовий об'єкт
                    gym.addVisitor(v.fromJson(line.trim()));
                }
            }
        }
    }


}
