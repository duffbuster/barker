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
             * e) { e.printStackTrace(); }
             */
        }
    }

    /**
     * Class to update the database
     *
     * @param query an SQL query to execute
     */
    public void update(String username) {
        Statement stmt = null;
        ResultSet rs = null;
        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT username,password FROM barker.users WHERE username = '" + username + "';");
            System.out.println("Got results:");

            while (rs.next()) { // process results one row at a time
                String name = rs.getString("username");
                String pass = rs.getString("password");

                System.out.println("Username = " + name);
                System.out.println("Password = " + pass);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Connection conn = null;
    // jdbc driver and database url
    private static final String DB_URL = "jdbc:mysql://localhost/barker";
}
