package dao.impl;

import dao.SubjectsInTrainerDao;
import db.DB;
import db.DbException;
import model.Subject;
import model.SubjectsInTrainer;
import model.Trainer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectsInTrainerDaoJDBC implements SubjectsInTrainerDao {

    private final Connection conn;

    public SubjectsInTrainerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Subject> findAll(Trainer trainer) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "select *\n" +
                            "from trainer\n" +
                            "join subject_trainer\n" +
                            "on subject_trainer.trainer_id =trainer.trainer_id\n" +
                            "join subject on subject.subject_id = subject_trainer.subject_id\n" +
                            "where trainer.afm = ? ");
            st.setString(1, trainer.getAfm());
            rs = st.executeQuery();
            List<Subject> subjects = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("subject_id");
                String subjectName = rs.getString("subject_name");
                Subject subject = new Subject(id, subjectName);
                subjects.add(subject);
            }
            return subjects;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void insert(SubjectsInTrainer subjectsInTrainer) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO subject_trainer" +
                            "(subject_id,trainer_id)" +
                            "VALUES " +
                            "(?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            for (Subject subjectInTrainer : subjectsInTrainer.getSubjects()) {
                st.setInt(1, subjectInTrainer.getId());
                st.setInt(2, subjectsInTrainer.getTrainer().getId());
                st.addBatch();
            }
            int[] rowsAffected = st.executeBatch();

            if (rowsAffected.length > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    subjectsInTrainer.setId(id);
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
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM subject_trainer WHERE trainer_id = ?");

            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
}
