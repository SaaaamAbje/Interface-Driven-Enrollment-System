package org.example;

import java.util.List;

public interface IEnrollmentService {
    void enrollStudentInSection(Student student, Section section)
            throws SectionFullException, PrerequisiteNotMetException;
    boolean removeStudentFromSection(Student student, Section section);
    void viewDepartmentHierarchy(Department department);
    void addDepartment(Department department);
    void addSectionToDepartment(String departmentId, Section section);
    List<Department> getAllDepartments();
    Department findDepartmentById(String departmentId);
    Section findSectionById(String sectionId);
    boolean isStudentEnrolledInSection(String studentId, Section section);
}