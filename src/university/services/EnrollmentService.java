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
    private int nextEnrollmentId = 1;
    public int generateEnrollmentId() {
        return nextEnrollmentId++;
    }

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollments;
    }

    public Enrollment createEnrollment(int studentId, int courseId, String semester) {
        Student student = studentService.getStudentById(studentId);
        Course course = courseService.getCourseById(courseId);

        if (student == null || course == null) {
            return null;
        }

        Enrollment enrollment = new Enrollment(generateEnrollmentId(), student, course, semester);
        enrollments.add(enrollment);

        return enrollment;
    }

    public Enrollment getEnrollment(int enrollmentId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollmentId == enrollment.getId()) {
                return enrollment;
            }
        }

        return null;
    }

    public boolean setGrade(int enrollmentId, Grade grade) {
        Enrollment enrollment = getEnrollment(enrollmentId);

        if (enrollment == null) {
            return false;
        }

        enrollment.setGrade(grade);
        return true;
    }

    public boolean setGradeByScore(int enrollmentId, int score) {
        Enrollment enrollment = getEnrollment(enrollmentId);

        if (enrollment == null) {
            return false;
        }

        Grade grade = Grade.fromScore(score);
        enrollment.setGrade(grade);

        return true;
    }

    public boolean markPaymentAsPaid(int enrollmentId) {
        Enrollment enrollment = getEnrollment(enrollmentId);

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

    public boolean deleteEnrollment(int enrollmentId) {
        Enrollment enrollment = getEnrollment(enrollmentId);

        if (enrollment == null) {
            return false;
        }

        enrollments.remove(enrollment);
        return true;
    }

    public List<Enrollment> getUnpaidEnrollments() {
        List<Enrollment> result = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            if (!enrollment.isPaid()) {
                result.add(enrollment);
            }
        }

        return result;
    }

    public double calcAvgGPAByCourseAndSemester(int courseId, String semester) {
        double total = 0;
        int count = 0;

        for (Enrollment enrollment : enrollments) {
            boolean sameCourse = enrollment.getCourse().getId() == courseId;
            boolean sameSemester = enrollment.getSemester().equalsIgnoreCase(semester);
            boolean hasGrade = enrollment.getGrade() != Grade.NA;

            if (sameCourse && sameSemester && hasGrade) {
                total += enrollment.getGrade().getGpaValue();
                count++;
            }
        }

        if (count == 0) {
            return 0.0;
        }

        return total / count;
    }

    public Student[] getTopStudentsByGPA(int limit) {
        List<Student> allStudents = studentService.getAllStudents();

        Student[] studentsArray = new Student[allStudents.size()];

        for (int i = 0; i < allStudents.size(); i++) {
            studentsArray[i] = allStudents.get(i);
        }

        for (int i = 0; i < studentsArray.length - 1; i++) {
            for (int j = 0; j < studentsArray.length - i - 1; j++) {
                double firstGPA = calculateStudentGPA(studentsArray[j].getId());
                double secondGPA = calculateStudentGPA(studentsArray[j + 1].getId());

                if (firstGPA < secondGPA) {
                    Student temp = studentsArray[j];
                    studentsArray[j] = studentsArray[j + 1];
                    studentsArray[j + 1] = temp;
                }
            }
        }

        int resultSize = Math.min(limit, studentsArray.length);
        Student[] result = new Student[resultSize];

        for (int i = 0; i < resultSize; i++) {
            result[i] = studentsArray[i];
        }

        return result;
    }

    public void printEnrollments(List<Enrollment> toPrint) {
        if (toPrint.isEmpty()) {
            System.out.println("Зарахувань не знайдено.");
            return;
        }

        for (Enrollment enrollment : toPrint) {
            System.out.println(enrollment);
        }
    }
}