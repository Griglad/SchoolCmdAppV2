package dao.impl;

import dao.TypeDao;
import db.DB;
import db.DbException;
import model.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeDaoJDBC implements TypeDao {

    private final Connection conn;

    public TypeDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Type> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "select * from type");
            rs = st.executeQuery();

            List<Type> typesList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("type_id");
                String typeName = rs.getString("type_name");
                Type type = new Type(id, typeName);
                typesList.add(type);
            }
            return typesList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Type findByTypeName(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Type type = null;
        try {
            st = conn.prepareStatement(
                    ("select * from type where type_name=?"));
            st.setString(1,name);
            rs = st.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("type_id");
                String typeName = rs.getString("type_name");
                type = new Type(id, typeName);
            }
            return type;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
