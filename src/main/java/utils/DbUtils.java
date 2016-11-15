package utils;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.util.HashMap;

public class DbUtils {

    public static HashMap<String, String> getSingleRecord(String query, String connectionString) throws SQLException {

        OracleDataSource ods = new OracleDataSource();
        ods.setURL(connectionString);
        ods.setUser(PropertyUtils.getProperty("username"));
        ods.setPassword(PropertyUtils.getProperty("password"));

        HashMap<String, String> row = null;

        Connection conn = null;
        try {
            conn = ods.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            rs.next();
            row = new HashMap<String, String>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getString(i) == null ? "" : rs.getString(i));
            }
        } finally {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        }

        return row;

    }
}
//TODO:Created conn string in pom.xml and contunuing to improve