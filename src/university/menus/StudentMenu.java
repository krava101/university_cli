package university.menus;

import university.entities.Student;
import university.enums.StudentStatus;
import university.services.StudentService;

import university.util.InputUtils;

import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private final Scanner scanner;
    private final StudentService studentService;

    public StudentMenu(Scanner scanner, StudentService studentService) {
        this.scanner = scanner;
        this.studentService = studentService;
    }

    public void show() {
        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("===== МЕНЮ СТУДЕНТІВ =====");
            System.out.println("1. Додати студента");
            System.out.println("2. Показати всіх студентів");
            System.out.println("3. Оновити дані студента");
            System.out.println("4. Видалити студента");
            System.out.println("5. Змінити статус студента");
            System.out.println("6. Фільтр за статусом");
            System.out.println("7. Фільтр за роком навчання");
            System.out.println("8. Сортувати за ПІБ");
            System.out.println("9. Пошук за ПІБ або email");
            System.out.println("0. Назад");

            int choice = InputUtils.readInt(scanner,"Виберіть опцію (0-9): ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    studentService.printStudents(studentService.getAllStudents());
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    changeStudentStatus();
                    break;
                case 6:
                    filterByStatus();
                    break;
                case 7:
                    filterByStudyYear();
                    break;
                case 8:
                    studentService.printStudents(studentService.sortByName());
                    break;
                case 9:
                    searchStudents();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Виберіть будь ласка одну із запропанованих опцій!");
            }
        }
    }

    private void addStudent() {
        try {
            int id = studentService.generateStudentId();
            String name = InputUtils.readString(scanner, "Введіть повне ім'я: ");
            String email = InputUtils.readString(scanner,"Введіть email: ");
            String group = InputUtils.readString(scanner,"Введіть групу: ");
            int studyYear = InputUtils.readInt(scanner,"Введіть рік навчання: ");
            StudentStatus status = readStudentStatus();

            Student student = new Student(id, name, email, group, studyYear, status);
            studentService.addStudent(student);
            System.out.println("Студента додано!");
        } catch (IllegalArgumentException e) {
            System.out.println("Виникла помилка: " + e.getMessage());
        }
    }

    private void updateStudent() {
        try{
            int id = InputUtils.readInt(scanner, "Введіть ID студента інформацію про якого ви хочете змінити: ");

            Student existingStudent = studentService.getStudentById(id);

            if (existingStudent == null) {
                System.out.printf("Студента з ID: %d не знайдено!", id);
                return;
            }

            String name = InputUtils.readString(scanner, "Введіть нове повне ім'я: ");
            String email = InputUtils.readString(scanner, "Введіть новий email: ");
            String group = InputUtils.readString(scanner, "Введіть нову групу: ");
            int studyYear = InputUtils.readInt(scanner, "Введіть новий рік навчання: ");

            boolean updated = studentService.updateStudent(id, name, email, group, studyYear);

            if (updated) {
                System.out.println("Інформацію успішно оновлено!");
            } else {
                System.out.println("Невдалось оновити інформацію!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Виникла помилка: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        int id = InputUtils.readInt(scanner,"Введіть ID студента, щоб видалити: ");

        boolean deleted = studentService.deleteStudent(id);

        if (deleted) {
            System.out.println("Студента видалено!");
        } else {
            System.out.printf("Студента з ID: %d не знайдено!", id);
        }
    }

    private void changeStudentStatus() {
        int id = InputUtils.readInt(scanner,"Введіть ID студента: ");

        Student existingStudent = studentService.getStudentById(id);

        if (existingStudent == null) {
            System.out.printf("Студента з ID: %d не знайдено!", id);
            return;
        }

        StudentStatus status = readStudentStatus();

        boolean changed = studentService.changeStudentStatus(id, status);

        if (changed) {
            System.out.println("Статус успішно оновлено!");
        } else {
            System.out.println("Невдалось оновити статус!");
        }
    }

    private void filterByStatus() {
        StudentStatus status = readStudentStatus();
        studentService.printStudents(studentService.filterByStatus(status));
    }

    private void filterByStudyYear() {
        try {
            int studyYear = InputUtils.readInt(scanner, "Введіть рік навчання: ");
            studentService.printStudents(studentService.filterByStudyYear(studyYear));
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
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

    private StudentStatus readStudentStatus() {
        while (true) {
            System.out.println("Виберіть статус студента:");
            System.out.println("1. ACTIVE");
            System.out.println("2. ON_LEAVE");
            System.out.println("3. EXPELLED");
            System.out.println("4. GRADUATED");

            int choice = InputUtils.readInt(scanner,"Status: ");

            switch (choice) {
                case 1:
                    return StudentStatus.ACTIVE;
                case 2:
                    return StudentStatus.ON_LEAVE;
                case 3:
                    return StudentStatus.EXPELLED;
                case 4:
                    return StudentStatus.GRADUATED;
                default:
                    System.out.println("Виберіть статус студента (1-4):");
            }
        }
    }

}
