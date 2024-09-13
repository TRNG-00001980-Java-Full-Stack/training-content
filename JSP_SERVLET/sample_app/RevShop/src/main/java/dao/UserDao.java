package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import configs.DBConnection;
import models.User;

public class UserDao {
	
	private Connection conn;
	
	public UserDao() {
		this.conn =  DBConnection.getInstance().getConnection();
	}

	
	public void insertUser(User user) {
		try {
			PreparedStatement pst = conn.prepareStatement("INSERT INTO users (username, email, password) VALUES (?,?,?)");
			pst.setString(1, user.getUsername());
			pst.setString(2, user.getEmail());
			pst.setString(3, user.getPassword());
			
			pst.executeUpdate();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public List<User> getAllUsers(){
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users");
			
			List<User> li = new ArrayList<>();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				
				li.add(user);
			}
			return li;

		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteUser(int id) {
		try {
			PreparedStatement pst = conn.prepareStatement("DELETE FROM users WHERE id = ?");
			pst.setInt(1, id);
			int rows_affected = pst.executeUpdate();
			if (rows_affected > 0) {
				return true;
			}
			return false;
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
