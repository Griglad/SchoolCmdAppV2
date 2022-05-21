package services;

import dao.*;
import dao.impl.*;
import db.DB;
import model.*;
import utilities.Input;
import utilities.InputValidator;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CourseService {

    private final Scanner input = Input.getInstance();
    private final CourseDao courseDao = new CourseDaoJDBC(DB.getConnection());
    private final StreamsInCourseDao streamsInCourseDao = new StreamsInCourseDaoJDBC(DB.getConnection());
    private final TypesInCourseDao typesInCourseDao = new TypesInCourseDaoJDBC(DB.getConnection());
    private final TypesInStreamsInCourseDao typesInStreamsInCourseDao = new TypesInStreamsInCourseDaoJDBC(DB.getConnection());


    public Course create() {
        String title;
        LocalDate startDate;
        LocalDate endDate;
        Course course = null;
        boolean flag = false;
        List<Stream> streams;
        List<Type> types;
        while (!flag) {
            System.out.println("COURSE TITLE:");
            title = InputValidator.provideStringsAndMinOneDigit(input);
            if (courseDao.findCourseByTitle(title) != null) {
                System.out.println("THIS COURSE ALREADY EXISTS,PLEASE CREATE ANOTHER COURSE.");
                flag = true;
            } else {
                streams = InputValidator.provideStreams(input);
                types = streams
                        .stream()
                        .flatMap(e -> e.getTypes().stream()).collect(Collectors.toList());
                //Taking the distinct types
                Set<Type> distinctTypes = new HashSet<>(types);
                System.out.println("START DATE");
                startDate = InputValidator.provideStartDate();
                System.out.println(("END DATE:"));
                endDate = InputValidator.provideEndDate(startDate);
                course = new Course(title, startDate, endDate);
                StreamsInCourse streamsInCourse = new StreamsInCourse(course, streams);
                TypesInCourse typesInCourse = new TypesInCourse(course, distinctTypes);
                TypesInStreamsInCourse typesInStreamsInCourse = new TypesInStreamsInCourse(typesInCourse, streamsInCourse);
                courseDao.insert(course);
                streamsInCourseDao.insert(streamsInCourse);
                typesInCourseDao.insert(typesInCourse);
                typesInStreamsInCourseDao.insert(typesInStreamsInCourse);
                flag = true;
            }
        }
        return course;
    }
}
