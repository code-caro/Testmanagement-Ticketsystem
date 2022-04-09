import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestCaseDAO {
	private static DB db = new DB();
	
	public String createTestCaseTable() {
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
			ResultSet tables = dbm.getTables(null, null, "testcase", null);
			if(tables.next()) {
				System.out.println("TESTCASE Tabelle existiert bereits!");
				result = "Existing";
			}
			else {
				System.out.println("Tabelle wird erstellt...");
				sql = "CREATE TABLE webapp.testcase " +
						"( caseName VARCHAR(45) NULL, " +
						"caseID INT NOT NULL AUTO_INCREMENT, " + 
						"caseDescription VARCHAR(125) NULL, " + 
						"result VARCHAR(125) NULL, " +
						"caseReq INT NULL, " + 
						"PRIMARY KEY(caseID), " +
						"INDEX reqID_idx (caseReq ASC) VISIBLE, " +
						"CONSTRAINT reqID FOREIGN KEY (caseReq) " +
						"REFERENCES webapp.requirement (reqID) " +
						"ON DELETE SET NULL ON UPDATE CASCADE);";
				stmnt.executeUpdate(sql);
				System.out.println("testcase Tabelle erstellt");
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
	
	public static List<TestCase> findAllCases() {
		List<TestCase> caseList = new ArrayList<TestCase>();
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
			sql = "SELECT * FROM webapp.testcase;";
			ResultSet rs = stmnt.executeQuery(sql);
			
			//extract data from result
			while(rs.next()) {
				TestCase currentCase = new TestCase();
				currentCase.setCaseName(rs.getString("caseName"));
				currentCase.setCaseID(rs.getInt("caseID"));
				currentCase.setCaseDescription(rs.getString("caseDescription"));
				currentCase.setResult(rs.getString("result"));
				currentCase.setCaseReq(rs.getInt("caseReq"));
				
				caseList.add(currentCase);
			}
			rs.close();
			
			stmnt.close();
			conn.close();
			return caseList;
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Fehler beim Lesen der testcase Tabelle!");
			return caseList;
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
		public static long insertCase (TestCase tcase) {
			Connection conn = null;
			PreparedStatement prepStmnt = null;
			String sql;
			try {
				//open connection
				System.out.println("Verbinde mit Datenbank...");
				conn = db.getConnection();
				
				sql = "INSERT INTO webapp.testcase"
						+ "(caseName, caseDescription, result, caseReq) VALUES"
						+ "(?,?,?,?)";
				
				//create statement
				System.out.println("Erstelle prepared Statement...");
				prepStmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prepStmnt.setString(1, tcase.getCaseName());
				prepStmnt.setString(2, tcase.getCaseDescription());
				prepStmnt.setString(3, tcase.getResult());
				prepStmnt.setInt(4, tcase.getCaseReq());
				
				//execute SGL statement
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
				System.out.println("Fehler beim Erstellen der Zeile in testcase");
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
		public static long deleteCase (TestCase tcase) {
			Connection conn = null;
			PreparedStatement prepStmnt = null;
			String sql;
			try {
				//open connection
				System.out.println("Verbinde mit Datenbank...");
				conn = db.getConnection();
				
				sql = "DELETE FROM webapp.testcase"
						+ " WHERE caseID = ?";
				
				//create statement
				System.out.println("Erstelle prepared Statement...");
				prepStmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prepStmnt.setLong(1, tcase.getCaseID());
				
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
				System.out.println("Fehler beim Löschen der Zeile in testcase");
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
		public static long updateCase (TestCase tcase) {
			Connection conn = null;
			PreparedStatement prepStmnt = null;
			String sql;
			try {
				//open connection
				System.out.println("Verbinde mit Datenbank...");
				conn = db.getConnection();
				
				sql = "UPDATE webapp.testcase SET "
						+ "caseName=?, caseDescription=?, result=?, caseReq=?"
						+ " WHERE caseID=?";
				
				//create statement
				System.out.println("Erstelle prepared Statement...");
				prepStmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prepStmnt.setString(1, tcase.getCaseName());
				prepStmnt.setString(2, tcase.getCaseDescription());
				prepStmnt.setString(3, tcase.getResult());
				prepStmnt.setInt(4, tcase.getCaseReq());
				prepStmnt.setLong(5, tcase.getCaseID());
				
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
				System.out.println("Fehler beim Update der Zeile in testcase");
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
