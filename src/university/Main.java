package university;

import university.entities.Course;
import university.entities.Student;
import university.entities.Teacher;
import university.enums.Grade;
import university.enums.StudentStatus;
import university.enums.TeacherPosition;
import university.services.CourseService;
import university.services.EnrollmentService;
import university.services.StudentService;
import university.services.TeacherService;

public class Main {
    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        TeacherService teacherService = new TeacherService();
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);

        Student student = new Student(
                101,
                "Oleksii Kravchenko",
                "o.krav.student@neomail.com",
                "JAVA-101",
                1,
                StudentStatus.ACTIVE
        );

        Teacher teacher = new Teacher(
                1,
                "Nina Petrenko",
                "nina@neomail.com",
                "Computer Science",
                TeacherPosition.PROFESSOR
        );

        Course course = new Course(
                1,
                "Java Programming",
                10,
                teacher
        );

        studentService.addStudent(student);
        teacherService.addTeacher(teacher);
        courseService.addCourse(course);

        enrollmentService.createEnrollment(101, 1, "2026 Sep");

        enrollmentService.setGrade(101, 1, "2026 Sep", Grade.A);

        enrollmentService.markPaymentAsPaid(101, 1, "2026 Sep");

        enrollmentService.printStudentTranscript(101);
    }
}
