package org.example;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentServiceImpl implements IEnrollmentService {

    private List<Department> departments = new ArrayList<>();
    private IInstructorService instructorService;
    private ICourseService courseService;

    public EnrollmentServiceImpl(IInstructorService instructorService, ICourseService courseService) {
        this.instructorService = instructorService;
        this.courseService = courseService;
    }

    @Override
    public void enrollStudentInSection(Student student, Section section)
            throws SectionFullException, PrerequisiteNotMetException {

        // 1. Capacity check
        if (section.isFull()) {
            throw new SectionFullException(
                    "Enrollment failed: Section '" + section.getSectionName() + "' is full ("
                            + section.getCurrentEnrollment() + "/" + section.getMaxCapacity() + ").");
        }

        // 2. Duplicate enrollment check
        if (isStudentEnrolledInSection(student.getStudentId(), section)) {
            System.out.println("Student " + student.getFullName() + " is already enrolled in this section.");
            return;
        }

        // 3. Prerequisite check
        Course course = courseService.findCourseByCode(section.getCourseCode());
        if (course != null && course.hasPrerequisite()) {
            if (!student.hasPassed(course.getPrerequisiteCode())) {
                throw new PrerequisiteNotMetException(
                        "Enrollment failed: " + student.getFullName()
                                + " has not passed the prerequisite: " + course.getPrerequisiteCode());
            }
        }

        // 4. Enroll
        section.getEnrolledStudents().add(student);
        System.out.println(student.getFullName() + " enrolled in " + section.getSectionName());
    }

    @Override
    public boolean removeStudentFromSection(Student student, Section section) {
        List<Student> list = section.getEnrolledStudents();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStudentId().equalsIgnoreCase(student.getStudentId())) {
                list.remove(i);
                System.out.println(student.getFullName() + " removed from " + section.getSectionName());
                return true;
            }
        }
        System.out.println("Student not found in section.");
        return false;
    }

    @Override
    public void viewDepartmentHierarchy(Department department) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  DEPARTMENT: " + department.getDepartmentName());
        System.out.println("=".repeat(60));

        if (department.getSections().isEmpty()) {
            System.out.println("  (No sections found)");
            return;
        }

        for (Section section : department.getSections()) {
            System.out.println("\n  +-- SECTION: " + section.getSectionName()
                    + "  [" + section.getCurrentEnrollment() + "/" + section.getMaxCapacity() + " students]");

            if (section.getInstructorId() != null) {
                Instructor instr = instructorService.getInstructorDetails(section.getInstructorId());
                String name = (instr != null) ? instr.getFullName() : section.getInstructorId();
                System.out.println("  |   Instructor : " + name);
            } else {
                System.out.println("  |   Instructor : TBA");
            }
            System.out.println("  |   Course     : " + section.getCourseCode());

            if (section.getEnrolledStudents().isEmpty()) {
                System.out.println("  |   Students   : (none enrolled)");
            } else {
                System.out.println("  |   Students:");
                for (Student s : section.getEnrolledStudents()) {
                    System.out.printf("  |      - [%s] %s%n", s.getStudentId(), s.getFullName());
                }
            }
            System.out.println("  +" + "-".repeat(50));
        }
        System.out.println();
    }

    @Override
    public void addDepartment(Department department) {
        departments.add(department);
        System.out.println("Department added: " + department.getDepartmentName());
    }

    @Override
    public void addSectionToDepartment(String departmentId, Section section) {
        Department dept = findDepartmentById(departmentId);
        if (dept == null) {
            System.out.println("Department not found: " + departmentId);
            return;
        }
        dept.addSection(section);
        System.out.println("Section " + section.getSectionName() + " added to " + dept.getDepartmentName());
    }

    @Override
    public List<Department> getAllDepartments() {
        return new ArrayList<>(departments);
    }

    @Override
    public Department findDepartmentById(String departmentId) {
        for (Department d : departments) {
            if (d.getDepartmentId().equalsIgnoreCase(departmentId)) {
                return d;
            }
        }
        return null;
    }

    @Override
    public Section findSectionById(String sectionId) {
        for (Department d : departments) {
            for (Section s : d.getSections()) {
                if (s.getSectionId().equalsIgnoreCase(sectionId)) {
                    return s;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isStudentEnrolledInSection(String studentId, Section section) {
        for (Student s : section.getEnrolledStudents()) {
            if (s.getStudentId().equalsIgnoreCase(studentId)) {
                return true;
            }
        }
        return false;
    }
}
