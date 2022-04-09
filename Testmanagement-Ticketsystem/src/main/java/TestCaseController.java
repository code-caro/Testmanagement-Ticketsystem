import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ViewScoped
public class TestCaseController {
	
	private List<TestCase> caseList;
	private TestCase singleCase;
	
	private static final String KEY_IN_SESSION = "singleCase";
		
	public TestCaseController() {
		caseList = Webapp.getInstance().getTestc();
		
		singleCase = (TestCase) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(KEY_IN_SESSION);
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(KEY_IN_SESSION, null);
	}

	//getter and setter
	
	public List<TestCase> getCaseList() {
		caseList = Webapp.getInstance().getTestc();
		return caseList;
	}
	
	public void setCaseList(List<TestCase> list) {
		this.caseList = list;
	}
	
	public TestCase getSingleCase() {
		return singleCase;
	}

	public void setSingleCase(TestCase singleCase) {
		this.singleCase = singleCase;
	}
	
	//create new Testcase
	public String createNewCase() {
		caseList = Webapp.getInstance().getTestc();
	    int idNew = 0;
	    for(TestCase me : caseList) {
	    	idNew = Math.max(idNew, me.getCaseID().intValue() + 1);
	      }
	    singleCase = new TestCase(null, idNew, null, null, null);
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(KEY_IN_SESSION, singleCase);
	    return "createSingleCase.xhtml";
	}
		
	//edit single Testcase
	public String editSingleCase() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(KEY_IN_SESSION, singleCase);
	    return "editSingleCase.xhtml";
	}

	//save single Testcase
	public String saveSingleCase() {
		TestCaseDAO.insertCase(singleCase);
		return "testcase.xhtml";
	}
	
	//update single TestCase
	public String updateSingleCase() {
		TestCaseDAO.updateCase(singleCase);
		return "testcase.xhtml";
	}
	
	//delete TestCase
	public String deleteSingleCase() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(KEY_IN_SESSION, singleCase);
		TestCaseDAO.deleteCase(singleCase);
		return null;
	}

	
	public void addFacesMessage(FacesMessage.Severity severity, String m){
	      FacesMessage facesMsg = new FacesMessage(severity, m, null);
	      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

	
}
