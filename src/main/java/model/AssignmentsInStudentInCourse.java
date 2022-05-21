package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AssignmentsInStudentInCourse  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private List<Assignment> assignments = new ArrayList<>();
    private Student student;
    private Course course;
    private static int count;


    public AssignmentsInStudentInCourse() {

    }

    public AssignmentsInStudentInCourse(Integer id, List<Assignment> assignments, Student student, Course course) {
        this(assignments, student, course);
        this.id = id;
    }

    public AssignmentsInStudentInCourse(List<Assignment> assignments, Student student, Course course) {
        this(course,student);
        this.assignments = assignments;
    }
    public AssignmentsInStudentInCourse(Course course,Student student){
        this.course = course;
        this.student = student;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
