package services;

import dao.StudentDao;
import dao.StudentsInCourseDao;
import dao.impl.StudentDaoJDBC;
import dao.impl.StudentsInCourseDaoJDBC;
import db.DB;
import model.Course;
import model.Student;
import model.StudentsInCourse;
import utilities.Input;
import utilities.InputValidator;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class StudentsInCourseService {

    private final Scanner input = Input.getInstance();
    private final StudentDao studentDao = new StudentDaoJDBC(DB.getConnection());
    private final StudentsInCourseDao studentsInCourseDao = new StudentsInCourseDaoJDBC(DB.getConnection());


    public StudentsInCourse create(Course course) {
        StudentsInCourse studentsInCourse = new StudentsInCourse(course);
        String afm;
        String firstName;
        String lastName;
        LocalDate dateOfBirth;
        double tuitionFees;
        Student student;
        boolean flag = false;
        while (!flag) {
            System.out.println("----ADDING STUDENT'S TO THE COURSE " + course.getTitle());
            System.out.println("AFM:");
            afm = InputValidator.provideAfmWithNineDigits(input);
            if (findStudentInCourseByAfm(studentsInCourse, afm) != null) {
                System.out.println("THIS STUDENT ALREADY EXISTS,PLEASE CREATE ANOTHER STUDENT.");
            } else {
                student = studentDao.findStudentByAfm(afm);
                if (student == null) {
                    System.out.println("FIRST NAME:");
                    firstName = InputValidator.provideOnlyString(input);
                    System.out.println("LAST NAME:");
                    lastName = InputValidator.provideOnlyString(input);
                    System.out.println("BIRTH DATE:");
                    dateOfBirth = InputValidator.validateBirthDate();
                    System.out.println(("GIVE THE STUDENT'S TUITION FEES:"));
                    tuitionFees = InputValidator.provideNumToDouble(input);
                    student = new Student(afm, firstName, lastName, dateOfBirth, tuitionFees);
                }
                Student studentFromDb = addStudentInDb(student);
                studentsInCourse.addStudent(studentFromDb);
                System.out.println("DO YOU WANT TO ADD ANOTHER STUDENT TO THE COURSE " + course.getTitle() + "?");
                System.out.println(("1 - YES"));
                System.out.println(("2 - NO"));
                boolean more = InputValidator.yesOrNoMenu();
                if (!more) {
                    addStudentsInCourseInDb(studentsInCourse);
                    flag = true;
                }
            }
        }
        return studentsInCourse;
    }

    private Student addStudentInDb(Student student) {
        if (student.getId() == null) {
            studentDao.insert(student);
        }
        return student;
    }

    private void addStudentsInCourseInDb(StudentsInCourse studentsInCourse) {
        studentsInCourseDao.insert(studentsInCourse);
    }

    public static Student findStudentInCourseByAfm(StudentsInCourse studentsInCourse, String afm) {
        Student foundStudent = null;
        Optional<Student> optionalStudent = studentsInCourse.getStudents()
                .stream().filter(student -> student.getAfm().equals(afm))
                .findFirst();
        if (optionalStudent.isPresent()) {
            foundStudent = optionalStudent.get();
        }
        return foundStudent;
    }
}
