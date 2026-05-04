package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentSystemTest {

    private IStudentService    studentService;
    private IInstructorService instructorService;
    private ICourseService     courseService;
    private ITuitionService    tuitionService;
    private IEnrollmentService enrollmentService;

    @BeforeEach
    void setUp() throws DuplicateIdException {
        studentService    = new StudentServiceImpl();
        instructorService = new InstructorServiceImpl();
        courseService     = new CourseServiceImpl();
        tuitionService    = new TuitionServiceImpl();
        enrollmentService = new EnrollmentServiceImpl(instructorService, courseService);

        courseService.addCourse(new Course("CS101", "Intro to Programming", 3, 500.00));
        courseService.addCourse(new Course("CS102", "Data Structures", 3, 550.00, "CS101"));
    }

    // TEST 1: Section full throws exception
    @Test
    void testEnroll_SectionFull_ThrowsException() throws Exception {
        Section section = new Section("S1", "BSIT-1A", 2, "CS101");
        Student alice   = new Student("S01", "Alice", "A", "a@test.com");
        Student bob     = new Student("S02", "Bob",   "B", "b@test.com");
        Student charlie = new Student("S03", "Charlie", "C", "c@test.com");

        enrollmentService.enrollStudentInSection(alice, section);
        enrollmentService.enrollStudentInSection(bob, section);

        assertThrows(SectionFullException.class, () ->
                        enrollmentService.enrollStudentInSection(charlie, section),
                "Should throw SectionFullException when section is full"
        );
        assertEquals(2, section.getEnrolledStudents().size());
    }

    // TEST 2: Enrollment succeeds when section has room
    @Test
    void testEnroll_SectionHasRoom_Succeeds() throws Exception {
        Section section = new Section("S2", "BSIT-1B", 5, "CS101");
        Student student = new Student("S10", "Diana", "D", "d@test.com");
        enrollmentService.enrollStudentInSection(student, section);
        assertEquals(1, section.getEnrolledStudents().size());
    }

    // TEST 3: Tuition fee calculation is correct
    @Test
    void testCalculateFee_CorrectAmount() {
        double fee = tuitionService.calculateFee(3, 500.00);
        assertEquals(1500.00, fee, 0.001,
                "3 units x PHP 500 should equal PHP 1500");
    }

    // TEST 4: Scholarship discount calculation
    @Test
    void testCalculateFeeWithDiscount_20Percent() {
        double fee = tuitionService.calculateFeeWithDiscount(3, 500.00, 20.0);
        assertEquals(1200.00, fee, 0.001,
                "20% discount on PHP 1500 should yield PHP 1200");
    }

    // TEST 5: Payment reduces balance correctly
    @Test
    void testMakePayment_ReducesBalance() throws Exception {
        Student student = new Student("S20", "Eve", "E", "e@test.com");
        tuitionService.assignTuitionToStudent(student, 1500.00);
        tuitionService.makePayment(student, 500.00);
        assertEquals(1000.00, student.getRemainingBalance(), 0.001);
    }

    // TEST 6: Overpayment caps at remaining balance
    @Test
    void testMakePayment_Overpayment_CapsAtBalance() throws Exception {
        Student student = new Student("S21", "Frank", "F", "f@test.com");
        tuitionService.assignTuitionToStudent(student, 500.00);
        tuitionService.makePayment(student, 1000.00);
        assertEquals(0.00, student.getRemainingBalance(), 0.001,
                "Overpayment should reduce balance to 0");
    }

    // TEST 7: Zero payment throws exception
    @Test
    void testMakePayment_ZeroAmount_ThrowsException() {
        Student student = new Student("S22", "Grace", "G", "g@test.com");
        tuitionService.assignTuitionToStudent(student, 1500.00);
        assertThrows(InvalidPaymentException.class, () ->
                        tuitionService.makePayment(student, 0),
                "Payment of 0 should throw InvalidPaymentException"
        );
    }

    // TEST 8: Duplicate student ID throws exception
    @Test
    void testAddStudent_DuplicateId_ThrowsException() throws DuplicateIdException {
        studentService.addStudent(new Student("S99", "Henry", "H", "h@test.com"));
        assertThrows(DuplicateIdException.class, () ->
                        studentService.addStudent(new Student("S99", "Hannah", "H2", "h2@test.com")),
                "Duplicate student ID should throw DuplicateIdException"
        );
    }

    // TEST 9: Prerequisite blocks enrollment
    @Test
    void testEnroll_PrerequisiteNotMet_ThrowsException() {
        Section section = new Section("S3", "BSIT-2A", 30, "CS102");
        Student student = new Student("S30", "Iris", "I", "i@test.com");
        assertThrows(PrerequisiteNotMetException.class, () ->
                        enrollmentService.enrollStudentInSection(student, section),
                "Should throw PrerequisiteNotMetException"
        );
    }

    // TEST 10: Prerequisite met allows enrollment
    @Test
    void testEnroll_PrerequisiteMet_Succeeds() throws Exception {
        Section section = new Section("S4", "BSIT-2B", 30, "CS102");
        Student student = new Student("S31", "Jake", "J", "j@test.com");
        student.addPassedCourse("CS101");
        enrollmentService.enrollStudentInSection(student, section);
        assertEquals(1, section.getEnrolledStudents().size());
    }

    // TEST 11: Instructor assignment works
    @Test
    void testAssignInstructor_UpdatesSection() throws Exception {
        Instructor instr = new Instructor("I99", "Prof", "Test", "CCS");
        instructorService.addInstructor(instr);
        Section section = new Section("S5", "BSIT-3A", 30, "CS101");
        Department dept = new Department("D1", "Test Dept");
        enrollmentService.addDepartment(dept);
        enrollmentService.addSectionToDepartment("D1", section);
        boolean result = instructorService.assignInstructorToSection("I99", section);
        assertTrue(result);
        assertEquals("I99", section.getInstructorId());
    }
}
