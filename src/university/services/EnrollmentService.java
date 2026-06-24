package university.services;

import university.entities.Course;
import university.entities.Enrollment;
import university.entities.Student;
import university.enums.Grade;
import university.util.GPAUtils;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {
    private final List<Enrollment> enrollments = new ArrayList<>();

    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollments;
    }

    public boolean createEnrollment(int studentId, int courseId, String semester) {
        Student student = studentService.getStudentById(studentId);
        Course course = courseService.getCourseById(courseId);

        if (student == null || course == null) {
            return false;
        }

        Enrollment existingEnrollment = getEnrollment(studentId, courseId, semester);

        if (existingEnrollment != null) {
            return false;
        }

        Enrollment enrollment = new Enrollment(student, course, semester);
        enrollments.add(enrollment);

        return true;
    }

    public Enrollment getEnrollment(int studentId, int courseId, String semester) {
        for (Enrollment enrollment : enrollments) {
            boolean sameStudent = enrollment.getStudent().getId() == studentId;
            boolean sameCourse = enrollment.getCourse().getId() == courseId;
            boolean sameSemester = enrollment.getSemester().equalsIgnoreCase(semester);

            if (sameStudent && sameCourse && sameSemester) {
                return enrollment;
            }
        }

        return null;
    }

    public boolean setGrade(int studentId, int courseId, String semester, Grade grade) {
        Enrollment enrollment = getEnrollment(studentId, courseId, semester);

        if (enrollment == null) {
            return false;
        }

        enrollment.setGrade(grade);
        return true;
    }

    public boolean setGradeByScore(int studentId, int courseId, String semester, int score) {
        Enrollment enrollment = getEnrollment(studentId, courseId, semester);

        if (enrollment == null) {
            return false;
        }

        Grade grade = Grade.fromScore(score);
        enrollment.setGrade(grade);

        return true;
    }

    public boolean markPaymentAsPaid(int studentId, int courseId, String semester) {
        Enrollment enrollment = getEnrollment(studentId, courseId, semester);

        if (enrollment == null) {
            return false;
        }

        enrollment.markAsPaid();
        return true;
    }

    public List<Enrollment> getEnrollmentsByStudentId(int studentId) {
        List<Enrollment> studentEnrollments = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().getId() == studentId) {
                studentEnrollments.add(enrollment);
            }
        }

        return studentEnrollments;
    }

    public double calculateStudentGPA(int studentId) {
        List<Enrollment> studentEnrollments = getEnrollmentsByStudentId(studentId);
        return GPAUtils.calculateGPA(studentEnrollments);
    }

    public void printStudentTranscript(int studentId) {
        Student student = studentService.getStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        List<Enrollment> studentEnrollments = getEnrollmentsByStudentId(studentId);

        System.out.println("==========================================");
        System.out.println("STUDENT TRANSCRIPT");
        System.out.println("Student ID: " + student.getId());
        System.out.println("Name: " + student.getName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Group: " + student.getGroup());
        System.out.println("Study year: " + student.getStudyYear());
        System.out.println("Status: " + student.getStatus());
        System.out.println("------------------------------------------");

        if (studentEnrollments.isEmpty()) {
            System.out.println("No enrollments found.");
        } else {
            for (Enrollment enrollment : studentEnrollments) {
                Course course = enrollment.getCourse();

                System.out.println("Course: " + course.getName());
                System.out.println("Credits: " + course.getCredits());
                System.out.println("Teacher: " + course.getTeacher().getName());
                System.out.println("Semester: " + enrollment.getSemester());
                System.out.println("Grade: " + enrollment.getGrade());
                System.out.println("GPA value: " + enrollment.getGrade().getGpaValue());
                System.out.println("Paid: " + enrollment.isPaid());
                System.out.println("------------------------------------------");
            }

            System.out.println("GPA: " + calculateStudentGPA(studentId));
        }

        System.out.println("==========================================");
    }

    public boolean deleteEnrollment(int studentId, int courseId, String semester) {
        Enrollment enrollment = getEnrollment(studentId, courseId, semester);

        if (enrollment == null) {
            return false;
        }

        enrollments.remove(enrollment);
        return true;
    }
}