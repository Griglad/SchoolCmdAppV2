package dao.impl;

import dao.StreamDao;
import db.DB;
import db.DbException;
import model.Stream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StreamDaoJDBC implements StreamDao {

    private final Connection conn;

    public StreamDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Stream> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "select stream_name from stream");
            rs = st.executeQuery();
            List<Stream> streamsList = new ArrayList<>();
            while (rs.next()) {
                String streamName = rs.getString("stream_name");
                Stream stream = new Stream(streamName);
                streamsList.add(stream);
            }
            return streamsList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Stream findByName(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Stream stream = null;
        try {
            st = conn.prepareStatement(
                    ("select * from stream where stream_name = ?"));

            st.setString(1,name);
            rs = st.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("stream_id");
                String streamName = rs.getString("stream_name");
                stream = new Stream(id, streamName);
            }
            return stream;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
