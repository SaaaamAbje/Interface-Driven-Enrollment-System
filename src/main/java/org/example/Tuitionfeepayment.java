package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TuitionFeePayment {
    private String paymentId;
    private String studentId;
    private double amount;
    private LocalDateTime paymentDate;
    private String remarks;

    public TuitionFeePayment (String paymentId, String studentId, double amount, String remarks) {
        this.paymentId = paymentId;
        this.studentId = studentId;
        this.amount = amount;
        this.paymentDate = LocalDateTime.now();
        this.remarks = remarks;
    }

    // Getters
    public String getPaymentId()          { return paymentId; }
    public String getStudentId()          { return studentId; }
    public double getAmount()             { return amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getRemarks()            { return remarks; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("[%s] Student: %s | Amount: PHP %.2f | Date: %s | %s",
                paymentId, studentId, amount, paymentDate.format(fmt), remarks);
    }
}
