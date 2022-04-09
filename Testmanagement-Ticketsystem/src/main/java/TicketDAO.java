import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
	
private static DB db = new DB();
	
	public String createTicketTable() {
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
			ResultSet tables = dbm.getTables(null, null, "ticket", null);
			if(tables.next()) {
				System.out.println("TICKET Tabelle existiert bereits!");
				result = "Existing";
			}
			else {
				System.out.println("Tabelle wird erstellt...");
				sql = "CREATE TABLE webapp.ticket " +
						"( ticketName VARCHAR(45) NULL, " +
						"ticketID INT NOT NULL AUTO_INCREMENT, " + 
						"ticketCase INT NULL, " + 
						"tester INT NULL, " + 
						"ticketresult VARCHAR(125) NULL, " +
						"PRIMARY KEY(ticketID), " +
						"INDEX caseID_idx (ticketCase ASC) VISIBLE, " +
						"INDEX userID_idx (tester ASC) VISIBLE, " +
						"CONSTRAINT caseID FOREIGN KEY (ticketCase) " +
						"REFERENCES webapp.testcase (caseID) " +
						"ON DELETE SET NULL ON UPDATE CASCADE, " +
						"CONSTRAINT userID FOREIGN KEY (tester) " +
						"REFERENCES webapp.user (userID) " +
						"ON DELETE SET NULL ON UPDATE CASCADE);";
				stmnt.executeUpdate(sql);
				System.out.println("ticket Tabelle erstellt");
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
	
	public static List<Ticket> findAllTickets() {
		List<Ticket> ticketList = new ArrayList<Ticket>();
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
			sql = "SELECT * FROM webapp.ticket;";
			ResultSet rs = stmnt.executeQuery(sql);
			
			//extract data from result
			while(rs.next()) {
				Ticket currentTicket = new Ticket();
				currentTicket.setTicketName(rs.getString("ticketName"));
				currentTicket.setTicketID(rs.getInt("ticketID"));
				currentTicket.setTicketCase(rs.getInt("ticketCase"));
				currentTicket.setTester(rs.getInt("tester"));
				currentTicket.setTicketResult(rs.getString("ticketresult"));
				
				ticketList.add(currentTicket);
			}
			rs.close();
			
			stmnt.close();
			conn.close();
			return ticketList;
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Fehler beim Lesen der ticket Tabelle!");
			return ticketList;
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
	public static long insertTicket (Ticket ticket) {
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		String sql;
		try {
			//open connection
			System.out.println("Verbinde mit Datenbank...");
			conn = db.getConnection();
			
			sql = "INSERT INTO webapp.ticket"
					+ "(ticketName, ticketCase, tester, ticketresult) VALUES"
					+ "(?,?,?,?)";
			
			//create statement
			System.out.println("Erstelle prepared Statement...");
			prepStmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, ticket.getTicketName());
			prepStmnt.setInt(2, ticket.getTicketCase());
			prepStmnt.setInt(3, ticket.getTester());
			prepStmnt.setString(4, ticket.getTicketResult());
			
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
			System.out.println("Fehler beim Erstellen der Zeile in ticket");
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
		public static long deleteTicket (Ticket ticket) {
			Connection conn = null;
			PreparedStatement prepStmnt = null;
			String sql;
			try {
				//open connection
				System.out.println("Verbinde mit Datenbank...");
				conn = db.getConnection();
				
				sql = "DELETE FROM webapp.ticket"
						+ " WHERE ticketID = ?";
				
				//create statement
				System.out.println("Erstelle prepared Statement...");
				prepStmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prepStmnt.setLong(1, ticket.getTicketID());
				
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
				System.out.println("Fehler beim Löschen der Zeile in ticket");
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
		public static long updateReq (Ticket ticket) {
			Connection conn = null;
			PreparedStatement prepStmnt = null;
			String sql;
			try {
				//open connection
				System.out.println("Verbinde mit Datenbank...");
				conn = db.getConnection();
				
				sql = "UPDATE webapp.ticket SET "
						+ "ticketName=?, ticketCase=?, tester=?, ticketresult=?"
						+ " WHERE ticketID=?";
				
				//create statement
				System.out.println("Erstelle prepared Statement...");
				prepStmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prepStmnt.setString(1, ticket.getTicketName());
				prepStmnt.setInt(2, ticket.getTicketCase());
				prepStmnt.setInt(3, ticket.getTester());
				prepStmnt.setString(4, ticket.getTicketResult());
				prepStmnt.setLong(5, ticket.getTicketID());
				
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
				System.out.println("Fehler beim Update der Zeile in ticket");
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
