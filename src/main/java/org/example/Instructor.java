package org.example;

import java.util.ArrayList;
import java.util.List;

public class Instructor {
    private String instructorId;
    private String firstName;
    private String lastName;
    private String department;
    private List<String> assignedSections;

    public Instructor(String instructorId, String firstName, String lastName, String department) {
        this.instructorId = instructorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.assignedSections = new ArrayList<>();
    }

    // Getters
    public String getInstructorId()            { return instructorId; }
    public String getFirstName()               { return firstName; }
    public String getLastName()                { return lastName; }
    public String getDepartment()              { return department; }
    public List<String> getAssignedSections()  { return assignedSections; }
    public String getFullName()                { return "Prof. " + firstName + " " + lastName; }

    // Setters
    public void setFirstName(String firstName)      { this.firstName = firstName; }
    public void setLastName(String lastName)        { this.lastName = lastName; }
    public void setDepartment(String department)    { this.department = department; }

    public void addAssignedSection(String sectionName) {
        if (!assignedSections.contains(sectionName)) {
            assignedSections.add(sectionName);
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] Prof. %s %s | Dept: %s | Sections: %d",
                instructorId, firstName, lastName, department, assignedSections.size());
    }
}