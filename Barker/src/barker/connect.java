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
                    stmt.executeUpdate("UPDATE barker.users SET password = '" + newPassword + "' WHERE username = '" + username + "';");
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
        Statement tbl = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO barker.users (username,password) VALUES ('" + username + "','" + password + "');");
            tbl = conn.createStatement();
            tbl.executeUpdate("CREATE TABLE " + username + " (friends VARCHAR(20))");
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
            stmt.executeUpdate("INSERT INTO barker." + username + " (friends) VALUES ('" + friend + "');");
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
    }

    /**
     * Sends a bark to the server with separated tags and mentions
     *
     * @param username name of the poster
     * @param bark the bark to be posted
     * @return true if successful, false if not
     */
    public boolean sendBark(String username, String abark) {
        String bark = abark.concat(" ");
        Statement stmt = null;
        StringBuilder tags = new StringBuilder();
        StringBuilder mentions = new StringBuilder();
        try {
            stmt = conn.createStatement();
            // seperate out the tags; 3 max
            if (bark.indexOf('#') > -1) {
                int index = bark.indexOf('#');
                int end = bark.indexOf(" ", index + 1);
                String tag = bark.substring(index + 1, end);
                tags.append(tag);
                if (bark.indexOf('#', end) > -1) {
                    tags.append(",");
                    int indexb = bark.indexOf('#', end);
                    int endb = bark.indexOf(" ", indexb);
                    String tagb = bark.substring(indexb + 1, endb);
                    tags.append(tagb);
                    if (bark.indexOf('#', endb) > -1) {
                        tags.append(",");
                        int indexc = bark.indexOf('#', endb);
                        int endc = bark.indexOf(" ", indexc);
                        String tagc = bark.substring(indexc + 1, endc);
                        tags.append(tagc);
                    }
                }
            }
            // seperate out the @ mentions; 3 max;
            if (bark.indexOf('@') > -1) {
                int mindex = bark.indexOf('@');
                int mend = bark.indexOf(" ", mindex + 1);
                String ment = bark.substring(mindex + 1, mend);
                mentions.append(ment);
                if (bark.indexOf('@', mend) > -1) {
                    mentions.append(",");
                    int mindexb = bark.indexOf('@', mend);
                    int mendb = bark.indexOf(" ", mindexb);
                    String mentb = bark.substring(mindexb + 1, mendb);
                    mentions.append(mentb);
                    if (bark.indexOf('@', mendb) > -1) {
                        mentions.append(",");
                        int mindexc = bark.indexOf('@', mendb);
                        int mendc = bark.indexOf(" ", mindexc);
                        String mentc = bark.substring(mindexc + 1, mendc);
                        mentions.append(mentc);
                    }
                }
            }
            String tagString = tags.toString();
            String mentionString = mentions.toString();
            stmt.executeUpdate("INSERT INTO barker.barks (username,bark,tags,mentions) VALUES ('" + username + "','" + bark + "','" + tagString + "','" + mentionString + "');");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * gets the latest bark
     *
     * @return array bark, containing relevant info for latest bark
     */
    public String[] getLastBark() {
        Statement stmt = null;
        ResultSet rs = null;
        String[] bark = new String[5];
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM barker.barks;");
            while (rs.next()) {
                bark[0] = rs.getString("username");
                bark[1] = rs.getString("bark");
                bark[2] = rs.getString("time");
                bark[3] = rs.getString("tags");
                bark[4] = rs.getString("mentions");
            }
            return bark;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Connection conn = null;
    // jdbc driver and database url
    private static final String DB_URL = "jdbc:mysql://localhost/barker";
}
