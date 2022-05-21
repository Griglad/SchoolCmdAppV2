package dao.impl;

import dao.AssignmentsInCourseDao;
import db.DB;
import db.DbException;
import model.Assignment;
import model.AssignmentsInCourse;
import java.sql.*;

public class AssignmentsInCourseDaoJDBC implements AssignmentsInCourseDao {

    private final Connection conn;

    public AssignmentsInCourseDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(AssignmentsInCourse assignmentsInCourse) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO assignment_course" +
                            "(assignment_id,course_id)" +
                            "VALUES " +
                            "(?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            for (Assignment assignmentInCourse : assignmentsInCourse.getAssignments()) {
                st.setInt(1, assignmentInCourse.getId());
                st.setInt(2, assignmentsInCourse.getCourse().getId());
                st.addBatch();
            }

            int[] rowsAffected = st.executeBatch();

            if (rowsAffected.length > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    assignmentsInCourse.setId(id);
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
