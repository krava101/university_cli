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

    public boolean updateStudent(int id, String name, String email, String group, int studyYear, StudentStatus status) {
        Student student = getStudentById(id);

        if (student == null) {
            return false;
        }

        student.setName(name);
        student.setEmail(email);
        student.setGroup(group);
        student.setStudyYear(studyYear);
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
}
