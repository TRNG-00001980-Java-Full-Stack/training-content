package configs;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	private static DBConnection dbc;
	
	private Connection connection;
	
	private DBConnection() {
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet","root","root");
		}catch(Exception ex) {
			ex.printStackTrace();		
		}
	}
	
	
	public static DBConnection getInstance() {
		if(dbc == null) {
			dbc = new DBConnection();
		}
		return dbc;
	}
	
	public Connection getConnection() {
		return connection;
	}

}


