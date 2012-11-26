/*
 * Class to connect to our SQL database running on localhost
 */
package barker;

import java.sql.*;

/**
 *
 * @author cmackey
 */
public class connect {

    public connect() {
    }

    public void startConnection(String username, String password) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // database credentials
    protected static String username;
    protected static String password;
    // jdbc driver and database url
    private static final String JDCB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/EMP";
}
