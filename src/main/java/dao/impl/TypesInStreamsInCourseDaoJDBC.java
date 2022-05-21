package dao.impl;

import dao.TypesInStreamsInCourseDao;
import db.DB;
import db.DbException;
import model.*;
import java.sql.*;

public class TypesInStreamsInCourseDaoJDBC implements TypesInStreamsInCourseDao {

    private final Connection conn;

    public TypesInStreamsInCourseDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(TypesInStreamsInCourse typesInStreamsInCourse) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO type_stream_course " +
                            "(type_id,stream_id,course_id ) " +
                            "VALUES" +
                            "(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            for (Stream streamsInCourse : typesInStreamsInCourse.getStreamsInCourse().getStreams()) {
                for (Type typeInStreamInCourse : streamsInCourse.getTypes()) {
                    st.setInt(1, typeInStreamInCourse.getId());
                    st.setInt(2, streamsInCourse.getId());
                    st.setInt(3, typesInStreamsInCourse.getStreamsInCourse().getCourse().getId());
                    st.addBatch();
                }
            }

            int[] b = st.executeBatch();
            if (b.length > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    typesInStreamsInCourse.setId(id);
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
