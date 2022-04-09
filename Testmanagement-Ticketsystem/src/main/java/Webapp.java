import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

@Named
public class Webapp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2311638058101361206L;
	
	private List<User> user = new ArrayList<User>();
	private List<Requirement> req = new ArrayList<Requirement>();
	private List<TestCase> testc = new ArrayList<TestCase>();
	private List<Ticket> ticket = new ArrayList<Ticket>();	
	private static Webapp instance = new Webapp();
	
	
	public Webapp() {
	}
	
	public List<User> getUser(){
		user = UserDAO.findAllUser();
		return user;
	}
	
	public List<Requirement> getReq(){
		req = ReqDAO.findAllReq();
		return req;
	}
	
	public List<TestCase> getTestc(){
		testc = TestCaseDAO.findAllCases();
		return testc;
	}
	
	public List<Ticket> getTicket(){
		ticket = TicketDAO.findAllTickets();
		return ticket;
	}
	
	public static Webapp getInstance() {
		return instance;
	}


}
