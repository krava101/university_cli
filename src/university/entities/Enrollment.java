package university.entities;

import university.enums.Grade;
import university.interfaces.Payable;

public class Enrollment implements Payable {
    private final int id;
    private final Student student;
    private final Course course;
    private final String semester;
    private Grade grade;
    private boolean paid;

    public Enrollment(int id, Student student, Course course, String semester) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.grade = Grade.NA;
        this.paid = false;
    }
    public int getId() {return id;}

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public boolean isPaid() {
        return paid;
    }

    @Override
    public void markAsPaid() {
        this.paid = true;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                "studentId=" + student.getId() +
                ", studentName='" + student.getName() + '\'' +
                ", courseId=" + course.getId() +
                ", courseName='" + course.getName() + '\'' +
                ", semester='" + semester + '\'' +
                ", grade=" + grade +
                ", gpaValue=" + grade.getGpaValue() +
                ", paid=" + paid +
                '}';
    }
}
