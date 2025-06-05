package org.example;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;


public class ConsoleMenu {
    public static void main(String[] args) {
        Gym gym = new Gym();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Додати відвідувача");
            System.out.println("2. Видалити відвідувача");
            System.out.println("3. Додати тренера");
            System.out.println("4. Відвідати зал");
            System.out.println("5. Переглянути історію відвідувань");
            System.out.println("6. Переглянути всіх відвідувачів");
            System.out.println("7. Переглянути всіх тренерів");
            System.out.println("8. Додати підписку відвідувачу");
            System.out.println("9. Експортувати відвідувачів у файл");
            System.out.println("10. Імпортувати відвідувачів з файлу");
            System.out.println("0. Вийти");
            System.out.print("Оберіть опцію: ");
            int choice = Integer.parseInt(scanner.nextLine());
            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("ID: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("Ім'я: ");
                        String name = scanner.nextLine();

                        if (gym.getVisitorById(id) != null) {
                            System.out.println("Відвідувач з таким ID вже існує. Додати з новим ID? (y/n): ");
                            String answer = scanner.nextLine();
                            if (answer.equalsIgnoreCase("y")) {
                                id = getNextAvailableVisitorId(gym);
                                System.out.println("Новий ID: " + id);
                            } else {
                                break;
                            }
                        }

                        gym.addVisitor(new Visitor(id, name));
                        System.out.println("Додано відвідувача.");
                    }
                    case 2 -> {
                        System.out.print("ID для видалення: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        gym.removeVisitor(id);
                        System.out.println("Видалено відвідувача.");
                    }
                    case 3 -> {
                        System.out.print("ID тренера: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("Ім'я тренера: ");
                        String name = scanner.nextLine();
                        System.out.print("Спеціалізація: ");
                        String spec = scanner.nextLine();
                        gym.addTrainer(new Trainer(id, name, spec));
                        System.out.println("Додано тренера.");
                    }
                    case 4 -> {
                        System.out.print("ID відвідувача для відвідування: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        gym.recordVisit(id);
                        System.out.println("Відвідування зареєстровано.");
                    }
                    case 5 -> {
                        System.out.print("ID відвідувача: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        for (VisitRecord record : gym.getVisitHistoryForVisitor(id)) {
                            System.out.println(record);
                        }
                    }
                    case 6 -> {
                        System.out.println("Список відвідувачів:");
                        for (Visitor v : gym.getVisitors()) {
                            System.out.println(v.getId() + ": " + v.getName());
                        }
                    }
                    case 7 -> {
                        System.out.println("Список тренерів:");
                        for (Trainer t : gym.getTrainers()) {
                            System.out.println(t.getId() + ": " + t);
                        }
                    }
                    case 8 -> {
                        System.out.print("ID відвідувача: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        Visitor visitor = gym.getVisitorById(id);
                        System.out.print("Тип підписки: ");
                        String type = scanner.nextLine();
                        System.out.print("Дата початку (рррр-мм-дд): ");
                        LocalDate start = LocalDate.parse(scanner.nextLine());
                        System.out.print("Дата завершення (рррр-мм-дд): ");
                        LocalDate end = LocalDate.parse(scanner.nextLine());
                        System.out.print("К-сть сесій: ");
                        int sessions = Integer.parseInt(scanner.nextLine());
                        visitor.setSubscription(new Subscription(type, start, end, sessions));
                        System.out.println("Підписку додано.");
                    }
                    case 9 -> {
                        System.out.print("Ім'я файлу для експорту (наприклад, visitors.json): ");
                        String filename = scanner.nextLine();
                        File file = new File(filename);

                        System.out.println("Сортування: 1 - за ID, 2 - за ім'ям");
                        int sortOption = Integer.parseInt(scanner.nextLine());

                        List<Visitor> sortedVisitors = gym.getVisitors();
                        if (sortOption == 1) {
                            sortedVisitors.sort(Comparator.comparingInt(Visitor::getId));
                        } else if (sortOption == 2) {
                            sortedVisitors.sort(Comparator.comparing(Visitor::getName));
                        }

                        GymIO.exportVisitorsToFile(sortedVisitors, file);
                        System.out.println("Відвідувачі експортовані у " + filename);
                    }
                    case 10 -> {
                        System.out.print("Ім'я файлу для імпорту (наприклад, visitors.json): ");
                        String filename = scanner.nextLine();
                        File file = new File(filename);
                        GymIO.importVisitorsFromFile(gym, file);
                        System.out.println("Відвідувачі імпортовані з " + filename);
                    }
                    case 0 -> {
                        System.out.println("Вихід...");
                        return;
                    }
                    default -> System.out.println("Невірна опція");
                }
            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
            }


        }
    }
    private static int getNextAvailableVisitorId(Gym gym) {
        int maxId = 0;
        for (Visitor v : gym.getVisitors()) {
            if (v.getId() > maxId) {
                maxId = v.getId();
            }
        }
        return maxId + 1;
    }
}
