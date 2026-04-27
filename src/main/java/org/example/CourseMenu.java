package org.example;

import java.util.List;

public class CourseMenu {
    private ICourseService courseService;

    public CourseMenu(ICourseService courseService) {
        this.courseService = courseService;
    }

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n-- Course Management --");
            System.out.println("  1. Add Course");
            System.out.println("  2. View All Courses");
            System.out.println("  3. Update Course");
            System.out.println("  4. Remove Course");
            System.out.println("  0. Back");
            int ch = InputHelper.readInt("Choice: ");
            if      (ch == 1) addCourse();
            else if (ch == 2) listCourses();
            else if (ch == 3) updateCourse();
            else if (ch == 4) removeCourse();
            else if (ch == 0) back = true;
            else System.out.println("Invalid choice.");
        }
    }

    private void addCourse() {
        System.out.println("\n[Add Course]");
        String code   = InputHelper.readString("Course Code   : ");
        String name   = InputHelper.readString("Course Name   : ");
        int    units  = InputHelper.readInt   ("Units         : ");
        double fee    = InputHelper.readDouble("Fee per Unit  : PHP ");
        String prereq = InputHelper.readString("Prerequisite Code (blank if none): ");
        try {
            Course c = prereq.isBlank()
                    ? new Course(code, name, units, fee)
                    : new Course(code, name, units, fee, prereq.toUpperCase());
            courseService.addCourse(c);
        } catch (DuplicateIdException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void listCourses() {
        List<Course> list = courseService.getAllCourses();
        if (list.isEmpty()) { System.out.println("No courses found."); return; }
        System.out.println("\n[All Courses]");
        for (Course c : list) System.out.println("  " + c);
    }

    private void updateCourse() {
        String code  = InputHelper.readString("Course Code to update: ");
        System.out.println("(Enter 0 for numeric fields to keep current value)");
        String name  = InputHelper.readString("New Course Name (blank to keep): ");
        int    units = InputHelper.readInt   ("New Units (0 to keep)          : ");
        double fee   = InputHelper.readDouble("New Fee/Unit  (0 to keep): PHP ");
        courseService.updateCourse(code, name.isBlank() ? null : name, units, fee);
    }

    private void removeCourse() {
        String code = InputHelper.readString("Course Code to remove: ");
        courseService.removeCourse(code);
    }
}
