import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ViewScoped
public class TicketController {

	private List<Ticket> ticketList;
	private Ticket singleTicket;
	private int ticketcount;
	
	private static final String KEY_IN_SESSION = "singleTicket";
	
	public TicketController() {
		ticketList = Webapp.getInstance().getTicket();
		
		singleTicket = (Ticket) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(KEY_IN_SESSION);
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(KEY_IN_SESSION, null);
	}
	
	
	//getter and setter
	public List<Ticket> getTicketList() {
		ticketList = Webapp.getInstance().getTicket();
		return ticketList;
	}
	
	public void setTicketList(List<Ticket> list) {
		this.ticketList = list;
	}

	public Ticket getSingleTicket() {
		return singleTicket;
	}

	public void setSingleTicket(Ticket singleTicket) {
		this.singleTicket = singleTicket;
	}
	
	
	//create new Ticket
	public String createNewTicket() {
		ticketList = Webapp.getInstance().getTicket();
	    int idNew = 0;
	    for(Ticket ti : ticketList) {
	    	idNew = Math.max(idNew, ti.getTicketID().intValue() + 1);
	    }
	    singleTicket = new Ticket(null, idNew, 0, 0, null);
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(KEY_IN_SESSION, singleTicket);
	    return "createSingleTicket.xhtml";
	}
		
	//edit single Ticket
	public String editSingleTicket() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(KEY_IN_SESSION, singleTicket);
	    return "editSingleTicket.xhtml";
	}

	//save single Ticket
	public String saveSingleTicket() {
		TicketDAO.insertTicket(singleTicket);
	    return "tickets.xhtml";
	}
	
	//update single Ticket
	public String updateSingleTicket() {
		TicketDAO.updateReq(singleTicket);
		return "tickets.xhtml";
	}
		
	//delete Ticket
	public String deleteSingleReq() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(KEY_IN_SESSION, singleTicket);
		TicketDAO.deleteTicket(singleTicket);
		return null;
	}
	
	public void addFacesMessage(FacesMessage.Severity severity, String m){
	      FacesMessage facesMsg = new FacesMessage(severity, m, null);
	      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	   }

}
