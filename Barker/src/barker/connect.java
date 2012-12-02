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
            /**
             * try { if (conn != null) { conn.close(); } } catch (SQLException
             * e) { e.printStackTrace();
            }
             */
        }
    }

    /**
     * Class to update the database
     *
     * @param query an SQL query to execute
     */
    public void update(String query) {
        Statement stmt = null;
        ResultSet rs = null;
        int updateQuery = 0;

        try {
            if (query != null) {
                stmt = conn.createStatement();
                updateQuery = stmt.executeUpdate(query);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Connection conn = null;
    // jdbc driver and database url
    private static final String DB_URL = "jdbc:mysql://localhost/barker";
}
