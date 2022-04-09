import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
private static DB db = new DB();
	
	public String createUserTable() {
		Connection conn = null;
		Statement stmnt = null;
		String sql;
		String result = null;
		try {
			//get connection
			System.out.println("Verbinde mit Datenbank...");
			conn = db.getConnection();
			
			//create statement
			System.out.println("Erstelle Statement...");
			stmnt = conn.createStatement();
		
			DatabaseMetaData dbm = conn.getMetaData();
			
			//check if table already exists
			ResultSet tables = dbm.getTables(null, null, "user", null);
			if(tables.next()) {
				System.out.println("USER Tabelle existiert bereits!");
				result = "Existing";
			}
			else {
				System.out.println("Tabelle wird erstellt...");
				sql = "CREATE TABLE webapp.user " +
						"( userID INT NOT NULL AUTO_INCREMENT, " +
						"name VARCHAR(45) NULL, " + 
						"pwd VARCHAR(45) NULL, " + 
						"role VARCHAR(45) NULL, " + 
						"PRIMARY KEY(userID)); ";
				stmnt.executeUpdate(sql);
				System.out.println("user Tabelle erstellt");
				result = "Created";		
			}
			stmnt.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}finally {
			try {
				if(stmnt != null)
					stmnt.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			}catch(SQLException se1) {
				se1.printStackTrace();
			}
		}	
	}
	
	public static List<User> findAllUser() {
		List<User> userList = new ArrayList<User>();
		Connection conn = null;
		Statement stmnt = null;
		String sql;
		try {
			//open connection
			System.out.println("Verbinde mit Datenbank...");
			conn = db.getConnection();
			
			//create statement
			System.out.println("Erstelle Statement...");
			stmnt = conn.createStatement();
			
			//select all entries in table
			sql = "SELECT * FROM webapp.user;";
			ResultSet rs = stmnt.executeQuery(sql);
			
			//extract data from result
			while(rs.next()) {
				User currentUser = new User();
				currentUser.setName(rs.getString("name"));
				currentUser.setPwd(rs.getString("pwd"));
				currentUser.setUserID(rs.getInt("userID"));
				currentUser.setRole(rs.getString("role"));
				
				userList.add(currentUser);
			}
			rs.close();
			
			stmnt.close();
			conn.close();
			return userList;
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Fehler beim Lesen der user Tabelle!");
			return userList;
		}finally {
			try {
				if(stmnt != null)
					stmnt.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			}catch(SQLException se1) {
				se1.printStackTrace();
			}
		}  
	}
	
}
