package university.services;

import university.entities.Student;
import university.enums.StudentStatus;
import university.util.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> students = new ArrayList<>();
    //проста імітація генерації id
    private int nextStudentId = 1;
    public int generateStudentId() {
        return nextStudentId++;
    }

    public List<Student> getAllStudents(){
        return students;
    }

    public void addStudent(Student student) {
        ValidationUtils.validateName(student.getName());
        ValidationUtils.validateEmail(student.getEmail());
        ValidationUtils.validateStudyYear(student.getStudyYear());
        ValidationUtils.validateGroup(student.getGroup());

        students.add(student);
    }

    public Student getStudentById(int id){
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public boolean updateStudent(int id, String name, String email, String group, int studyYear) {
        ValidationUtils.validateName(name);
        ValidationUtils.validateEmail(email);
        ValidationUtils.validateStudyYear(studyYear);
        ValidationUtils.validateGroup(group);

        Student student = getStudentById(id);

        if (student == null) {
            return false;
        }

        student.setName(name);
        student.setEmail(email);
        student.setGroup(group);
        student.setStudyYear(studyYear);

        return true;
    }

    public boolean changeStudentStatus(int id, StudentStatus status) {
        Student student = getStudentById(id);

        if (student == null) {
            return false;
        }

        student.setStatus(status);
        return true;
    }

    public boolean deleteStudent(int id) {
        Student student = getStudentById(id);

        if (student == null) {
            return false;
        }

        students.remove(student);
        return true;
    }

    public List<Student> filterByStatus(StudentStatus status) {
        List<Student> result = new ArrayList<>();

        for (Student student : students) {
            if (student.getStatus() == status) {
                result.add(student);
            }
        }

        return result;
    }

    public List<Student> filterByStudyYear(int studyYear) {
        ValidationUtils.validateStudyYear(studyYear);

        List<Student> result = new ArrayList<>();

        for (Student student : students) {
            if (student.getStudyYear() == studyYear) {
                result.add(student);
            }
        }

        return result;
    }

    public List<Student> sortByName() {
        List<Student> sortedStudents = new ArrayList<>(students);

        for (int i = 0; i < sortedStudents.size() - 1; i++) {
            for (int j = 0; j < sortedStudents.size() - i - 1; j++) {
                String firstName = sortedStudents.get(j).getName();
                String secondName = sortedStudents.get(j + 1).getName();

                if (firstName.compareToIgnoreCase(secondName) > 0) {
                    Student temp = sortedStudents.get(j);
                    sortedStudents.set(j, sortedStudents.get(j + 1));
                    sortedStudents.set(j + 1, temp);
                }
            }
        }

        return sortedStudents;
    }

    public List<Student> searchStudents(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Пошуковий запит не може бути порожнім.");
        }

        List<Student> result = new ArrayList<>();

        String normalizedQuery = query.toLowerCase();

        for (Student student : students) {
            String name = student.getName().toLowerCase();
            String email = student.getEmail().toLowerCase();

            if (name.contains(normalizedQuery) || email.contains(normalizedQuery)) {
                result.add(student);
            }
        }

        return result;
    }

    public void printStudents(List<Student> studentsToPrint) {
        if (studentsToPrint.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student student : studentsToPrint) {
            System.out.println(student);
        }
    }


}
