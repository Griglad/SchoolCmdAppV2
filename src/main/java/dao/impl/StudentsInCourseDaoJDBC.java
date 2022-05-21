package dao.impl;

import dao.StudentsInCourseDao;
import db.DB;
import db.DbException;
import model.Student;
import model.StudentsInCourse;
import java.sql.*;

public class StudentsInCourseDaoJDBC implements StudentsInCourseDao {


    private final Connection conn;


    public StudentsInCourseDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(StudentsInCourse studentsInCourse) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO student_course" +
                            "(student_id,course_id)" +
                            "VALUES " +
                            "(?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            for (Student studentInCourse : studentsInCourse.getStudents()) {
                st.setInt(1, studentInCourse.getId());
                st.setInt(2, studentsInCourse.getCourse().getId());
                st.addBatch();
            }

            int[] rowsAffected = st.executeBatch();

            if (rowsAffected.length > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    studentsInCourse.setId(id);
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
}
