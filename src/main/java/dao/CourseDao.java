package dao;

import model.Course;

public interface CourseDao {

    void insert(Course course);

    Course findCourseByTitle(String title);
}
