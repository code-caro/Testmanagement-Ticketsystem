import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
public class LoginController {
	
	private User user = new User();
	private String name;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public void postValidateName(ComponentSystemEvent ev) throws AbortProcessingException {
		UIInput temp = (UIInput)ev.getComponent();
		this.name = (String)temp.getValue();
	}
	
	public void validateLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException {  
		List<User> userList = Webapp.getInstance().getUser();
		for (User u : userList) {
			User temp = new User(this.name, (String) value);
			if (u.equals(temp))
				return;
		}
		throw new ValidatorException(new FacesMessage("Login leider falsch!"));
	}
	
	private boolean checkLogin(){
		List<User> userList = Webapp.getInstance().getUser();
			for (User u : userList) {
				if (u.equals(this.user))
					System.out.println("Login erfolgreich");
					return true;
			}
			System.out.println("Login falsch");
			return false;
			
	}


	public String login() {
		if (checkLogin())
			return "index.xhtml";
		else
			return "login.xhtml";
	}
	
	
}
