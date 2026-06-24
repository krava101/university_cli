package university.services;

import university.entities.Course;
import university.entities.Teacher;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private final List<Course> courses = new ArrayList<>();

    public List<Course> getAllCourses(){
        return courses;
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public Course getCourseById(int id){
        for(Course course: courses){
            if(course.getId() == id){
                return course;
            }
        }
        return null;
    }

    public boolean updateCourse(int id, String name, int credits, Teacher teacher){
        Course course = getCourseById(id);

        if(course == null){
            return false;
        }

        course.setName(name);
        course.setCredits(credits);
        course.setTeacher(teacher);

        return true;
    }

    public boolean deleteCourse(int id) {
        Course course = getCourseById(id);

        if (course == null) {
            return false;
        }

        courses.remove(course);
        return true;
    }

    public List<Course> filterByCredits(int credits) {
        List<Course> result = new ArrayList<>();

        for (Course course : courses) {
            if (course.getCredits() == credits) {
                result.add(course);
            }
        }

        return result;
    }
}
