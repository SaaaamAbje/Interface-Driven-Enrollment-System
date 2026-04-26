package org.example;

import java.util.ArrayList;
import java.util.List;

public class CourseServiceImpl implements ICourseService {

    private List<Course> courses = new ArrayList<>();

    @Override
    public void addCourse(Course course) throws DuplicateIdException {
        if (courseExists(course.getCourseCode())) {
            throw new DuplicateIdException("Course code already exists: " + course.getCourseCode());
        }
        courses.add(course);
        System.out.println("Course added: " + course.getCourseName());
    }

    @Override
    public boolean updateCourse(String courseCode, String courseName, int units, double feePerUnit) {
        Course c = findCourseByCode(courseCode);
        if (c == null) {
            System.out.println("Course not found: " + courseCode);
            return false;
        }
        if (courseName != null && !courseName.isBlank()) c.setCourseName(courseName);
        if (units > 0)      c.setUnits(units);
        if (feePerUnit > 0) c.setFeePerUnit(feePerUnit);
        System.out.println("Course updated: " + c.getCourseName());
        return true;
    }

    @Override
    public boolean removeCourse(String courseCode) {
        Course c = findCourseByCode(courseCode);
        if (c == null) {
            System.out.println("Course not found: " + courseCode);
            return false;
        }
        courses.remove(c);
        System.out.println("Course removed: " + c.getCourseName());
        return true;
    }

    @Override
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    @Override
    public Course findCourseByCode(String courseCode) {
        for (Course c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(courseCode)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public boolean courseExists(String courseCode) {
        return findCourseByCode(courseCode) != null;
    }
}