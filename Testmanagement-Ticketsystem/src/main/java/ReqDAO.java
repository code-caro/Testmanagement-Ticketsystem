import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReqDAO {
	private static DB db = new DB();
	
	//create table
	public String createRequirementTable() {
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
			ResultSet tables = dbm.getTables(null, null, "requirement", null);
			if(tables.next()) {
				System.out.println("REQUIREMENT Tabelle existiert bereits!");
				result = "Existing";
			}
			else {
				System.out.println("Tabelle wird erstellt...");
				sql = "CREATE TABLE webapp.requirement " +
						"( reqName VARCHAR(45) NULL, " +
						"reqID INT NOT NULL AUTO_INCREMENT, " + 
						"reqDescription VARCHAR(125) NULL, " + 
						"reqVersion FLOAT NULL, " + 
						"PRIMARY KEY(reqID));";
				stmnt.executeUpdate(sql);
				System.out.println("requirement Tabelle erstellt");
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
	
	//extract data from table
	public static List<Requirement> findAllReq() {
		List<Requirement> reqList = new ArrayList<Requirement>();
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
			sql = "SELECT * FROM webapp.requirement;";
			ResultSet rs = stmnt.executeQuery(sql);
			
			//extract data from result
			while(rs.next()) {
				Requirement currentReq = new Requirement();
				currentReq.setReqName(rs.getString("reqName"));
				currentReq.setReqID(rs.getInt("reqID"));
				currentReq.setReqDescription(rs.getString("reqDescription"));
				currentReq.setReqVersion(rs.getFloat("reqVersion"));
				
				reqList.add(currentReq);
			}
			rs.close();
			
			stmnt.close();
			conn.close();
			return reqList;
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Fehler beim Lesen der requirement Tabelle!");
			return reqList;
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
	
	//save data into table
	public static long insertReq (Requirement req) {
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		String sql;
		try {
			//open connection
			System.out.println("Verbinde mit Datenbank...");
			conn = db.getConnection();
			
			sql = "INSERT INTO webapp.requirement"
					+ "(reqName, reqDescription, reqVersion) VALUES"
					+ "(?,?,?)";
			
			//create statement
			System.out.println("Erstelle prepared Statement...");
			prepStmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, req.getReqName());
			prepStmnt.setString(2, req.getReqDescription());
			prepStmnt.setFloat(3, req.getReqVersion());
			
			//execute SQL statement
			Integer affectedRows = prepStmnt.executeUpdate();
			
			Long idNewRow;
			if(affectedRows == 0 ) {
				throw new SQLException("Erstellen der Zeile fehlgeschlagen, keine Zeile betroffen.");
			}
			
			try (ResultSet generatedKeys = prepStmnt.getGeneratedKeys()) {
				if(generatedKeys.next()) {
					idNewRow = generatedKeys.getLong(1);
					System.out.println("ID von neuem Objekt: " + idNewRow);
				}else {
					throw new SQLException("Erstellen der Zeile fehlgeschlagen, keine ID."); 
				}
			}
			prepStmnt.close();
			conn.close();
			return idNewRow;
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Fehler beim Erstellen der Zeile in requirement");
			return 0;
		}finally {
			try {
				if (prepStmnt != null)
					prepStmnt.close();
			}catch (SQLException se) {
			}
			try {
				if(conn != null)
					conn.close();
			}catch (SQLException se1) {
				se1.printStackTrace();
			}
		}
	}
	
	//delete entry in table
	public static long deleteReq (Requirement req) {
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		String sql;
		try {
			//open connection
			System.out.println("Verbinde mit Datenbank...");
			conn = db.getConnection();
			
			sql = "DELETE FROM webapp.requirement"
					+ " WHERE reqID = ?";
			
			//create statement
			System.out.println("Erstelle prepared Statement...");
			prepStmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setLong(1, req.getReqID());
			
			//execute SQL statement
			Integer affectedRows = prepStmnt.executeUpdate();
			
			if(affectedRows == 0 ) {
				throw new SQLException("Löschen der Zeile fehlgeschlagen, keine Zeile betroffen.");
			}else {
				System.out.println("Objekt gelöscht!");
			}
			
			prepStmnt.close();
			conn.close();
			return affectedRows;
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Fehler beim Löschen der Zeile in requirement");
			return 0;
		}finally {
			try {
				if (prepStmnt != null)
					prepStmnt.close();
			}catch (SQLException se) {
			}
			try {
				if(conn != null)
					conn.close();
			}catch (SQLException se1) {
				se1.printStackTrace();
			}
		}
	}
	
	//update data into table
	public static long updateReq (Requirement req) {
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		String sql;
		try {
			//open connection
			System.out.println("Verbinde mit Datenbank...");
			conn = db.getConnection();
			
			sql = "UPDATE webapp.requirement SET "
					+ "reqName=?, reqDescription=?, reqVersion=?"
					+ " WHERE reqID=?";
			
			//create statement
			System.out.println("Erstelle prepared Statement...");
			prepStmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, req.getReqName());
			prepStmnt.setString(2, req.getReqDescription());
			prepStmnt.setFloat(3, req.getReqVersion());
			prepStmnt.setLong(4, req.getReqID());
			
			//execute SQL statement
			Integer affectedRows = prepStmnt.executeUpdate();
			
			Long idNewRow;
			if(affectedRows == 0 ) {
				throw new SQLException("Erstellen der Zeile fehlgeschlagen, keine Zeile betroffen.");
			}
			
			try (ResultSet generatedKeys = prepStmnt.getGeneratedKeys()) {
				if(generatedKeys.next()) {
					idNewRow = generatedKeys.getLong(1);
					System.out.println("ID von neuem Objekt: " + idNewRow);
				}else {
					throw new SQLException("Updating der Zeile fehlgeschlagen, keine ID."); 
				}
			}
			prepStmnt.close();
			conn.close();
			return idNewRow;
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Fehler beim Update der Zeile in requirement");
			return 0;
		}finally {
			try {
				if (prepStmnt != null)
					prepStmnt.close();
			}catch (SQLException se) {
			}
			try {
				if(conn != null)
					conn.close();
			}catch (SQLException se1) {
				se1.printStackTrace();
			}
		}
	}


}
