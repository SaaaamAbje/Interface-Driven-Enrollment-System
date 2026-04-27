package org.example;

import java.util.List;

public class StudentMenu {
    private IStudentService studentService;

    public StudentMenu(IStudentService studentService) {
        this.studentService = studentService;
    }

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n-- Student Management --");
            System.out.println("  1. Add Student");
            System.out.println("  2. View All Students");
            System.out.println("  3. Update Student");
            System.out.println("  4. Remove Student");
            System.out.println("  0. Back");
            int ch = InputHelper.readInt("Choice: ");
            if      (ch == 1) addStudent();
            else if (ch == 2) listStudents();
            else if (ch == 3) updateStudent();
            else if (ch == 4) removeStudent();
            else if (ch == 0) back = true;
            else System.out.println("Invalid choice.");
        }
    }

    private void addStudent() {
        System.out.println("\n[Add Student]");
        String id    = InputHelper.readString("Student ID   : ");
        String fn    = InputHelper.readString("First Name   : ");
        String ln    = InputHelper.readString("Last Name    : ");
        String email = InputHelper.readString("Email        : ");
        try {
            studentService.addStudent(new Student(id, fn, ln, email));
        } catch (DuplicateIdException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void listStudents() {
        List<Student> list = studentService.getAllStudents();
        if (list.isEmpty()) { System.out.println("No students found."); return; }
        System.out.println("\n[All Students]");
        for (Student s : list) System.out.println("  " + s);
    }

    private void updateStudent() {
        String id    = InputHelper.readString("Student ID to update: ");
        System.out.println("(Leave blank to keep current value)");
        String fn    = InputHelper.readString("New First Name : ");
        String ln    = InputHelper.readString("New Last Name  : ");
        String email = InputHelper.readString("New Email      : ");
        studentService.updateStudent(id,
                fn.isBlank()    ? null : fn,
                ln.isBlank()    ? null : ln,
                email.isBlank() ? null : email);
    }

    private void removeStudent() {
        String id = InputHelper.readString("Student ID to remove: ");
        studentService.removeStudent(id);
    }
}
