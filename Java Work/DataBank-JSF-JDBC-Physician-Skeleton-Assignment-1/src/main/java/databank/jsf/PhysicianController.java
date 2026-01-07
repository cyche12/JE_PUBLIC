/*********************************************************************************************************
 * File:  PhysicianController.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 * @author Hanna Felix document updated
 */
package databank.jsf;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.SessionMap;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import databank.dao.ListDataDao;
import databank.dao.PhysicianDao;
import databank.model.PhysicianPojo;

/**
 * Description:  Responsible for collection of Physician Pojo's in XHTML (list) <h:dataTable> </br>
 * Delegates all C-R-U-D behavior to DAO
 */

@Named("physicianController")
@SessionScoped
public class PhysicianController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	@SessionMap
	private Map<String, Object> sessionMap;

	@Inject
	private PhysicianDao physicianDao;

	@Inject
	private ListDataDao listDataDao;

	private List<PhysicianPojo> physicians;
	

	public void loadPhysicians() {
		setPhysicians(physicianDao.readAllPhysicians());
	}

	public List<PhysicianPojo> getPhysicians() {
		return physicians;
	}

	public void setPhysicians(List<PhysicianPojo> physicians) {
		this.physicians = physicians;
	}

	public List<String> getSpecialties() {
		return this.listDataDao.readAllSpecialties();
	}
	
	public String navigateToAddForm() {
		//Pay attention to the name here, it will be used as the object name in add-physician.xhtml
		//ex. <h:inputText value="#{newPhysician.firstName}" id="firstName" />
		sessionMap.put("newPhysician", new PhysicianPojo());
		return "add-physician.xhtml?faces-redirect=true";
	}

	public String submitPhysician(PhysicianPojo physician) {
		physician.setCreated(LocalDateTime.now());			
		physicianDao.createPhysician(physician);	
		return "list-physicians.xhtml?faces-redirect=true";
	}

	public String navigateToUpdateForm(int physicianId) {
		sessionMap.put("editingPhysician", physicianDao.readPhysicianById(physicianId));
		return "edit-physician.xhtml?faces-redirect=true";
	}

	public String submitUpdatedPhysician(PhysicianPojo physician) {
		physicianDao.updatePhysician(physician);
		return "list-physicians.xhtml?faces-redirect=true";
	}

	public String deletePhysician(int physicianId) {
		physicianDao.deletePhysicianById(physicianId);
		return "list-physicians.xhtml?faces-redirect=true";
	}
	

}
