package university.services;

import university.entities.Teacher;
import university.enums.TeacherPosition;
import university.util.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

public class TeacherService {
    private final List<Teacher> teachers = new ArrayList<>();
    //проста імітація генерації id
    private int nextTeacherId = 1;
    public int generateTeacherId() {
        return nextTeacherId++;
    }

    public List<Teacher> getAllTeachers(){
        return teachers;
    }

    public void addTeacher(Teacher teacher){
        ValidationUtils.validateName(teacher.getName());
        ValidationUtils.validateEmail(teacher.getEmail());

        teachers.add(teacher);
    }

    public Teacher getTeacherById(int id){
        for (Teacher teacher: teachers){
            if(teacher.getId() == id) {
                return teacher;
            }
        }
        return null;
    }

    public boolean updateTeacher(int id, String name, String email, String department, TeacherPosition position){
        ValidationUtils.validateName(name);
        ValidationUtils.validateEmail(email);

        Teacher teacher = getTeacherById(id);
        if(teacher == null){
            return false;
        }
        teacher.setName(name);
        teacher.setEmail(email);
        teacher.setDepartment(department);
        teacher.setPosition(position);

        return true;
    }

    public boolean deleteTeacher(int id){
        Teacher teacher = getTeacherById(id);
        if (teacher == null){
            return false;
        }

        teachers.remove(teacher);
        return true;
    }

    public void printTeachers(List<Teacher> teachersToPrint) {
        if (teachersToPrint.isEmpty()) {
            System.out.println("No teachers found.");
            return;
        }

        for (Teacher teacher : teachersToPrint) {
            System.out.println(teacher);
        }
    }
}
