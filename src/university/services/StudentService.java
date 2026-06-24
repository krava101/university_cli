package university.services;

import university.entities.Student;
import university.enums.StudentStatus;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public List<Student> getAllStudents(){
        return students;
    }

    public void addStudent(Student student) {
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

    public List<Student> searchStudents(String q) {
        List<Student> result = new ArrayList<>();
        String query = q.toLowerCase();

        for (Student student : students) {
            String studentName = student.getName().toLowerCase();
            String studentEmail = student.getEmail().toLowerCase();

            if (studentName.contains(query) || studentEmail.contains(query)) {
                result.add(student);
            }
        }

        return result;
    }


}
