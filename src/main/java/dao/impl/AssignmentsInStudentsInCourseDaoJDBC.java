package dao.impl;

import dao.AssignmentsInStudentsInCourseDao;
import dao.AssignmentsInStudentsInCourseDao;
import db.DB;
import db.DbException;
import model.Assignment;
import model.AssignmentsInStudentInCourse;
import java.sql.*;


public class AssignmentsInStudentsInCourseDaoJDBC  implements AssignmentsInStudentsInCourseDao {

    private Connection conn;

    public AssignmentsInStudentsInCourseDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(AssignmentsInStudentInCourse assignmentsInStudentInCourse) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO assignment_student_course" +
                            "(assignment_id,student_id,course_id)" +
                            "VALUES " +
                            "(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            for (Assignment assignment : assignmentsInStudentInCourse.getAssignments()) {
                st.setInt(1, assignment.getId());
                st.setInt(2, assignmentsInStudentInCourse.getStudent().getId());
                st.setInt(3, assignmentsInStudentInCourse.getCourse().getId());
                st.addBatch();
            }

            int[] b = st.executeBatch();

            if (b.length > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    assignmentsInStudentInCourse.setId(id);
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
