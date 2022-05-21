package dao.impl;

import dao.TypesInCourseDao;
import db.DB;
import db.DbException;
import model.Type;
import model.TypesInCourse;
import java.sql.*;

public class TypesInCourseDaoJDBC implements TypesInCourseDao {

    private final Connection conn;

    public TypesInCourseDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(TypesInCourse typesInCourse) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO type_course" +
                            "(type_id,course_id)" +
                            "VALUES " +
                            "(?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            for (Type typeInCourse : typesInCourse.getTypes()) {
                st.setInt(1, typeInCourse.getId());
                st.setInt(2, typesInCourse.getCourse().getId());
                st.addBatch();
            }

            int[] rowsAffected = st.executeBatch();

            if (rowsAffected.length > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    typesInCourse.setId(id);
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
