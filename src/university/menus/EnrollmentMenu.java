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

    public EnrollmentMenu(Scanner scanner, EnrollmentService enrollmentService, StudentService studentService, CourseService courseService) {
        this.scanner = scanner;
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService =  courseService;
    }

    public void show() {
        boolean back = false;

        while (!back) {
            printMenu();

            int choice = InputUtils.readInt(scanner, "Оберіть опцію (0-6): ");

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
                case 6:
                    showStudentEnrollments();
                    break;
                case 7:
                    printStudentTranscript();
                    break;
                case 8:
                    showAllEnrollments();
                    break;
                case 9:
                    showAllUnpaidEnrollments();
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
        System.out.println("2. Показати зарахування");
        System.out.println("3. Видалити зарахування");
        System.out.println("4. Поставити оцінку");
        System.out.println("5. Позначити оплату");
        System.out.println("6. Переглянути зарахування студента");
        System.out.println("7. Вивести транскрипт студента");
        System.out.println("8. Показати всі зарахування");
        System.out.println("9. Показати всі неоплачені зарахування");
        System.out.println("0. Назад");
    }

    private void createEnrollment() {
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
            System.out.println("Зарахування не створено. Перевірте ID студента або ID курсу.");
        }
    }

    private void getEnrollment() {
        int enrollmentId = InputUtils.readInt(scanner, "Введіть ID зарахування: ");

        Enrollment enrollment = enrollmentService.getEnrollment(enrollmentId);

        if (enrollment != null) {
            System.out.println(enrollment.toString());
        } else {
            System.out.println("Зарахування не знайдено.");
        }
    }

    public void deleteEnrollment(){
        int enrollmentId = InputUtils.readInt(scanner, "Введіть ID зарахування: ");

        boolean deleted = enrollmentService.deleteEnrollment(enrollmentId);

        if (deleted){
            System.out.println("Зарахування успішно видалено.");
        } else {
            System.out.println("Зарахування не знайдено.");
        }
    }

    private void setGradeByScore() {
        int enrolmentId = InputUtils.readInt(scanner, "Введіть ID зарахування: ");
        int score = InputUtils.readInt(scanner, "Введіть оцінку від 0 до 100: ");

        boolean updated = enrollmentService.setGradeByScore(enrolmentId, score);

        if (updated) {
            Grade grade = Grade.fromScore(score);
            System.out.println("Оцінку успішно виставлено: " + grade);
        } else {
            System.out.println("Зарахування не знайдено. Оцінку не виставлено.");
        }
    }

    private void markPaymentAsPaid() {
        int enrolmentId = InputUtils.readInt(scanner, "Введіть ID зарахування: ");

        boolean updated = enrollmentService.markPaymentAsPaid(enrolmentId);

        if (updated) {
            System.out.println("Оплату успішно позначено.");
        } else {
            System.out.println("Зарахування не знайдено. Оплату не змінено.");
        }
    }

    private void showStudentEnrollments() {
        int studentId = InputUtils.readInt(scanner, "Введіть ID студента: ");

        enrollmentService.printEnrollments(enrollmentService.getEnrollmentsByStudentId(studentId));
    }

    private void showAllUnpaidEnrollments(){
        enrollmentService.printEnrollments(enrollmentService.getUnpaidEnrollments());
    }

    private void printStudentTranscript() {
        int studentId = InputUtils.readInt(scanner, "Введіть ID студента: ");

        enrollmentService.printStudentTranscript(studentId);
    }

    private void showAllEnrollments() {
        enrollmentService.printEnrollments(enrollmentService.getAllEnrollments());
    }
}
