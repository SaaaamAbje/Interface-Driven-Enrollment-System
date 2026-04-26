package org.example;

import java.util.ArrayList;
import java.util.List;

public class InstructorServiceImpl implements IInstructorService {

    private List<Instructor> instructors = new ArrayList<>();

    @Override
    public void addInstructor(Instructor instructor) throws DuplicateIdException {
        if (instructorExists(instructor.getInstructorId())) {
            throw new DuplicateIdException("Instructor ID already exists: " + instructor.getInstructorId());
        }
        instructors.add(instructor);
        System.out.println("Instructor added: " + instructor.getFullName());
    }

    @Override
    public boolean updateInstructor(String instructorId, String firstName, String lastName, String department) {
        Instructor i = getInstructorDetails(instructorId);
        if (i == null) {
            System.out.println("Instructor not found: " + instructorId);
            return false;
        }
        if (firstName  != null && !firstName.isBlank())   i.setFirstName(firstName);
        if (lastName   != null && !lastName.isBlank())    i.setLastName(lastName);
        if (department != null && !department.isBlank())  i.setDepartment(department);
        System.out.println("Instructor updated: " + i.getFullName());
        return true;
    }

    @Override
    public boolean removeInstructor(String instructorId) {
        Instructor i = getInstructorDetails(instructorId);
        if (i == null) {
            System.out.println("Instructor not found: " + instructorId);
            return false;
        }
        instructors.remove(i);
        System.out.println("Instructor removed: " + i.getFullName());
        return true;
    }

    @Override
    public boolean assignInstructorToSection(String instructorId, Section section) {
        Instructor i = getInstructorDetails(instructorId);
        if (i == null) {
            System.out.println("Instructor not found: " + instructorId);
            return false;
        }
        section.setInstructorId(instructorId);
        i.addAssignedSection(section.getSectionName());
        System.out.println(i.getFullName() + " assigned to section: " + section.getSectionName());
        return true;
    }

    @Override
    public Instructor getInstructorDetails(String instructorId) {
        for (Instructor i : instructors) {
            if (i.getInstructorId().equalsIgnoreCase(instructorId)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return new ArrayList<>(instructors);
    }

    @Override
    public boolean instructorExists(String instructorId) {
        return getInstructorDetails(instructorId) != null;
    }
}