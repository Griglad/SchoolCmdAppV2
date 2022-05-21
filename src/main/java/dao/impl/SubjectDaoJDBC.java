package dao.impl;

import dao.SubjectDao;
import db.DB;
import db.DbException;
import model.Subject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDaoJDBC implements SubjectDao {


    private final Connection conn;

    public SubjectDaoJDBC(Connection conn) {
        this.conn = conn;
    }



    @Override
    public List<Subject> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "select subject_name from subject");
            rs = st.executeQuery();
            List<Subject> subjects = new ArrayList<>();
            while (rs.next()) {
                String subjectName = rs.getString("subject_name");
                Subject subject = new Subject(subjectName);
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
    public Subject findByName(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Subject subject = null;
        try {
            st = conn.prepareStatement(
                    ("select * from subject where subject_name = ?"));

            st.setString(1,name);
            rs = st.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("subject_id");
                String subjectName = rs.getString("subject_name");
                subject = new Subject(id,subjectName);
            }
            return subject;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
