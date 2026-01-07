/********************************************************************************************************2*4*w*
 * File:  PhysicianDaoImpl.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databank.model.PhysicianPojo;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;

/**
 * Description: Implements the C-R-U-D API for the database
 *
 * TODO 01 - Some components are managed by CDI.<br>
 * TODO 02 - Methods which perform DML need @Transactional annotation.<br>
 * TODO 03 - Fix the syntax errors to correct methods. <br>
 * TODO 04 - Refactor this class. Move all the method bodies and EntityManager
 * to a new service class (e.g. StudentService) which is a singleton (EJB).<br>
 * TODO 05 - Inject the service class using EJB.<br>
 * TODO 06 - Call all the methods of service class from each appropriate method
 * here.
 */
@Named
@ApplicationScoped
public class PhysicianDaoImpl implements PhysicianDao, Serializable {
	/** explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;

	// Get the log4j2 logger for this class
	private static final Logger LOG = LogManager.getLogger();

	@EJB // Adding PhysicianService Class//
	private PhysicianService physicianService;

	protected EntityManager em;

	@Override
	public List<PhysicianPojo> readAllPhysicians() {
		LOG.debug("reading all physicians");
		// Use the named JPQL query from the PhysicianPojo class to grab all the
		// students
		return physicianService.readAllPhysicians();
	}

	@Override
	public PhysicianPojo createPhysician(PhysicianPojo physician) {
		LOG.debug("creating a physician = {}", physician);
		return physicianService.createPhysician(physician);
	}

	@Override
	public PhysicianPojo readPhysicianById(int physicianId) {
		LOG.debug("read a specific physician = {}", physicianId);
		return physicianService.readAllPhysicians().stream().filter(p -> p.getId() == physicianId).findFirst()
				.orElse(null);
	}

	@Override
	public PhysicianPojo updatePhysician(PhysicianPojo physicianWithUpdates) {
		LOG.debug("updating a specific physician = {}", physicianWithUpdates);
		return physicianService.updatePhysician(physicianWithUpdates);
	}

	@Override
	public void deletePhysicianById(int physicianId) {
		LOG.debug("deleting a specific physicianID = {}", physicianId);
		physicianService.deletePhysicianById(physicianId);
	}
}