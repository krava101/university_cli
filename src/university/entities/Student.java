package university.entities;

import university.enums.StudentStatus;

public class Student extends Person{
    private String group;
    private int studyYear;
    private StudentStatus status;
    private double gpa;

    public Student(int id, String name, String email, String group, int studyYear, StudentStatus status){
        super(id, name, email);
        this.group = group;
        this.studyYear = studyYear;
        this.status = status;
        this.gpa = 0.0;
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }


    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString(){
        return "Student{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", group='" + group + '\'' +
                ", studyYear=" + studyYear +
                ", status=" + status +
                ", gpa=" + gpa +
                '}';
    }
}
