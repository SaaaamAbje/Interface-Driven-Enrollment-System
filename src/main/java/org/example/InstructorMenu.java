package org.example;

import java.util.List;

public class InstructorMenu {
    private IInstructorService instructorService;
    private IEnrollmentService enrollmentService;

    public InstructorMenu(IInstructorService instructorService, IEnrollmentService enrollmentService) {
        this.instructorService = instructorService;
        this.enrollmentService = enrollmentService;
    }

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n-- Instructor Management --");
            System.out.println("  1. Add Instructor");
            System.out.println("  2. View All Instructors");
            System.out.println("  3. Update Instructor");
            System.out.println("  4. Remove Instructor");
            System.out.println("  5. Assign Instructor to Section");
            System.out.println("  0. Back");
            int ch = InputHelper.readInt("Choice: ");
            if      (ch == 1) addInstructor();
            else if (ch == 2) listInstructors();
            else if (ch == 3) updateInstructor();
            else if (ch == 4) removeInstructor();
            else if (ch == 5) assignInstructor();
            else if (ch == 0) back = true;
            else System.out.println("Invalid choice.");
        }
    }

    private void addInstructor() {
        System.out.println("\n[Add Instructor]");
        String id   = InputHelper.readString("Instructor ID : ");
        String fn   = InputHelper.readString("First Name    : ");
        String ln   = InputHelper.readString("Last Name     : ");
        String dept = InputHelper.readString("Department    : ");
        try {
            instructorService.addInstructor(new Instructor(id, fn, ln, dept));
        } catch (DuplicateIdException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void listInstructors() {
        List<Instructor> list = instructorService.getAllInstructors();
        if (list.isEmpty()) { System.out.println("No instructors found."); return; }
        System.out.println("\n[All Instructors]");
        for (Instructor i : list) System.out.println("  " + i);
    }

    private void updateInstructor() {
        String id   = InputHelper.readString("Instructor ID to update: ");
        System.out.println("(Leave blank to keep current value)");
        String fn   = InputHelper.readString("New First Name  : ");
        String ln   = InputHelper.readString("New Last Name   : ");
        String dept = InputHelper.readString("New Department  : ");
        instructorService.updateInstructor(id,
                fn.isBlank()   ? null : fn,
                ln.isBlank()   ? null : ln,
                dept.isBlank() ? null : dept);
    }

    private void removeInstructor() {
        String id = InputHelper.readString("Instructor ID to remove: ");
        instructorService.removeInstructor(id);
    }

    private void assignInstructor() {
        String instrId   = InputHelper.readString("Instructor ID : ");
        String sectionId = InputHelper.readString("Section ID    : ");
        Section section  = enrollmentService.findSectionById(sectionId);
        if (section == null) { System.out.println("Section not found."); return; }
        instructorService.assignInstructorToSection(instrId, section);
    }
}
