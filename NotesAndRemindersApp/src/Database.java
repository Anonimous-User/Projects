
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

public class Database {

	public static enum types{
		Time,
		Reminder,
		Note
	}

    private static Connection connection;
    
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		try {
			initDatabaseConnection();
			// try (PreparedStatement prep = connection.prepareStatement(
			// 	"CREATE TABLE testuser(Note TEXT, Reminder TEXT, Time TEXT)"
			// 	)) {
			//  	prep.executeQuery();
		  	// }
			// insertData(types.Note, "testtest");
			retrieveData();
		}
		catch(SQLException e){
			System.out.println(e);
		}
		finally{
			// try (PreparedStatement prep = connection.prepareStatement(
            //       "DELETE FROM test")) {
            //    prep.executeQuery();
            // }
		}
	}

	public static void insertData(types column, String msg) throws SQLException{
		try(PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO testuser(" + column + ") VALUES ('" + msg +"')"
			)) {
				statement.executeQuery();
		}
	}

	public static ArrayList<String[]> retrieveData() throws SQLException{
		try(PreparedStatement statement = connection.prepareStatement(
			"SELECT Note, Reminder, Time FROM testuser"
		)){
			try (ResultSet resultSet = statement.executeQuery()) {
				boolean empty = true;
				int i=0;
				ArrayList<String[]> rtn = new ArrayList<String[]>();
				while (resultSet.next()) {
					empty = false;
					rtn.add(new String[3]);
					rtn.get(i)[0] = resultSet.getString("Note");
					rtn.get(i)[1] = resultSet.getString("Reminder");
					rtn.get(i)[2] = resultSet.getString("Time");

					System.out.println("\t> " + rtn.get(i)[0] + " : " + rtn.get(i)[1] + " : " + rtn.get(i)[2]);
					i++;
				}
				if (empty) {
					System.out.println("\t (no data)");
				}
				return rtn;
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
