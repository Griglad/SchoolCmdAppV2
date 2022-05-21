package services;

import dao.*;
import dao.impl.*;
import db.DB;
import model.*;
import utilities.Input;
import utilities.InputValidator;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AssignmentsInStudentInCourseService {

    private final Scanner input = Input.getInstance();
    private final StudentDao studentDao = new StudentDaoJDBC(DB.getConnection());
    private final AssignmentDao assignmentDao = new AssignmentDaoJDBC(DB.getConnection());
    private final AssignmentsInStudentsInCourseDao assignmentsInStudentsInCourseDao = new AssignmentsInStudentsInCourseDaoJDBC(DB.getConnection());

    public void create(AssignmentsInCourse assignmentsInCourse, StudentsInCourse studentsInCourse) {
        String courseTitle = studentsInCourse.getCourse().getTitle();
        AssignmentsInStudentInCourse assignmentsInStudentInCourse;
        String afm;
        String title;
        Student foundStudent;
        Assignment foundAssignment;
        boolean flag1 = false;
        boolean flag2 = false;
        while (!flag1) {
            System.out.println("----ADDING ASSIGNMENTS TO STUDENTS FOR THE COURSE " + courseTitle);
            System.out.println("STUDENT AFM");
            afm = InputValidator.provideAfmWithNineDigits(input);
            if (StudentsInCourseService.findStudentInCourseByAfm(studentsInCourse, afm) != null) {
                foundStudent = studentDao.findStudentByAfm(afm);
                assignmentsInStudentInCourse = new AssignmentsInStudentInCourse(assignmentsInCourse.getCourse(), foundStudent);
                while (!flag2) {
                    System.out.println("ASSIGNMENT'S TITLE");
                    title = InputValidator.provideOnlyString(input);
                    if (AssignmentsInCourseService.findAssignmentInCourseByTitle(assignmentsInCourse, title) != null) {
                        //Checks if his assignment has already been assigned to this student
                        if (findAssignmentInStudentInCourseByTitle(assignmentsInStudentInCourse, title) == null) {
                            foundAssignment = assignmentDao.findAssignmentByTitle(title);
                            assignmentsInStudentInCourse.getAssignments().add(foundAssignment);

                        } else {
                            System.out.println("THIS STUDENT WITH AFM " + afm + " HAS ALREADY BEEN ASSIGNED THE ASSIGNMENT WITH TITLE " + title);
                        }

                    } else {
                        System.out.println("THIS ASSIGNMENT DOES NOT EXIST PLEASE INPUT A CORRECT ASSIGNMENT");
                    }
                    System.out.println("DO YOU WANT TO ADD ANOTHER ASSIGNMENT FOR THE STUDENT WITH AFM " + afm);
                    System.out.println(("1 - YES"));
                    System.out.println(("2 - NO"));
                    boolean more = InputValidator.yesOrNoMenu();
                    if (!more && assignmentsInStudentInCourse.getAssignments().size() == 7) {
                        flag2 = true;
                        addAssignmentsInStudentsInCourseInDb(assignmentsInStudentInCourse);
                    } else {
                        StringBuilder builder = new StringBuilder();
                        builder.append("YOU HAVE TO ADD 7 ASSIGNMENTS FOR THIS STUDENT WITH AFM:");
                        builder.append(foundStudent.getAfm());
                        if (assignmentsInStudentInCourse.getAssignments().size() > 1) {
                            builder.append(" YOU NOW HAVE THE ASSIGNMENTS:");
                        } else if (assignmentsInStudentInCourse.getAssignments().size() == 1) {
                            builder.append(" YOU NOW HAVE THE ASSIGNMENT:");
                        } else {
                            builder.append(" YOU DON'T HAVE ANY ASSIGNMENTS");
                        }
                        builder.append(assignmentsInStudentInCourse.getAssignments().stream()
                                .map(as -> as.getTitle().toUpperCase()).collect(Collectors.joining(",")));
                        System.out.println(builder.toString());
                    }
                }
            } else {
                System.out.println("THIS STUDENT WITH AFM " + afm + " DOES NOT EXIST PLEASE INPUT A CORRECT STUDENT'S AFM");
            }

            System.out.println("DO YOU WANT TO ADD AN ASSIGNMENT FOR ANOTHER STUDENT?");
            System.out.println(("1 - YES"));
            System.out.println(("2 - NO"));
            boolean more = InputValidator.yesOrNoMenu();
            if (!more) {
                flag1 = true;
            } else {
                flag2 = false;
            }
        }

    }

    private Assignment findAssignmentInStudentInCourseByTitle(AssignmentsInStudentInCourse assignmentsInStudentInCourse, String title) {
        Assignment foundAssignment = null;
        Optional<Assignment> optionalAssignment = assignmentsInStudentInCourse.getAssignments()
                .stream().filter(assignment -> assignment.getTitle().equalsIgnoreCase(title))
                .findFirst();
        if (optionalAssignment.isPresent()) {
            foundAssignment = optionalAssignment.get();
        }
        return foundAssignment;
    }

    private void addAssignmentsInStudentsInCourseInDb(AssignmentsInStudentInCourse assignmentsInStudentsInCourse) {
        assignmentsInStudentsInCourseDao.insert(assignmentsInStudentsInCourse);
    }
}
