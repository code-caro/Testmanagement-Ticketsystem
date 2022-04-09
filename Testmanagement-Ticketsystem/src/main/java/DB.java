import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
	
	public Connection getConnection() {
		try {
			//register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//open connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp", "root", "12345-SQL");
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	

	
}
