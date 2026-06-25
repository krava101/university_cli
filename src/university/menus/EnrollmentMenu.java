package university.menus;

import university.entities.Enrollment;
import university.enums.Grade;
import university.services.CourseService;
import university.services.EnrollmentService;
import university.services.StudentService;
import university.util.InputUtils;

import java.util.Scanner;

public class EnrollmentMenu {
    private final Scanner scanner;
    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentMenu(
            Scanner scanner,
            EnrollmentService enrollmentService,
            StudentService studentService,
            CourseService courseService
    ) {
        this.scanner = scanner;
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public void show() {
        boolean back = false;

        while (!back) {
            printMenu();

            int choice = InputUtils.readInt(scanner, "Оберіть опцію (0-5): ");

            switch (choice) {
                case 1:
                    createEnrollment();
                    break;
                case 2:
                    getEnrollment();
                    break;
                case 3:
                    deleteEnrollment();
                    break;
                case 4:
                    setGradeByScore();
                    break;
                case 5:
                    markPaymentAsPaid();
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
        System.out.println("===== МЕНЮ ЗАРАХУВАНЬ =====");
        System.out.println("1. Створити зарахування студента на курс");
        System.out.println("2. Показати зарахування за ID");
        System.out.println("3. Видалити зарахування");
        System.out.println("4. Поставити оцінку");
        System.out.println("5. Позначити оплату");
        System.out.println("0. Назад");
    }

    private void createEnrollment() {
        try {
            int studentId = InputUtils.readInt(scanner, "Введіть ID студента: ");

            if (studentService.getStudentById(studentId) == null) {
                System.out.println("Зарахування не створено. Студента з таким ID не знайдено.");
                return;
            }

            int courseId = InputUtils.readInt(scanner, "Введіть ID курсу: ");

            if (courseService.getCourseById(courseId) == null) {
                System.out.println("Зарахування не створено. Курс з таким ID не знайдено.");
                return;
            }

            String semester = InputUtils.readString(scanner, "Введіть семестр: ");

            Enrollment enrollment = enrollmentService.createEnrollment(studentId, courseId, semester);

            if (enrollment != null) {
                System.out.println("Зарахування створено успішно. ID: " + enrollment.getId());
            } else {
                System.out.println("Зарахування не створено. Таке зарахування вже існує.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void getEnrollment() {
        int enrollmentId = InputUtils.readInt(scanner, "Введіть ID зарахування: ");

        Enrollment enrollment = enrollmentService.getEnrollment(enrollmentId);

        if (enrollment != null) {
            System.out.println(enrollment);
        } else {
            System.out.println("Зарахування не знайдено.");
        }
    }

    private void deleteEnrollment() {
        int enrollmentId = InputUtils.readInt(scanner, "Введіть ID зарахування: ");

        boolean deleted = enrollmentService.deleteEnrollment(enrollmentId);

        if (deleted) {
            System.out.println("Зарахування успішно видалено.");
        } else {
            System.out.println("Зарахування не знайдено.");
        }
    }

    private void setGradeByScore() {
        try {
            int enrollmentId = InputUtils.readInt(scanner, "Введіть ID зарахування: ");
            int score = InputUtils.readInt(scanner, "Введіть оцінку від 0 до 100: ");

            boolean updated = enrollmentService.setGradeByScore(enrollmentId, score);

            if (updated) {
                Grade grade = Grade.fromScore(score);
                System.out.println("Оцінку успішно виставлено: " + grade);
            } else {
                System.out.println("Зарахування не знайдено. Оцінку не виставлено.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void markPaymentAsPaid() {
        int enrollmentId = InputUtils.readInt(scanner, "Введіть ID зарахування: ");

        boolean updated = enrollmentService.markPaymentAsPaid(enrollmentId);

        if (updated) {
            System.out.println("Оплату успішно позначено.");
        } else {
            System.out.println("Зарахування не знайдено. Оплату не змінено.");
        }
    }
}