package org.example;

import java.util.List;

public class TuitionMenu {
    private ITuitionService  tuitionService;
    private IStudentService  studentService;
    private ICourseService   courseService;

    public TuitionMenu(ITuitionService tuitionService, IStudentService studentService, ICourseService courseService) {
        this.tuitionService = tuitionService;
        this.studentService = studentService;
        this.courseService  = courseService;
    }

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n-- Tuition Fee Management --");
            System.out.println("  1. Assign Tuition to Student");
            System.out.println("  2. Make Payment");
            System.out.println("  3. View Student Balance");
            System.out.println("  4. View Payment History");
            System.out.println("  5. Calculate Fee (preview)");
            System.out.println("  6. Calculate Fee with Scholarship Discount");
            System.out.println("  0. Back");
            int ch = InputHelper.readInt("Choice: ");
            if      (ch == 1) assignTuition();
            else if (ch == 2) makePayment();
            else if (ch == 3) viewBalance();
            else if (ch == 4) viewPaymentHistory();
            else if (ch == 5) previewFee();
            else if (ch == 6) previewDiscountedFee();
            else if (ch == 0) back = true;
            else System.out.println("Invalid choice.");
        }
    }

    private void assignTuition() {
        String studentId  = InputHelper.readString("Student ID  : ");
        String courseCode = InputHelper.readString("Course Code : ");
        Student student   = studentService.findStudentById(studentId);
        Course  course    = courseService.findCourseByCode(courseCode);
        if (student == null) { System.out.println("Student not found."); return; }
        if (course  == null) { System.out.println("Course not found.");  return; }
        double fee = tuitionService.calculateFee(course.getUnits(), course.getFeePerUnit());
        tuitionService.assignTuitionToStudent(student, fee);
        System.out.printf("   (%d units x PHP %.2f = PHP %.2f)%n",
                course.getUnits(), course.getFeePerUnit(), fee);
    }

    private void makePayment() {
        String studentId = InputHelper.readString("Student ID : ");
        Student student  = studentService.findStudentById(studentId);
        if (student == null) { System.out.println("Student not found."); return; }
        System.out.printf("   Balance: PHP %.2f%n", student.getRemainingBalance());
        double amount = InputHelper.readDouble("Payment Amount: PHP ");
        try {
            tuitionService.makePayment(student, amount);
            System.out.printf("   Remaining Balance: PHP %.2f%n", student.getRemainingBalance());
        } catch (InvalidPaymentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void viewBalance() {
        String studentId = InputHelper.readString("Student ID : ");
        Student student  = studentService.findStudentById(studentId);
        if (student == null) { System.out.println("Student not found."); return; }
        System.out.printf("%n  Student      : %s%n", student.getFullName());
        System.out.printf("  Total Fee    : PHP %.2f%n", student.getTotalTuitionFee());
        System.out.printf("  Amount Paid  : PHP %.2f%n", student.getAmountPaid());
        System.out.printf("  Balance      : PHP %.2f%n", student.getRemainingBalance());
        String status = student.getRemainingBalance() <= 0 ? "FULLY PAID" : "HAS BALANCE";
        System.out.println("  Status       : " + status);
    }

    private void viewPaymentHistory() {
        String studentId = InputHelper.readString("Student ID : ");
        List<TuitionFeePayment> history = tuitionService.getPaymentHistory(studentId);
        if (history.isEmpty()) { System.out.println("No payment records found."); return; }
        System.out.println("\n[Payment History for " + studentId + "]");
        for (TuitionFeePayment p : history) System.out.println("  " + p);
    }

    private void previewFee() {
        int    units      = InputHelper.readInt   ("Number of Units  : ");
        double feePerUnit = InputHelper.readDouble("Fee per Unit: PHP ");
        double total      = tuitionService.calculateFee(units, feePerUnit);
        System.out.printf("%n  %d units x PHP %.2f = PHP %.2f%n", units, feePerUnit, total);
    }

    private void previewDiscountedFee() {
        int    units      = InputHelper.readInt   ("Number of Units    : ");
        double feePerUnit = InputHelper.readDouble("Fee per Unit  : PHP ");
        double discount   = InputHelper.readDouble("Discount (%%)  : ");
        double base       = tuitionService.calculateFee(units, feePerUnit);
        double total      = tuitionService.calculateFeeWithDiscount(units, feePerUnit, discount);
        System.out.printf("%n  Base Fee        : PHP %.2f%n", base);
        System.out.printf("  Discount (%.1f%%): - PHP %.2f%n", discount, base - total);
        System.out.printf("  Final Fee       : PHP %.2f%n", total);
    }
}
