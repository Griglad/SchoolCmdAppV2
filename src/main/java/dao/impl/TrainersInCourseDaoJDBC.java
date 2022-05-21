package dao.impl;

import dao.TrainersInCourseDao;
import db.DB;
import db.DbException;
import model.Trainer;
import model.TrainersInCourse;
import java.sql.*;

public class TrainersInCourseDaoJDBC implements TrainersInCourseDao{


        private final Connection conn;


    public TrainersInCourseDaoJDBC(Connection conn) {
            this.conn = conn;
        }

        @Override
        public void insert(TrainersInCourse trainersInCourse) {
            PreparedStatement st = null;
            try {
                st = conn.prepareStatement(
                        "INSERT INTO trainer_course" +
                                "(trainer_id,course_id)" +
                                "VALUES " +
                                "(?,?)",
                        Statement.RETURN_GENERATED_KEYS);

                for (Trainer trainerInCourse : trainersInCourse.getTrainers()) {
                    st.setInt(1, trainerInCourse.getId());
                    st.setInt(2, trainersInCourse.getCourse().getId());
                    st.addBatch();
                }

                int[] rowsAffected = st.executeBatch();

                if (rowsAffected.length > 0) {
                    ResultSet rs = st.getGeneratedKeys();
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        trainersInCourse.setId(id);
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
