package university.menus;

import university.entities.Teacher;
import university.enums.TeacherPosition;
import university.services.TeacherService;
import university.util.InputUtils;

import java.util.Scanner;

public class TeacherMenu {
    private final Scanner scanner;
    private final TeacherService teacherService;

    public TeacherMenu(Scanner scanner, TeacherService teacherService) {
        this.scanner = scanner;
        this.teacherService = teacherService;
    }

    public void show() {
        boolean back = false;

        while (!back) {
            printMenu();

            int choice = InputUtils.readInt(scanner, "Оберіть опцію: ");

            switch (choice) {
                case 1:
                    addTeacher();
                    break;
                case 2:
                    teacherService.printTeachers(teacherService.getAllTeachers());
                    break;
                case 3:
                    updateTeacher();
                    break;
                case 4:
                    deleteTeacher();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Невірна опція. Спробуйте ще раз. 0-4:");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("===== МЕНЮ ВИКЛАДАЧІВ =====");
        System.out.println("1. Додати викладача");
        System.out.println("2. Показати всіх викладачів");
        System.out.println("3. Оновити дані викладача");
        System.out.println("4. Видалити викладача");
        System.out.println("0. Назад");
    }

    private void addTeacher() {
        try {
            int id = teacherService.generateTeacherId();

            String name = InputUtils.readString(scanner, "Введіть ПІБ викладача: ");
            String email = InputUtils.readString(scanner, "Введіть email: ");
            String department = InputUtils.readString(scanner, "Введіть кафедру: ");
            TeacherPosition position = readTeacherPosition();

            Teacher teacher = new Teacher(id, name, email, department, position);
            teacherService.addTeacher(teacher);

            System.out.printf("Викладача з ID: %d додано успішно.", id);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void updateTeacher() {
        try {
            int id = InputUtils.readInt(scanner, "Введіть ID викладача для оновлення: ");

            Teacher existingTeacher = teacherService.getTeacherById(id);

            if (existingTeacher == null) {
                System.out.println("Викладача не знайдено.");
                return;
            }

            String name = InputUtils.readString(scanner, "Введіть новий ПІБ: ");
            String email = InputUtils.readString(scanner, "Введіть новий email: ");
            String department = InputUtils.readString(scanner, "Введіть нову кафедру: ");
            TeacherPosition position = readTeacherPosition();

            boolean updated = teacherService.updateTeacher(id, name, email, department, position);

            if (updated) {
                System.out.println("Дані викладача успішно оновлено.");
            } else {
                System.out.println("Невдалось оновити дані викладача.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void deleteTeacher() {
        int id = InputUtils.readInt(scanner, "Введіть ID викладача для видалення: ");

        boolean deleted = teacherService.deleteTeacher(id);

        if (deleted) {
            System.out.println("Викладача успішно видалено.");
        } else {
            System.out.printf("Викладача з ID: %d не знайдено.", id);
        }
    }

    private TeacherPosition readTeacherPosition() {
        while (true) {
            System.out.println("Оберіть посаду викладача (1-3):");
            System.out.println("1. ASSISTANT");
            System.out.println("2. LECTURER");
            System.out.println("3. PROFESSOR");

            int choice = InputUtils.readInt(scanner, "Посада: ");

            switch (choice) {
                case 1:
                    return TeacherPosition.ASSISTANT;
                case 2:
                    return TeacherPosition.LECTURER;
                case 3:
                    return TeacherPosition.PROFESSOR;
                default:
                    System.out.println("Невірна посада. Введіть 1-3:");
            }
        }
    }
}
