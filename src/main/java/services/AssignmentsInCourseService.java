package services;

import dao.AssignmentDao;
import dao.AssignmentsInCourseDao;
import dao.impl.AssignmentDaoJDBC;
import dao.impl.AssignmentsInCourseDaoJDBC;
import db.DB;
import model.Assignment;
import model.AssignmentsInCourse;
import model.Course;
import utilities.Input;
import utilities.InputValidator;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AssignmentsInCourseService {

    private final Scanner input = Input.getInstance();
    private final AssignmentDao assignmentDao = new AssignmentDaoJDBC(DB.getConnection());
    private final AssignmentsInCourseDao assignmentsInCourseDao = new AssignmentsInCourseDaoJDBC(DB.getConnection());

    public AssignmentsInCourse create(Course course) {
        AssignmentsInCourse assignmentsInCourse = new AssignmentsInCourse(course);
        String title;
        String description;
        LocalDate subDateTime;
        double oralMark;
        double totalMark;
        Assignment assignment;
        boolean flag = false;
        boolean moreAssignments = false;
        while (!flag) {
            System.out.println("----ADDING ASSIGNMENTS TO THE COURSE " + course.getTitle());
            //ASSIGNMENT CREATION
            System.out.println("ASSIGNMENT'S TITLE:");
            title = InputValidator.provideOnlyString(input);
            if (findAssignmentInCourseByTitle(assignmentsInCourse, title) != null) {
                System.out.println("THIS ASSIGNMENT ALREADY EXISTS,PLEASE CREATE ANOTHER ASSIGNMENT.");
            } else {
                assignment = assignmentDao.findAssignmentByTitle(title);
                if (assignment == null) {
                    System.out.println("DESCRIPTION:");
                    description = InputValidator.provideOnlyString(input);
                    System.out.println("SUBMISSION DATE:");
                    subDateTime = InputValidator.provideSubDate(course.getStart_date(), course.getEnd_date());
                    System.out.println("ORAL-MARK:");
                    oralMark = InputValidator.provideNumToDouble(input);
                    System.out.println("TOTAL-MARK:");
                    totalMark = InputValidator.provideNumToDouble(input);
                    assignment = new Assignment(title, description, subDateTime, oralMark, totalMark);
                }
                Assignment assignmentFromDb = addAssignmentInDb(assignment);
                assignmentsInCourse.addAssignment(assignmentFromDb);

                if (assignmentsInCourse.getAssignments().size() >= 7) {
                    System.out.println("DO YOU WANT TO CREATE ANOTHER ASSIGNMENT FOR THIS COURSE?");
                    System.out.println(("1 - YES"));
                    System.out.println(("2 - NO"));
                    moreAssignments = InputValidator.yesOrNoMenu();
                }
                if (!moreAssignments && assignmentsInCourse.getAssignments().size() >= 7) {
                    addAssignmentsInCourseInDb(assignmentsInCourse);
                    flag = true;
                } else {
                    StringBuilder builder = new StringBuilder();
                    builder.append("YOU HAVE TO ADD MINIMUM 7 ASSIGNMENTS FOR THE COURSE:");
                    builder.append(course.getTitle());
                    if (assignmentsInCourse.getAssignments().size() > 1) {
                        builder.append(".YOU NOW HAVE THE ASSIGNMENTS:");
                    } else {
                        builder.append(".YOU NOW HAVE THE ASSIGNMENT:");
                    }
                    builder.append(assignmentsInCourse.getAssignments().stream()
                            .map(as -> as.getTitle().toUpperCase()).collect(Collectors.joining(",")));
                    System.out.println(builder.toString());
                }
            }
        }
        return assignmentsInCourse;
    }


    public static Assignment findAssignmentInCourseByTitle(AssignmentsInCourse assignmentsInCourse, String title) {
        Assignment foundAssignment = null;
        Optional<Assignment> optionalAssignment = assignmentsInCourse.getAssignments()
                .stream().filter(assignment -> assignment.getTitle().equalsIgnoreCase(title))
                .findFirst();
        if (optionalAssignment.isPresent()) {
            foundAssignment = optionalAssignment.get();
        }
        return foundAssignment;
    }

    private Assignment addAssignmentInDb(Assignment assignment) {
        if (assignment.getId() == null) {
            assignmentDao.insert(assignment);
        }
        return assignment;
    }

    private void addAssignmentsInCourseInDb(AssignmentsInCourse assignmentsInCourse) {
        assignmentsInCourseDao.insert(assignmentsInCourse);
    }
}
