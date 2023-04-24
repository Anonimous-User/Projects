
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
//https://stackoverflow.com/questions/21955256/manipulating-an-access-database-from-java-without-odbc

public class Database {

    //TODO: HELP!?!?

    private static Connection connection;
    
	public static void main(String[] args) throws SQLException {
		try {
			initDatabaseConnection();

		} finally {
			if (connection != null) {
				closeDatabaseConnection();
			}
		}
	}

	private static void initDatabaseConnection() throws SQLException {
		System.out.println("Connecting to the database...");
		connection = DriverManager.getConnection(
				"jdbc:mariadb://localhost:3306/demo",
				"user", "password");
		System.out.println("Connection valid: " + connection.isValid(5));
	}

	private static void closeDatabaseConnection() throws SQLException {
		System.out.println("Closing database connection...");
		connection.close();
		System.out.println("Connection valid: " + connection.isValid(5));
	}
}
