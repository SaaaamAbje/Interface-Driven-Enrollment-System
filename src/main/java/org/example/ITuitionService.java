package org.example;

import java.util.List;

public interface ITuitionService {
    double calculateFee(int numberOfUnits, double feePerUnit);
    double calculateFeeWithDiscount(int numberOfUnits, double feePerUnit, double discountPercent);
    TuitionFeePayment makePayment(Student student, double amount) throws InvalidPaymentException;
    double getRemainingBalance(Student student);
    List<TuitionFeePayment> getPaymentHistory(String studentId);
    void assignTuitionToStudent(Student student, double totalFee);
}