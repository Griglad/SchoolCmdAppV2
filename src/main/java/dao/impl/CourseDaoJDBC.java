package dao.impl;

import dao.CourseDao;
import db.DB;
import db.DbException;
import model.Course;
import java.sql.*;
import java.time.LocalDate;

public class CourseDaoJDBC implements CourseDao {

    private final Connection conn;

    public CourseDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Course course) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO course " +
                            "(title,start_date,end_date) " +
                            "VALUES" +
                            "(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, course.getTitle());
            st.setDate(2, Date.valueOf(course.getStart_date().toString()));
            st.setDate(3, Date.valueOf(course.getEnd_date().toString()));
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    course.setId(id);
                }
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Course findCourseByTitle(String title) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Course course = null;
        try {
            st = conn.prepareStatement(
                    ("select * from course where title = ?"));

            st.setString(1, title.trim());
            rs = st.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("course_id");
                String courseTitle = rs.getString("title");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                LocalDate startDateLocal = startDate.toLocalDate();
                LocalDate endDateLocal = endDate.toLocalDate();
                course = new Course(id, courseTitle, startDateLocal, endDateLocal);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        return course;
    }
}
