package model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Course  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String title;
    private LocalDate start_date;
    private LocalDate end_date;
    private static int count;

    public Course() {

    }

    public Course(Integer id, String title, LocalDate start_date, LocalDate end_date) {
        this(title, start_date, end_date);
        this.id = id;
    }

    public Course(String title, LocalDate start_date, LocalDate end_date) {
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return title.equalsIgnoreCase(course.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(++count).append("-");
        sb.append("THE COURSE TITLE IS:").append(title);
        sb.append(",THE START DATE IS:").append(start_date);
        sb.append(",THE END DATE IS:").append(end_date);
        return sb.toString();
    }
}

