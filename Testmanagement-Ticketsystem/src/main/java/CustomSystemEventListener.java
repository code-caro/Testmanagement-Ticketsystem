import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public class CustomSystemEventListener implements SystemEventListener{

	@Override
	public boolean isListenerForSource(Object value) {
		// TODO Auto-generated method stub
		return (value instanceof Application);
	}

	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if(event instanceof PostConstructApplicationEvent) {
			System.out.println("Application started. PostConstructApplicationEvent occured");
			ReqDAO reqDB = new ReqDAO();
			reqDB.createRequirementTable();
			TestCaseDAO caseDB = new TestCaseDAO();
			caseDB.createTestCaseTable();
			UserDAO userDB = new UserDAO();
			userDB.createUserTable();
			TicketDAO ticketDB = new TicketDAO();
			ticketDB.createTicketTable();
			
		}
		if(event instanceof PreDestroyApplicationEvent) {
			System.out.println("PreDestroyApplicationEvent occured. Application is stopping");
		}
	}
}
