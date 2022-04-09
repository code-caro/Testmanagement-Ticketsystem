import java.io.Serializable;

import javax.inject.Named;

@Named
public class Requirement implements Serializable{

	private static final long serialVersionUID = -1096240399509429971L;
	
	private String reqName;
	private Integer reqID;
	private String reqDescription;
	private float reqVersion;
	
	//Constructor
	public Requirement() {	
	}
	
	public Requirement(String name, int id, String description, int version) {
		this.reqName = name;
		this.reqID = Integer.valueOf(id);
		this.reqDescription = description;
		this.reqVersion = version;
	}
	
	
	//getter and setter
	public String getReqName() {
		return reqName;
	}

	public void setReqName(String reqName) {
		this.reqName = reqName;
	}

	public Integer getReqID() {
		return reqID;
	}

	public void setReqID(Integer reqID) {
		this.reqID = Integer.valueOf(reqID);
	}

	public String getReqDescription() {
		return reqDescription;
	}

	public void setReqDescription(String reqDescription) {
		this.reqDescription = reqDescription;
	}

	public float getReqVersion() {
		return reqVersion;
	}

	public void setReqVersion(float reqVersion) {
		this.reqVersion = reqVersion;
	}
	
	//Override toString, equals and hashcode
	@Override
	public String toString() {
		return "{reqID = " + reqID + ", reqName = " + reqName + ", reqDescription = " + reqDescription + ", reqVersion = " + reqVersion + ", reqPriority = " + "}";
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
	    Requirement other = (Requirement) obj;
	    if(reqID == null) { 
	    	return other.reqID == null; 
	    }
	    return reqID.equals(other.reqID );
	}
	
	@Override
	public int hashCode() {
		return 31 + ((reqID == null) ? 0 : reqID.hashCode());
	}
	
}
