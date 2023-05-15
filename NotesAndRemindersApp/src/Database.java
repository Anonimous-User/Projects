
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

public class Database {

    private static Connection connection;
    
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		try {
			initDatabaseConnection();
			// try (PreparedStatement prep = connection.prepareStatement(
			// 	"CREATE TABLE testuser(Note TEXT, Date TEXT, Time TEXT)"
			// 	)) {
			//  	prep.executeQuery();
		  	// }
			// insertData("test...test...");
			// retrieveData();
		}
		catch(SQLException e){
            e.printStackTrace();
			// closeDatabaseConnection();
		}
		finally{
			// try (PreparedStatement prep = connection.prepareStatement(
            //       "DELETE FROM test")) {
            //    prep.executeQuery();
            // }
		}
	}

	public static void clearData() throws SQLException{
		try(PreparedStatement statement = connection.prepareStatement(
			"DELETE FROM testuser"
		)) {
			statement.executeQuery();
		} catch (SQLException e){
			e.printStackTrace();
		} catch(NullPointerException e){
			e.printStackTrace();
		}
	}

	public static void insertData(String msg) throws SQLException{
		try(PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO testuser(Note) VALUES ('" + msg +"')"
			)) {
				statement.executeQuery();
		}
	}
	public static void insertData(String msg, String date, String time) throws SQLException{
		try(PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO testuser(Note, Date, Time) VALUES ('" + msg +"', '" + date +"', '" + time + "')"
			)) {
				statement.executeQuery();
		}
	}

	public static ArrayList<String[]> retrieveData() throws SQLException{
		try(PreparedStatement statement = connection.prepareStatement(
			"SELECT Note, Date, Time FROM testuser"
		)){
			try (ResultSet resultSet = statement.executeQuery()) {
				boolean empty = true;
				int i=0;
				ArrayList<String[]> rtn = new ArrayList<String[]>();
				while (resultSet.next()) {
					empty = false;
					rtn.add(new String[3]);
					rtn.get(i)[0] = resultSet.getString("Note");
					rtn.get(i)[1] = resultSet.getString("Date");
					rtn.get(i)[2] = resultSet.getString("Time");

					// System.out.println("\t> " + rtn.get(i)[0] + " . " + rtn.get(i)[1] + " : " + rtn.get(i)[2]);
					i++;
				}
				if (empty) {
					System.out.println("\t (no data)");
				}
				return rtn;
			}
		}
	}


	public static void initDatabaseConnection() throws SQLException {
		System.out.println("Connecting to the database...");
		connection = DriverManager.getConnection(
				"jdbc:mariadb://192.168.2.241:3306/NotesAndRemindersDB",
				"RemoteUser", "RemoteAccess");
		System.out.println("Connection valid: " + connection.isValid(5));
	}

	public static void closeDatabaseConnection() throws SQLException {
		System.out.println("Closing database connection...");
		connection.close();
		System.out.println("Connection valid: " + connection.isValid(5));
	}
}
