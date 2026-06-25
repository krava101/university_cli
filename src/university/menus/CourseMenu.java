package university.menus;

import university.entities.Course;
import university.entities.Teacher;
import university.services.CourseService;
import university.services.TeacherService;
import university.util.InputUtils;

import java.util.List;
import java.util.Scanner;

public class CourseMenu {
    private final Scanner scanner;
    private final CourseService courseService;
    private final TeacherService teacherService;

    public CourseMenu(Scanner scanner, CourseService courseService, TeacherService teacherService) {
        this.scanner = scanner;
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    public void show() {
        boolean back = false;

        while (!back) {
            printMenu();

            int choice = InputUtils.readInt(scanner, "Оберіть опцію (0-5): ");

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    courseService.printCourses(courseService.getAllCourses());
                    break;
                case 3:
                    updateCourse();
                    break;
                case 4:
                    deleteCourse();
                    break;
                case 5:
                    filterByCredits();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Невірна опція. Спробуйте ще раз.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("======== МЕНЮ КУРСІВ ========");
        System.out.println("1. Додати курс");
        System.out.println("2. Показати всі курси");
        System.out.println("3. Оновити дані курсу");
        System.out.println("4. Видалити курс");
        System.out.println("5. Фільтр за кількістю кредитів");
        System.out.println("0. Назад");
    }

    private void addCourse() {
        try {
            int id = courseService.generateCourseId();

            String name = InputUtils.readString(scanner, "Введіть назву курсу: ");
            int credits = InputUtils.readInt(scanner, "Введіть кількість кредитів: ");

            Teacher teacher = chooseTeacher();

            if (teacher == null) {
                System.out.println("Невдалось створити курс, оскільки викладача не знайдено.");
                return;
            }

            Course course = new Course(id, name, credits, teacher);
            courseService.addCourse(course);

            System.out.printf("Курс з ID: %d успішно додано.%n", id);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void updateCourse() {
        try {
            int id = InputUtils.readInt(scanner, "Введіть ID курсу для оновлення: ");

            Course existingCourse = courseService.getCourseById(id);

            if (existingCourse == null) {
                System.out.printf("Курс з ID: %d не знайдено.%n", id);
                return;
            }

            String name = InputUtils.readString(scanner, "Введіть нову назву курсу: ");
            int credits = InputUtils.readInt(scanner, "Введіть нову кількість кредитів: ");

            Teacher teacher = chooseTeacher();

            if (teacher == null) {
                System.out.println("Дані курсу не оновлено, оскільки викладача не знайдено.");
                return;
            }

            boolean updated = courseService.updateCourse(id, name, credits, teacher);

            if (updated) {
                System.out.println("Дані курсу успішно оновлено.");
            } else {
                System.out.println("Невдалось оновити дані курсу.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void deleteCourse() {
        int id = InputUtils.readInt(scanner, "Введіть ID курсу для видалення: ");

        boolean deleted = courseService.deleteCourse(id);

        if (deleted) {
            System.out.println("Курс успішно видалено.");
        } else {
            System.out.println("Курс не знайдено.");
        }
    }

    private void filterByCredits() {
        try {
            int credits = InputUtils.readInt(scanner, "Введіть кількість кредитів: ");
            courseService.printCourses(courseService.filterByCredits(credits));
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private Teacher chooseTeacher() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        if(teachers.isEmpty()){
            System.out.println("Наразі немає викладачів!");
            return null;
        }
        System.out.println("Доступні викладачі:");
        teacherService.printTeachers(teachers);

        int teacherId = InputUtils.readInt(scanner, "Введіть ID викладача для курсу: ");

        return teacherService.getTeacherById(teacherId);
    }
}