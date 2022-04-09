import java.util.Objects;

public class User {
	
	private String name;
	private String pwd;
	private String role;
	private int userID;
	
	public User() {
		
	}
	
	public User(String name, String pwd) {
		this.name = name;
		this.pwd = pwd;
	}
	
	public User(String name, String pwd, int userID) {
		this.name = name;
		this.pwd = pwd;
		this.userID = userID;
	}
	
	public User(String name, String pwd, int userID, String role) {
		this.name = name;
		this.pwd = pwd;
		this.userID = userID;
		this.role = role;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPwd() {
		return this.pwd;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", pwd=" + pwd + ", role=" + role + ", userID=" + userID + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(name, other.name) && Objects.equals(pwd, other.pwd);
	}
	
	 @Override
	public int hashCode() {
		return Objects.hash(name, pwd, role, userID);
	}

}
