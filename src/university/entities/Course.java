package university.entities;

public class Course {
    private final int id;
    private String name;
    private int credits;
    private Teacher teacher;

    public Course(int id, String name, int credits, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", teacher=" + teacher.getName() +
                '}';
    }
}