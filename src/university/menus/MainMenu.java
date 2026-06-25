package university.menus;

import university.services.CourseService;
import university.services.EnrollmentService;
import university.services.StudentService;
import university.services.TeacherService;

import university.util.InputUtils;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner;

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    private final StudentMenu studentMenu;
    private final TeacherMenu teacherMenu;
    private final CourseMenu courseMenu;
    private final EnrollmentMenu enrollmentMenu;
    private final ReportMenu reportMenu ;

    public MainMenu() {
        scanner = new Scanner(System.in);

        studentService = new StudentService();
        teacherService = new TeacherService();
        courseService = new CourseService();
        enrollmentService = new EnrollmentService(studentService, courseService);
        reportMenu = new ReportMenu(scanner, studentService, enrollmentService);

        studentMenu = new StudentMenu(scanner, studentService);
        teacherMenu = new TeacherMenu(scanner, teacherService);
        courseMenu = new CourseMenu(scanner, courseService, teacherService);
        enrollmentMenu = new EnrollmentMenu(scanner, enrollmentService, studentService, courseService);
    }

    public void show() {
        boolean running = true;

        while (running) {
            printMenu();

            int choice = InputUtils.readInt(scanner, "Виберіть опцію (0-5): ");

            switch (choice) {
                case 1:
                    studentMenu.show();
                    break;
                case 2:
                    teacherMenu.show();
                    break;
                case 3:
                    courseMenu.show();
                    break;
                case 4:
                    enrollmentMenu.show();
                    break;
                case 5:
                    reportMenu.show();
                    break;
                case 0:
                    running = false;
                    System.out.println("Програма завершена.");
                    break;
                default:
                    System.out.println("Виберіть будь ласка одну із запропанованих опцій!");
            }
        }

        scanner.close();
    }

    private void printMenu() {
        System.out.println();
        System.out.println("===== Система керування університетом =====");
        System.out.println("1. Студенти");
        System.out.println("2. Викладачі");
        System.out.println("3. Курси");
        System.out.println("4. Зарахування");
        System.out.println("5. Звіти/Пошук");
        System.out.println("0. Вихід");
    }

}
