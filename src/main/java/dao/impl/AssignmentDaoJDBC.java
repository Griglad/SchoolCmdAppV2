package dao.impl;

import dao.AssignmentDao;

import dao.AssignmentDao;
import db.DB;
import db.DbException;
import model.Assignment;
import java.sql.*;

public class AssignmentDaoJDBC implements AssignmentDao {

    private final Connection conn;

    public AssignmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Assignment assignment) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO assignment " +
                            "(title,description,sub_date,oral_mark,total_mark) " +
                            "VALUES" +
                            "(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, assignment.getTitle());
            st.setString(2, assignment.getDescription());
            st.setDate(3, Date.valueOf(assignment.getSubDateTime().toString()));
            st.setDouble(4, assignment.getOralMark());
            st.setDouble(5, assignment.getTotalMark());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    assignment.setId(id);
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
    public Assignment findAssignmentByTitle(String title) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Assignment assignment = null;
        try {
            st = conn.prepareStatement(
                    ("select * from assignment where title = ?"));

            st.setString(1, title.trim());
            rs = st.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("assignment_id");
                String assignmentTitle = rs.getString("title");
                String description = rs.getString("description");
                Date subDate = rs.getDate("sub_date");
                double oralMark = rs.getDouble("oral_mark");
                double totalMark = rs.getDouble("total_mark");
                assignment = new Assignment(id, assignmentTitle, description, subDate.toLocalDate(), oralMark, totalMark);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        return assignment;
    }

}

