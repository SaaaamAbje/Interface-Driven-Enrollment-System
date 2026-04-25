package org.example;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private String departmentId;
    private String departmentName;
    private List<Section> sections;

    public Department(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.sections = new ArrayList<>();
    }

    // Getters
    public String getDepartmentId()    { return departmentId; }
    public String getDepartmentName()  { return departmentName; }
    public List<Section> getSections() { return sections; }

    // Setters
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public void addSection(Section section) {
        sections.add(section);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Sections: %d",
                departmentId, departmentName, sections.size());
    }
}