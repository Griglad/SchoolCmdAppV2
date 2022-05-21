package services;

import model.*;

public class CreateAllDataService {

    private final CourseService courseService = new CourseService();
    private final TrainersInCourseService trainersInCourseService = new TrainersInCourseService();
    private final AssignmentsInCourseService assignmentsInCourseService = new AssignmentsInCourseService();
    private final StudentsInCourseService studentsInCourseService = new StudentsInCourseService();
    private final AssignmentsInStudentInCourseService createAssignmentsInStudentInCourseService = new AssignmentsInStudentInCourseService();

    public void create() {
        Course createdCourse = courseService.create();
        //Check if the user tries to create a specific course twice
        if (createdCourse != null) {
            trainersInCourseService.create(createdCourse);
            AssignmentsInCourse assignmentsInCourse = assignmentsInCourseService.create(createdCourse);
            StudentsInCourse studentsInCourse = studentsInCourseService.create(createdCourse);
            createAssignmentsInStudentInCourseService.create(assignmentsInCourse, studentsInCourse);
        }
    }
}
