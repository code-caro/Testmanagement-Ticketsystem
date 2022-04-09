import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ViewScoped
public class UserController {
	
	private List<User> userList;
	private User singleUser; 
	
	private static final String KEY_IN_SESSION = "singleUser";
	
	public UserController() {
		userList = Webapp.getInstance().getUser();
		
		singleUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(KEY_IN_SESSION);
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(KEY_IN_SESSION, null);
	}

	public List<User> getUserList() {
		userList = Webapp.getInstance().getUser();
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public User getSingleUser() {
		return singleUser;
	}

	public void setSingleUser(User singleUser) {
		this.singleUser = singleUser;
	}
	
	public void addFacesMessage(FacesMessage.Severity severity, String m){
		FacesMessage facesMsg = new FacesMessage(severity, m, null);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

}
