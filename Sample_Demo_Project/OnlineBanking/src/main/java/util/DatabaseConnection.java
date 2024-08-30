package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	private static DatabaseConnection instance;
	private static Connection connection=null;

	private DatabaseConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3360/OnlineBanking", "root","Sreealee@2609");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DatabaseConnection getInstance() {
		if (instance == null) {
			instance = new DatabaseConnection();
		}
		return instance;
	}
	public static Connection getConnection() {
		return connection;
	}
}
