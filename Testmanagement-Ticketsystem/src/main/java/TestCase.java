import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.inject.Named;

@Named
public class TestCase implements Serializable{

	private static final long serialVersionUID = 7135062233928176534L;
	
	private String caseName;
	private Integer caseID;
	private String caseDescription;
	private String result;
	private Integer caseReq;
	private String reqCaseName; 
	
	//Constructor
	public TestCase() {
	}
	
	public TestCase (String name, Integer id, String description) {
		this.caseName = name;
		this.caseID = Integer.valueOf(id);
		this.caseDescription = description;
	}
	
	public TestCase (String name, Integer id, String description, String result) {
		this.caseName = name;
		this.caseID = Integer.valueOf(id);
		this.caseDescription = description;
		this.result = result;
	}
	
	public TestCase (String name, Integer id, String description, String result, Integer caseReq) {
		this.caseName = name;
		this.caseID = Integer.valueOf(id);
		this.caseDescription = description;
		this.result = result;
		this.caseReq = Integer.valueOf(caseReq);
	}
	
	
    //getter and setter
	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public Integer getCaseID() {
		return caseID;
	}

	public void setCaseID(int caseID) {
		this.caseID = Integer.valueOf(caseID);
	}

	public String getCaseDescription() {
		return caseDescription;
	}

	public void setCaseDescription(String caseDescription) {
		this.caseDescription = caseDescription;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public Integer getCaseReq() {
		return caseReq;
	}

	public void setCaseReq(Integer caseReq) {
		this.caseReq =  Integer.valueOf(caseReq);
		List <Requirement> reqList1 = Webapp.getInstance().getReq();
		int index = caseReq-1;
		this.reqCaseName = reqList1.get(index).getReqName();		
	}

	
	public String getReqCaseName() {
		return reqCaseName;
	}

	public void setReqCaseName(String reqCaseName) {
		this.reqCaseName  = reqCaseName;
	}

	@Override
	public String toString() {
		return "TestCase [caseName=" + caseName + ", caseID=" + caseID + ", caseDescription=" + caseDescription
				+ ", result=" + result + ", caseReq=" + caseReq + ", reqCaseName=" + reqCaseName + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestCase other = (TestCase) obj;
		return Objects.equals(caseDescription, other.caseDescription) && Objects.equals(caseID, other.caseID)
				&& Objects.equals(caseName, other.caseName) && Objects.equals(caseReq, other.caseReq)
				&& Objects.equals(reqCaseName, other.reqCaseName) && Objects.equals(result, other.result);
	}
		
	@Override
	public int hashCode() {
		return Objects.hash(caseDescription, caseID, caseName, caseReq, reqCaseName, result);
	}
			
}
