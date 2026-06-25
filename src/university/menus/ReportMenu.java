package university.menus;

import university.entities.Student;
import university.services.EnrollmentService;
import university.services.StudentService;
import university.util.InputUtils;

import java.util.List;
import java.util.Scanner;

public class ReportMenu {
    private final Scanner scanner;
    private final StudentService studentService;
    private final EnrollmentService enrollmentService;

    public ReportMenu(
            Scanner scanner,
            StudentService studentService,
            EnrollmentService enrollmentService
    ) {
        this.scanner = scanner;
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
    }

    public void show() {
        boolean back = false;

        while (!back) {
            printMenu();

            int choice = InputUtils.readInt(scanner, "Оберіть опцію (0-6): ");

            switch (choice) {
                case 1:
                    searchStudents();
                    break;
                case 2:
                    showStudentEnrollments();
                    break;
                case 3:
                    printStudentTranscript();
                    break;
                case 4:
                    showUnpaidEnrollments();
                    break;
                case 5:
                    showAverageGPAByCourseAndSemester();
                    break;
                case 6:
                    showTopStudentsByGPA();
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
        System.out.println("===== ЗВІТИ / ПОШУК =====");
        System.out.println("1. Пошук студента за ПІБ або email");
        System.out.println("2. Переглянути зарахування студента");
        System.out.println("3. Вивести транскрипт студента");
        System.out.println("4. Показати неоплачені зарахування");
        System.out.println("5. Середній GPA по курсу і семестру");
        System.out.println("6. Топ-N студентів за GPA");
        System.out.println("0. Назад");
    }

    private void searchStudents() {
        try {
            String query = InputUtils.readString(scanner, "Введіть частину ПІБ або email: ");

            List<Student> students = studentService.searchStudents(query);
            studentService.printStudents(students);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void showStudentEnrollments() {
        int studentId = InputUtils.readInt(scanner, "Введіть ID студента: ");

        if (studentService.getStudentById(studentId) == null) {
            System.out.println("Студента з таким ID не знайдено.");
            return;
        }

        enrollmentService.printEnrollments(
                enrollmentService.getEnrollmentsByStudentId(studentId)
        );
    }

    private void printStudentTranscript() {
        int studentId = InputUtils.readInt(scanner, "Введіть ID студента: ");

        enrollmentService.printStudentTranscript(studentId);
    }

    private void showUnpaidEnrollments() {
        enrollmentService.printEnrollments(enrollmentService.getUnpaidEnrollments());
    }

    private void showAverageGPAByCourseAndSemester() {
        try {
            int courseId = InputUtils.readInt(scanner, "Введіть ID курсу: ");
            String semester = InputUtils.readString(scanner, "Введіть семестр: ");

            double averageGPA = enrollmentService.calcAvgGPAByCourseAndSemester(courseId, semester);

            System.out.printf(
                    "Середній GPA по курсу ID %d за семестр \"%s\": %.2f%n",
                    courseId,
                    semester,
                    averageGPA
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void showTopStudentsByGPA() {
        try {
            int limit = InputUtils.readInt(scanner, "Введіть кількість студентів у топі: ");

            Student[] topStudents = enrollmentService.getTopStudentsByGPA(limit);

            if (topStudents.length == 0) {
                System.out.println("Студентів не знайдено.");
                return;
            }

            System.out.println("===== ТОП СТУДЕНТІВ ЗА GPA =====");

            for (int i = 0; i < topStudents.length; i++) {
                Student student = topStudents[i];
                double gpa = enrollmentService.calculateStudentGPA(student.getId());

                System.out.printf(
                        "%d. ID: %d | ПІБ: %s | Email: %s | GPA: %.2f%n",
                        i + 1,
                        student.getId(),
                        student.getName(),
                        student.getEmail(),
                        gpa
                );
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}