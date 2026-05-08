# Interface-Driven Enrollment System

A Java capstone project demonstrating interface-based architecture,
CRUD operations, business validation, and JUnit 5 testing.

## Developer
- **Name:** Sam Gabriel
- **GitHub:** SaaaamAbje
- **Project:** Interface-Driven Enrollment System

## Project Structure
src/
└── main/java/org/example/
├── Entities        → Student, Instructor, Course, Section, Department, TuitionFeePayment
├── Exceptions      → SectionFullException, DuplicateIdException, InvalidPaymentException, PrerequisiteNotMetException
├── Interfaces      → IStudentService, IInstructorService, ICourseService, ITuitionService, IEnrollmentService
├── Services        → StudentServiceImpl, InstructorServiceImpl, CourseServiceImpl, TuitionServiceImpl, EnrollmentServiceImpl
└── CLI             → Main, InputHelper, StudentMenu, InstructorMenu, CourseMenu, EnrollmentMenu, TuitionMenu
└── test/java/org/example/
└── EnrollmentSystemTest  → 11 JUnit 5 tests

## How to Run
1. Open project in IntelliJ IDEA
2. Open `Main.java`
3. Click the green Run button ▶

## How to Run Tests
1. Right-click `EnrollmentSystemTest.java`
2. Click Run 'EnrollmentSystemTest'
3. All 11 tests should pass ✅

## Features Implemented
- ✅ Interface architecture (5 interfaces, 5 implementations)
- ✅ CRUD for Student, Instructor, Course
- ✅ Section capacity validation (SectionFullException)
- ✅ Department hierarchy viewer (Department → Section → Instructor + Students)
- ✅ Tuition calculation and payment processing
- ✅ Overpayment handling
- ✅ Scholarship discount calculation (bonus)
- ✅ Prerequisite course checking (bonus)
- ✅ Duplicate ID prevention (bonus)
- ✅ Input validation with try-catch (bonus)
- ✅ 11 passing JUnit 5 unit tests (bonus)

## Git Workflow Used
| Branch | Description |
|--------|-------------|
| `feature/entities` | Entity classes |
| `feature/exceptions` | Custom exceptions |
| `feature/interfaces` | Service interfaces |
| `feature/student-crud` | Student service implementation |
| `feature/instructor-service` | Instructor service implementation |
| `feature/course-service` | Course service implementation |
| `feature/tuition-service` | Tuition service implementation |
| `feature/enrollment-service` | Enrollment service implementation |
| `feature/cli-menu` | CLI menu system |
| `feature/unit-tests` | JUnit 5 unit tests |

## Sample Data Loaded on Startup
| Type | Data |
|------|------|
| Courses | CS101, CS102, CS201, MATH101 |
| Students | Maria Santos, Juan Dela Cruz, Ana Reyes |
| Instructors | Prof. Roberto Lim, Prof. Carla Bautista |
| Department | College of Computer Studies (BSIT-1A, BSIT-1B) |