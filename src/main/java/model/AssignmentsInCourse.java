package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AssignmentsInCourse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Course course;
    private List<Assignment> assignments = new ArrayList<>();
    private static int count;


    public AssignmentsInCourse(Course course) {
        this.course = course;
    }

    public AssignmentsInCourse(Course course, List<Assignment> assignments) {
        this(course);
        this.assignments = assignments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentsInCourse that = (AssignmentsInCourse) o;
        return course.equals(that.course) && assignments.equals(that.assignments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, assignments);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(++count + "-");
        sb.append("THE COURSE IS:")
                .append(course.getTitle());
        if (assignments.size() > 1) {
            sb.append(",THE ASSIGNMENTS ARE:");
        } else {
            sb.append(",THE ASSIGNMENT IS:");
        }
        sb.append(assignments.stream()
                .map(assignment -> assignment.getTitle().toUpperCase())
                .collect(Collectors.joining(",")));
        return sb.toString();
    }
}
