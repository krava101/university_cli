package university.util;

public class ValidationUtils {
    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("ПІБ не може бути порожнім.");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email не може бути порожнім.");
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Неправильний формат email.");
        }
    }

    public static void validateStudyYear(int studyYear) {
        if (studyYear < 1) {
            throw new IllegalArgumentException("Рік навчання має бути більше або дорівнювати 1.");
        }
    }

    public static void validateCredits(int credits) {
        if (credits < 1) {
            throw new IllegalArgumentException("Кількість кредитів має бути більше або дорівнювати 1.");
        }
    }

    public static void validateSemester(String semester) {
        if (semester == null || semester.trim().isEmpty()) {
            throw new IllegalArgumentException("Семестр не може бути порожнім.");
        }
    }

    public static void validateGroup(String group) {
        if (group == null || group.trim().isEmpty()) {
            throw new IllegalArgumentException("Група не може бути порожньою.");
        }

        if (group.trim().length() < 2) {
            throw new IllegalArgumentException("Назва групи має містити щонайменше 2 символи.");
        }
    }
}
