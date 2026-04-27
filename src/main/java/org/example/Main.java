package org.example;

import java.util.List;

public class Main {

    static IStudentService    studentService    = new StudentServiceImpl();
    static IInstructorService instructorService = new InstructorServiceImpl();
    static ICourseService     courseService     = new CourseServiceImpl();
    static ITuitionService    tuitionService    = new TuitionServiceImpl();
    static IEnrollmentService enrollmentService = new EnrollmentServiceImpl(instructorService, courseService);

    public static void main(String[] args) {
        loadSampleData();

        StudentMenu    studentMenu    = new StudentMenu(studentService);
        InstructorMenu instructorMenu = new InstructorMenu(instructorService, enrollmentService);
        CourseMenu     courseMenu     = new CourseMenu(courseService);
        EnrollmentMenu enrollmentMenu = new EnrollmentMenu(enrollmentService, studentService);
        TuitionMenu    tuitionMenu    = new TuitionMenu(tuitionService, studentService, courseService);

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = InputHelper.readInt("Enter choice: ");
            if      (choice == 1) studentMenu.show();
            else if (choice == 2) instructorMenu.show();
            else if (choice == 3) courseMenu.show();
            else if (choice == 4) enrollmentMenu.show();
            else if (choice == 5) tuitionMenu.show();
            else if (choice == 6) showHierarchyMenu();
            else if (choice == 0) { System.out.println("\nGoodbye!"); running = false; }
            else System.out.println("Invalid choice. Please try again.");
        }
    }

    static void printMainMenu() {
        System.out.println("\n+======================================+");
        System.out.println("|   ENROLLMENT MANAGEMENT SYSTEM       |");
        System.out.println("+======================================+");
        System.out.println("|  1. Student Management               |");
        System.out.println("|  2. Instructor Management            |");
        System.out.println("|  3. Course Management                |");
        System.out.println("|  4. Enrollment Management            |");
        System.out.println("|  5. Tuition Fee Management           |");
        System.out.println("|  6. View Department Hierarchy        |");
        System.out.println("|  0. Exit                             |");
        System.out.println("+======================================+");
    }

    static void showHierarchyMenu() {
        List<Department> depts = enrollmentService.getAllDepartments();
        if (depts.isEmpty()) {
            System.out.println("No departments found.");
            return;
        }
        System.out.println("\n[Select a Department]");
        for (int i = 0; i < depts.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, depts.get(i).getDepartmentName());
        }
        int idx = InputHelper.readInt("Choice (0 to cancel): ");
        if (idx == 0 || idx > depts.size()) return;
        enrollmentService.viewDepartmentHierarchy(depts.get(idx - 1));
    }

    static void loadSampleData() {
        System.out.println("Loading sample data...");
        try {
            courseService.addCourse(new Course("CS101", "Introduction to Programming", 3, 500.00));
            courseService.addCourse(new Course("CS102", "Data Structures", 3, 550.00, "CS101"));
            courseService.addCourse(new Course("CS201", "Object-Oriented Programming", 3, 600.00, "CS101"));
            courseService.addCourse(new Course("MATH101", "Mathematics in the Modern World", 3, 400.00));
        } catch (DuplicateIdException e) { /* skip */ }

        try {
            studentService.addStudent(new Student("S001", "Maria", "Santos", "maria@email.com"));
            studentService.addStudent(new Student("S002", "Juan", "Dela Cruz", "juan@email.com"));
            studentService.addStudent(new Student("S003", "Ana", "Reyes", "ana@email.com"));
        } catch (DuplicateIdException e) { /* skip */ }

        try {
            instructorService.addInstructor(new Instructor("I001", "Roberto", "Lim", "CCS"));
            instructorService.addInstructor(new Instructor("I002", "Carla", "Bautista", "CCS"));
        } catch (DuplicateIdException e) { /* skip */ }

        Department ccs = new Department("DCCS", "College of Computer Studies");
        enrollmentService.addDepartment(ccs);

        Section sec1A = new Section("SEC-1A", "BSIT-1A", 30, "CS101");
        Section sec1B = new Section("SEC-1B", "BSIT-1B", 3,  "CS101");
        enrollmentService.addSectionToDepartment("DCCS", sec1A);
        enrollmentService.addSectionToDepartment("DCCS", sec1B);

        instructorService.assignInstructorToSection("I001", sec1A);
        instructorService.assignInstructorToSection("I002", sec1B);

        Student maria = studentService.findStudentById("S001");
        tuitionService.assignTuitionToStudent(maria,
                tuitionService.calculateFee(3, 500.00));

        System.out.println("Sample data loaded!\n");
    }
}