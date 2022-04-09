import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

@Named
public class Ticket implements Serializable{

	private static final long serialVersionUID = 2915526291070888005L;
	
	private String ticketName;
	private Integer ticketID;
	private Integer ticketCase;
	private String ticketResult;
	private Integer tester;
	private String testerName;
	private String tCaseName;
	
	
	//Constructor
	public Ticket() {
		
	}
	
	public Ticket(String name, int id, int tcase) {
		this.ticketName = name;
		this.ticketID = Integer.valueOf(id);
		this.ticketCase = tcase;
	}
	
	public Ticket(String name, int id, int tcase, int tester) {
		this.ticketName = name;
		this.ticketID = Integer.valueOf(id);
		this.ticketCase = Integer.valueOf(tcase);
		this.tester = Integer.valueOf(tester);
	}
	
	public Ticket(String name, int id, int tcase, int tester, String result) {
		this.ticketName = name;
		this.ticketID = Integer.valueOf(id);
		this.ticketCase = Integer.valueOf(tcase);
		this.tester = Integer.valueOf(tester);
		this.ticketResult = result;
	}
	
	
	//getter and setter
	public String getTicketName() {
		return ticketName;
	}
	
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	
	public Integer getTicketID() {
		return ticketID;
	}
	
	public void setTicketID(Integer ticketID) {
		this.ticketID = Integer.valueOf(ticketID);
	}
	
	public Integer getTicketCase() {
		return ticketCase;
	}
	
	public void setTicketCase(Integer ticketCase) {
		this.ticketCase = ticketCase;
		List <TestCase> caseList1 = Webapp.getInstance().getTestc();
		int index = ticketCase-1;
		this.tCaseName = caseList1.get(index).getCaseName();
	}
	
	public String getTicketResult() {
		return ticketResult;
	}
	
	public void setTicketResult(String ticketResult) {
		this.ticketResult = ticketResult;
	}
	
	public Integer getTester() {
		return tester;
	}
	
	public void setTester(Integer tester) {
		this.tester = Integer.valueOf(tester);
		List <User> userList1 = Webapp.getInstance().getUser();
		int index = tester-1;
		this.testerName = userList1.get(index).getName();
	}
	
	public String getTesterName() {
		return testerName;
	}

	public void setTesterName(String testerName) {
		this.testerName = testerName;
	}

	public String gettCaseName() {
		return tCaseName;
	}

	public void settCaseName(String tCaseName) {
		this.tCaseName = tCaseName;
	}

	//Override toString, equals and hashcode
	@Override
	public String toString() {
		return "{ticketID = " + ticketID + ", ticketName = " + ticketName + ", ticketCase = " + ticketCase + ", ticketResult = " + ticketResult + ", tester = " + tester +"}";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) { 
			return true; 
		}
	    if(obj == null) { 
	    	return false; 
	    }
	    if(getClass() != obj.getClass()) { 
	    	return false;
	    }
	    Ticket other = (Ticket) obj;
	    if(ticketID == null) { 
	    	return other.ticketID == null; 
	    }
	    return ticketID.equals(other.ticketID );
	}
		
	@Override
	public int hashCode() {
		return 31 + ((ticketID == null) ? 0 : ticketID.hashCode());
	}

}
