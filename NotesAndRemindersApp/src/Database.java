
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class Database {

	enum types{
		Time,
		Reminder,
		Note,
		SpecializedProtein
	}

    private static Connection connection;
    
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		try {
			initDatabaseConnection();
			try (PreparedStatement prep = connection.prepareStatement(
				"INSERT INTO test(testColomb) VALUES (100)"
				)) {
			 	prep.executeQuery();
		  	}

		}
		catch(SQLException e){
			System.out.println(e);
		}
		finally{
			try (PreparedStatement prep = connection.prepareStatement(
                  "DELETE FROM test")) {
               prep.executeQuery();
            }
		}
	}

	public static void insertData(types column, String msg) throws SQLException{
		try(PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO testuser(" + column + ") VALUES (" + msg +")"
			)) {
				statement.executeQuery();
		}
	}

	public static void retrieveDate(types column){

	}
	// use as reference
	// private static void readData() throws SQLException {
	// 	try (PreparedStatement statement = connection.prepareStatement("""
	// 			    SELECT name, rating
	// 			    FROM programming_language
	// 			    ORDER BY rating DESC
	// 			""")) {
	// 		try (ResultSet resultSet = statement.executeQuery()) {
	// 			boolean empty = true;
	// 			while (resultSet.next()) {
	// 				empty = false;
	// 				String name = resultSet.getString("name");
	// 				int rating = resultSet.getInt("rating");
	// 				System.out.println("\t> " + name + ": " + rating);
	// 			}
	// 			if (empty) {
	// 				System.out.println("\t (no data)");
	// 			}
	// 		}
	// 	}
	// }


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
