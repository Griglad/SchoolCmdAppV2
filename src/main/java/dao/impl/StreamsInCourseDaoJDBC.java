package dao.impl;

import dao.StreamsInCourseDao;
import db.DB;
import db.DbException;
import model.Stream;
import model.StreamsInCourse;
import java.sql.*;

public class StreamsInCourseDaoJDBC implements StreamsInCourseDao {

    private final Connection conn;

    public StreamsInCourseDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(StreamsInCourse streamsInCourse) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO stream_course" +
                            "(stream_id,course_id)" +
                            "VALUES " +
                            "(?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            for (Stream streamInCourse : streamsInCourse.getStreams()) {
                st.setInt(1, streamInCourse.getId());
                st.setInt(2, streamsInCourse.getCourse().getId());
                st.addBatch();
            }

            int[] rowsAffected = st.executeBatch();

            if (rowsAffected.length > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    streamsInCourse.setId(id);
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
