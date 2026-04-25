package org.example;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private String sectionId;
    private String sectionName;
    private int maxCapacity;
    private String courseCode;
    private String instructorId;
    private List<Student> enrolledStudents;

    public Section(String sectionId, String sectionName, int maxCapacity, String courseCode) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.maxCapacity = maxCapacity;
        this.courseCode = courseCode;
        this.instructorId = null;
        this.enrolledStudents = new ArrayList<>();
    }

    // Getters
    public String getSectionId()               { return sectionId; }
    public String getSectionName()             { return sectionName; }
    public int getMaxCapacity()                { return maxCapacity; }
    public String getCourseCode()              { return courseCode; }
    public String getInstructorId()            { return instructorId; }
    public List<Student> getEnrolledStudents() { return enrolledStudents; }
    public int getCurrentEnrollment()          { return enrolledStudents.size(); }
    public boolean isFull()                    { return enrolledStudents.size() >= maxCapacity; }
    public int getAvailableSlots()             { return maxCapacity - enrolledStudents.size(); }

    // Setters
    public void setSectionName(String sectionName)   { this.sectionName = sectionName; }
    public void setMaxCapacity(int maxCapacity)      { this.maxCapacity = maxCapacity; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

    @Override
    public String toString() {
        String instr = instructorId != null ? instructorId : "TBA";
        return String.format("[%s] %s | Course: %s | Enrolled: %d/%d | Instructor: %s",
                sectionId, sectionName, courseCode,
                enrolledStudents.size(), maxCapacity, instr);
    }
}