package org.example.restservise.dbconnector;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;


public class MSConnector {

    private static Connection conn;

    public static Connection getOpenConnection(String srvName, String dbName, String userName, String userPass) {
        try {
            if (conn == null) {
                SQLServerDataSource ds = new SQLServerDataSource();
                ds.setUser(userName);
                ds.setPassword(userPass);
                ds.setServerName(srvName);
                ds.setPortNumber(1433);
                ds.setDatabaseName(dbName);
                return ds.getConnection();
            } else {
                return conn;
            }
        } catch (Exception sqlex) {
            sqlex.printStackTrace();
            throw new RuntimeException(sqlex.getMessage());
        }
    }

    public static boolean connectionClose() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            throw new RuntimeException(sqlex.getMessage());
        }
        return true;
    }

}