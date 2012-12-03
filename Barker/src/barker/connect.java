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

    /**
     * Starts the database connection with admin credentials
     *
     * @param username admin username
     * @param password admin password
     */
    public void startConnection(String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * runs a select query to get the password of the user
     *
     * @param username; to be checked
     * @return pass; password corresponding to the username
     */
    public String getPassword(String username) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT password FROM barker.users WHERE username = '" + username + "';");
            while (rs.next()) { // process results one row at a time
                String pass = rs.getString("password");
                return pass;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * Method to allow the user to change their password
     *
     * @param username who's password will be changed
     * @param oldPassword the user's old password
     * @param newPassword the new password
     * @return true if successful, false if incorrect password
     */
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT password FROM barker.users WHERE username = '" + username + "';");
            while (rs.next()) {
                String pass = rs.getString("password");
                if (pass.equals(oldPassword)) {
                    stmt.executeUpdate("UPDATE users SET password = '" + newPassword + "' WHERE username = '" + username + "';");
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Method to add a new user to the users table in the database
     *
     * @param username the user to be added
     * @param password the user's password
     * @return true if successful, false if not
     */
    public boolean newUser(String username, String password) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO users (username,password) VALUES ('" + username + "','" + password + "');");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * executes an insert statement to add a friend
     *
     * @param username user who's friends list will be updated
     * @param friend the new friend
     * @return true if successful, false if not
     */
    public boolean addFriend(String username, String friend) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO duffbuster (friends) VALUES ('" + friend + "');");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to get a user's friends list
     *
     * @param username the user to be queried
     * @return the user's friends in an array
     */
    public String[] getFriends(String username) {
        Statement stmt = null;
        ResultSet rs = null;
        String[] friends = new String[100];
        int loc = 0;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT friends FROM barker." + username);
            while (rs.next()) {
                friends[loc] = rs.getString("friends");
                loc++;
            }
            return friends;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        //return null;
    }
    private Connection conn = null;
    // jdbc driver and database url
    private static final String DB_URL = "jdbc:mysql://localhost/barker";
}
