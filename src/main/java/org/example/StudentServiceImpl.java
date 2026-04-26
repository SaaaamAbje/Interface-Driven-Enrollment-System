package org.example;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements IStudentService {

    private List<Student> students = new ArrayList<>();

    @Override
    public void addStudent(Student student) throws DuplicateIdException {
        if (studentExists(student.getStudentId())) {
            throw new DuplicateIdException("Student ID already exists: " + student.getStudentId());
        }
        students.add(student);
        System.out.println("Student added: " + student.getFullName());
    }

    @Override
    public boolean updateStudent(String studentId, String firstName, String lastName, String email) {
        Student s = findStudentById(studentId);
        if (s == null) {
            System.out.println("Student not found: " + studentId);
            return false;
        }
        if (firstName != null && !firstName.isBlank()) s.setFirstName(firstName);
        if (lastName  != null && !lastName.isBlank())  s.setLastName(lastName);
        if (email     != null && !email.isBlank())      s.setEmail(email);
        System.out.println("Student updated: " + s.getFullName());
        return true;
    }

    @Override
    public boolean removeStudent(String studentId) {
        Student s = findStudentById(studentId);
        if (s == null) {
            System.out.println("Student not found: " + studentId);
            return false;
        }
        students.remove(s);
        System.out.println("Student removed: " + s.getFullName());
        return true;
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    @Override
    public Student findStudentById(String studentId) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(studentId)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public boolean studentExists(String studentId) {
        return findStudentById(studentId) != null;
    }
}