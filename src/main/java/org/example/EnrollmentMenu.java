package org.example;

import java.util.List;

public class EnrollmentMenu {
    private IEnrollmentService enrollmentService;
    private IStudentService    studentService;

    public EnrollmentMenu(IEnrollmentService enrollmentService, IStudentService studentService) {
        this.enrollmentService = enrollmentService;
        this.studentService    = studentService;
    }

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n-- Enrollment Management --");
            System.out.println("  1. Add Department");
            System.out.println("  2. Add Section to Department");
            System.out.println("  3. Enroll Student in Section");
            System.out.println("  4. Remove Student from Section");
            System.out.println("  5. Record Passed Course");
            System.out.println("  6. View All Departments");
            System.out.println("  0. Back");
            int ch = InputHelper.readInt("Choice: ");
            if      (ch == 1) addDepartment();
            else if (ch == 2) addSection();
            else if (ch == 3) enrollStudent();
            else if (ch == 4) unenrollStudent();
            else if (ch == 5) recordPassedCourse();
            else if (ch == 6) listDepartments();
            else if (ch == 0) back = true;
            else System.out.println("Invalid choice.");
        }
    }

    private void addDepartment() {
        String id   = InputHelper.readString("Department ID   : ");
        String name = InputHelper.readString("Department Name : ");
        enrollmentService.addDepartment(new Department(id, name));
    }

    private void addSection() {
        String deptId      = InputHelper.readString("Department ID   : ");
        String sectionId   = InputHelper.readString("Section ID      : ");
        String sectionName = InputHelper.readString("Section Name    : ");
        int    capacity    = InputHelper.readInt   ("Max Capacity    : ");
        String courseCode  = InputHelper.readString("Course Code     : ");
        Section s = new Section(sectionId, sectionName, capacity, courseCode.toUpperCase());
        enrollmentService.addSectionToDepartment(deptId, s);
    }

    private void enrollStudent() {
        String studentId = InputHelper.readString("Student ID  : ");
        String sectionId = InputHelper.readString("Section ID  : ");
        Student student  = studentService.findStudentById(studentId);
        Section section  = enrollmentService.findSectionById(sectionId);
        if (student == null) { System.out.println("Student not found."); return; }
        if (section == null) { System.out.println("Section not found."); return; }
        try {
            enrollmentService.enrollStudentInSection(student, section);
        } catch (SectionFullException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (PrerequisiteNotMetException e) {
            System.out.println("PREREQUISITE ERROR: " + e.getMessage());
        }
    }

    private void unenrollStudent() {
        String studentId = InputHelper.readString("Student ID : ");
        String sectionId = InputHelper.readString("Section ID : ");
        Student student  = studentService.findStudentById(studentId);
        Section section  = enrollmentService.findSectionById(sectionId);
        if (student == null) { System.out.println("Student not found."); return; }
        if (section == null) { System.out.println("Section not found."); return; }
        enrollmentService.removeStudentFromSection(student, section);
    }

    private void recordPassedCourse() {
        String studentId  = InputHelper.readString("Student ID    : ");
        String courseCode = InputHelper.readString("Course Code   : ");
        Student student   = studentService.findStudentById(studentId);
        if (student == null) { System.out.println("Student not found."); return; }
        student.addPassedCourse(courseCode.toUpperCase());
        System.out.println("Recorded: " + courseCode.toUpperCase() + " for " + student.getFullName());
    }

    private void listDepartments() {
        List<Department> list = enrollmentService.getAllDepartments();
        if (list.isEmpty()) { System.out.println("No departments found."); return; }
        System.out.println("\n[All Departments]");
        for (Department d : list) System.out.println("  " + d);
    }
}