package org.example;

public class Course {
    private String courseCode;
    private String courseName;
    private int units;
    private double feePerUnit;
    private String prerequisiteCode; // null if none

    public Course(String courseCode, String courseName, int units, double feePerUnit) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.units = units;
        this.feePerUnit = feePerUnit;
        this.prerequisiteCode = null;
    }

    public Course(String courseCode, String courseName, int units, double feePerUnit, String prerequisiteCode) {
        this(courseCode, courseName, units, feePerUnit);
        this.prerequisiteCode = prerequisiteCode;
    }

    // Getters
    public String getCourseCode()       { return courseCode; }
    public String getCourseName()       { return courseName; }
    public int getUnits()               { return units; }
    public double getFeePerUnit()       { return feePerUnit; }
    public double getTotalFee()         { return units * feePerUnit; }
    public String getPrerequisiteCode() { return prerequisiteCode; }
    public boolean hasPrerequisite()    { return prerequisiteCode != null; }

    // Setters
    public void setCourseName(String courseName)        { this.courseName = courseName; }
    public void setUnits(int units)                     { this.units = units; }
    public void setFeePerUnit(double feePerUnit)        { this.feePerUnit = feePerUnit; }
    public void setPrerequisiteCode(String code)        { this.prerequisiteCode = code; }

    @Override
    public String toString() {
        String prereq = hasPrerequisite() ? " | Prereq: " + prerequisiteCode : "";
        return String.format("[%s] %s | %d units | PHP %.2f/unit | Total: PHP %.2f%s",
                courseCode, courseName, units, feePerUnit, getTotalFee(), prereq);
    }
}
