
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class Database {

    private static Connection connection;
    
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		try {
			initDatabaseConnection();
			try (PreparedStatement prep = connection.prepareStatement(
				"INSERT INTO test(testColomb) VALUES (100)"
				)) {
			 	prep.executeQuery();
		  	}

			
		} finally{
			try (PreparedStatement prep = connection.prepareStatement(
                  "DELETE FROM test")) {
               prep.executeQuery();
            }
		}
	}

	private static void initDatabaseConnection() throws SQLException {
		System.out.println("Connecting to the database...");
		connection = DriverManager.getConnection(
				"jdbc:mariadb://localhost:3306/NotesAndRemindersDB",
				"User", "LocalUser");
		System.out.println("Connection valid: " + connection.isValid(5));
	}

	private static void closeDatabaseConnection() throws SQLException {
		System.out.println("Closing database connection...");
		connection.close();
		System.out.println("Connection valid: " + connection.isValid(5));
	}
}
