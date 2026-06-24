package university.entities;

import university.enums.TeacherPosition;

public class Teacher extends Person{
    private String department;
    private TeacherPosition position;

    public Teacher(int id, String name, String email, String department, TeacherPosition position){
        super(id, name, email);
        this.department = department;
        this.position = position;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public TeacherPosition getPosition() {
        return position;
    }

    public void setPosition(TeacherPosition position) {
        this.position = position;
    }

    @Override
    public String toString(){
        return "Teacher{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", department='" + department + '\'' +
                ", position=" + position +
                '}';
    }
}
