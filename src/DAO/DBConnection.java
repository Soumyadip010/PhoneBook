package DAO;

import java.sql.*;

public class DBConnection {
    private static final String driver="oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "system";
    private static final String PASSWORD = "tito";

    public static Connection getConnect() {
        try {
            //load the oracle jdbc driver
            Class.forName(driver);

            //establish connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }
}
