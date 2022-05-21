package dao.impl;

import dao.StudentDao;
import db.DB;
import db.DbException;
import model.Course;
import model.Student;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoJDBC implements StudentDao {

    private final Connection conn;

    public StudentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Student student) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO student " +
                            "(afm,first_name,last_name,date_of_birth,tuition_fees) " +
                            "VALUES" +
                            "(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, student.getAfm());
            st.setString(2, student.getFirstName());
            st.setString(3, student.getLastName());
            st.setDate(4, Date.valueOf(student.getDateOfBirth().toString()));
            st.setDouble(5, student.getTuitionFees());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    student.setId(id);
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
    public Student findStudentByAfm(String afm) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Student student = null;

        try {
            st = conn.prepareStatement(
                    ("select * from student where afm = ?"));

            st.setString(1, afm);
            rs = st.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("student_id");
                String studentAfm = rs.getString("afm");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                Date dateOfBirth = rs.getDate("date_of_birth");
                double tuitionFees = rs.getDouble("tuition_fees");
                student = new Student(id, studentAfm, firstName, lastName, dateOfBirth.toLocalDate(), tuitionFees);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        return student;
    }
}
