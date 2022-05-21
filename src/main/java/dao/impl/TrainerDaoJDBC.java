package dao.impl;

import dao.TrainerDao;
import db.DB;
import db.DbException;
import model.Trainer;
import java.sql.*;

public class TrainerDaoJDBC implements TrainerDao {


    private final Connection conn;

    public TrainerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Trainer trainer) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO trainer " +
                            "(afm,first_name,last_name) " +
                            "VALUES" +
                            "(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, trainer.getAfm());
            st.setString(2, trainer.getFirstName());
            st.setString(3, trainer.getLastName());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    trainer.setId(id);
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
    public Trainer findTrainerByAfm(String afm) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Trainer trainer = null;
        try {
            st = conn.prepareStatement(
                    ("select * from trainer where afm = ?"));

            st.setString(1, afm.trim());
            rs = st.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("trainer_id");
                String trainerAfm = rs.getString("afm");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                trainer = new Trainer(id, trainerAfm, firstName, lastName);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        return trainer;
    }

}

