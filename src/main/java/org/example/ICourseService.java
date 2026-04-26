package org.example;

import java.util.List;

public interface ICourseService {
    void addCourse(Course course) throws DuplicateIdException;
    boolean updateCourse(String courseCode, String courseName, int units, double feePerUnit);
    boolean removeCourse(String courseCode);
    List<Course> getAllCourses();
    Course findCourseByCode(String courseCode);
    boolean courseExists(String courseCode);
}