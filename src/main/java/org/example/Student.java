package org.example;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private double totalTuitionFee;
    private double amountPaid;
    private List<String> passedCourses;

    public Student(String studentId, String firstName, String lastName, String email) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.totalTuitionFee = 0.0;
        this.amountPaid = 0.0;
        this.passedCourses = new ArrayList<>();
    }

    // Getters
    public String getStudentId()        { return studentId; }
    public String getFirstName()        { return firstName; }
    public String getLastName()         { return lastName; }
    public String getEmail()            { return email; }
    public double getTotalTuitionFee()  { return totalTuitionFee; }
    public double getAmountPaid()       { return amountPaid; }
    public List<String> getPassedCourses() { return passedCourses; }

    public String getFullName()         { return firstName + " " + lastName; }
    public double getRemainingBalance() { return totalTuitionFee - amountPaid; }

    // Setters
    public void setFirstName(String firstName)      { this.firstName = firstName; }
    public void setLastName(String lastName)        { this.lastName = lastName; }
    public void setEmail(String email)              { this.email = email; }
    public void setTotalTuitionFee(double fee)      { this.totalTuitionFee = fee; }
    public void setAmountPaid(double amountPaid)    { this.amountPaid = amountPaid; }

    public void addPassedCourse(String courseCode) {
        if (!passedCourses.contains(courseCode)) {
            passedCourses.add(courseCode);
        }
    }

    public boolean hasPassed(String courseCode) {
        return passedCourses.contains(courseCode);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Email: %s | Balance: PHP %.2f",
                studentId, getFullName(), email, getRemainingBalance());
    }
}