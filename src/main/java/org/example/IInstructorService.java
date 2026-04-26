package org.example;

import java.util.List;

public interface IInstructorService {
    void addInstructor(Instructor instructor) throws DuplicateIdException;
    boolean updateInstructor(String instructorId, String firstName, String lastName, String department);
    boolean removeInstructor(String instructorId);
    boolean assignInstructorToSection(String instructorId, Section section);
    Instructor getInstructorDetails(String instructorId);
    List<Instructor> getAllInstructors();
    boolean instructorExists(String instructorId);
}