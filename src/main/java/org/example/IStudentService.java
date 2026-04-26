package org.example;

import java.util.List;

public interface IStudentService {
    void addStudent(Student student) throws DuplicateIdException;
    boolean updateStudent(String studentId, String firstName, String lastName, String email);
    boolean removeStudent(String studentId);
    List<Student> getAllStudents();
    Student findStudentById(String studentId);
    boolean studentExists(String studentId);
}