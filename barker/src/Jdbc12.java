import java.sql.*;

public class Jdbc12 {
	public static void main(String args[]) {
		String userName = null;
		String password = null;
		System.out.println("Copyright 2004, R.G.Baldwin");
		try {
			Statement stmt;

			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			// Define URL of database server for
			// database named mysql on the localhost
			// with the default port number 3306.
			String url = "jdbc:mysql://localhost:3306/test";

			// Get a connection to the database for a
			// user named root with a blank password.
			// This user is the default administrator
			// having full privileges to do anything.
			Connection con = DriverManager.getConnection(url, userName, password);
			System.out.println("Connection open");

			
			
			// Display URL and connection information
			System.out.println("URL: " + url);
			System.out.println("Connection: " + con);

			// Get a Statement object
			stmt = con.createStatement();

			stmt.executeUpdate(
					 "CREATE DATABASE JunkDB");
			//Register a new user named auser on the
			// database named JunkDB with a password
			// drowssap enabling several different
			// privileges.
			stmt.executeUpdate(
			 "GRANT SELECT,INSERT,UPDATE,DELETE," +
			 "CREATE,DROP " +
			 "ON JunkDB.* TO 'auser'@'localhost' " +
			 "IDENTIFIED BY 'drowssap';");
			 
			// Remove the user named auser
			stmt.executeUpdate("REVOKE ALL PRIVILEGES ON *.* "
					+ "FROM 'auser'@'localhost'");
			stmt.executeUpdate("REVOKE GRANT OPTION ON *.* "
					+ "FROM 'auser'@'localhost'");
			stmt.executeUpdate("DELETE FROM mysql.user WHERE "
					+ "User='auser' and Host='localhost'");
			stmt.executeUpdate("FLUSH PRIVILEGES");

			// Delete the database
			stmt.executeUpdate("DROP DATABASE JunkDB");

			con.close();
			System.out.println("Connection closed");
		} catch (Exception e) {
			e.printStackTrace();
		}// end catch
	}// end main
}// end class Jdbc12