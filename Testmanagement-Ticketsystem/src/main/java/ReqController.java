import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ViewScoped
public class ReqController{

	private List<Requirement> reqList;
	private Requirement singleReq; 
	
	private static final String KEY_IN_SESSION = "singleReq";
	
	public ReqController() {
		reqList = Webapp.getInstance().getReq();
		
		singleReq = (Requirement) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(KEY_IN_SESSION);
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(KEY_IN_SESSION, null);
	}
	

	// getter and setter
	public List<Requirement> getReqList() { 
		reqList = Webapp.getInstance().getReq();
		return reqList;
	}
	   
	public void setReqList(List<Requirement> reqList) { 
		this.reqList = reqList; 
	}
   
    public Requirement getSingleReq() { 
	   return singleReq; 
    }
   
    public void setSingleReq(Requirement singleReq) { 
	   this.singleReq = singleReq; 
    }   
   
   	//create new Requirement
	public String createNewReq() {
		reqList = Webapp.getInstance().getReq();
	    int idNew = 0;
	    for(Requirement r : reqList) {
	    	idNew = Math.max(idNew, r.getReqID().intValue() + 1);
	      }
	    singleReq = new Requirement(null, idNew, null, 1);
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(KEY_IN_SESSION, singleReq);
	    return "createSingleReq.xhtml";
	}
	
	//edit single Requirement
	public String editSingleReq() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(KEY_IN_SESSION, singleReq);
	    return "editSingleReq.xhtml";
	}
	
	//save single Requirement
	public String saveSingleReq() {
		ReqDAO.insertReq(singleReq);
		return "requirements.xhtml";
	}
	
	//update single Requirement
	public String updateSingleReq() {
		ReqDAO.updateReq(singleReq);
		return "requirements.xhtml";
	}
	
	//delete Requirement
	public String deleteSingleReq() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(KEY_IN_SESSION, singleReq);
		ReqDAO.deleteReq(singleReq);
		return null;
	}
	
	
	public void addFacesMessage(FacesMessage.Severity severity, String m){
		FacesMessage facesMsg = new FacesMessage(severity, m, null);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}


}