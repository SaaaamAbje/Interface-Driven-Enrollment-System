package org.example;

import java.util.ArrayList;
import java.util.List;

public class TuitionServiceImpl implements ITuitionService {

    private List<TuitionFeePayment> paymentHistory = new ArrayList<>();
    private int paymentCounter = 1;

    @Override
    public double calculateFee(int numberOfUnits, double feePerUnit) {
        return numberOfUnits * feePerUnit;
    }

    @Override
    public double calculateFeeWithDiscount(int numberOfUnits, double feePerUnit, double discountPercent) {
        double baseFee = calculateFee(numberOfUnits, feePerUnit);
        double discount = baseFee * (discountPercent / 100.0);
        return baseFee - discount;
    }

    @Override
    public TuitionFeePayment makePayment(Student student, double amount) throws InvalidPaymentException {
        if (amount <= 0) {
            throw new InvalidPaymentException("Payment amount must be greater than zero.");
        }
        double remaining = student.getRemainingBalance();
        if (remaining <= 0) {
            throw new InvalidPaymentException(student.getFullName() + " has no outstanding balance.");
        }

        String remarks;
        if (amount > remaining) {
            remarks = String.format("Overpayment: PHP %.2f received. PHP %.2f applied. PHP %.2f is your change.",
                    amount, remaining, (amount - remaining));
            amount = remaining;
        } else {
            remarks = "Payment accepted.";
        }

        student.setAmountPaid(student.getAmountPaid() + amount);

        String paymentId = String.format("PAY-%04d", paymentCounter++);
        TuitionFeePayment payment = new TuitionFeePayment(paymentId, student.getStudentId(), amount, remarks);
        paymentHistory.add(payment);

        System.out.printf("Payment recorded: PHP %.2f for %s%n", amount, student.getFullName());
        System.out.println("  " + remarks);
        return payment;
    }

    @Override
    public double getRemainingBalance(Student student) {
        return student.getRemainingBalance();
    }

    @Override
    public List<TuitionFeePayment> getPaymentHistory(String studentId) {
        List<TuitionFeePayment> result = new ArrayList<>();
        for (TuitionFeePayment p : paymentHistory) {
            if (p.getStudentId().equalsIgnoreCase(studentId)) {
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public void assignTuitionToStudent(Student student, double totalFee) {
        student.setTotalTuitionFee(totalFee);
        System.out.printf("Tuition assigned to %s: PHP %.2f%n", student.getFullName(), totalFee);
    }
}
