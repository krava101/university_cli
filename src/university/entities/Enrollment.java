package university.entities;

import university.enums.Grade;

public class Enrollment {
    private Student student;
    private Course course;
    private Grade grade;
    private boolean paid;

    public Enrollment(Student student, Course course, Grade grade, boolean paid) {
        this.student = student;
        this.course = course;
        this.grade = grade;
        this.paid = paid;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "student=" + student.getName() +
                ", course=" + course.getName() +
                ", grade=" + grade +
                ", paid=" + paid +
                '}';
    }
}
