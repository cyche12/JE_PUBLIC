package databank.dao;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

import databank.model.PhysicianPojo;

@Singleton
public class PhysicianService {

@PersistenceContext(name = "PU_DataBank")
private EntityManager em;

public List<PhysicianPojo> readAllPhysicians() {
	TypedQuery<PhysicianPojo> allPhysiciansQuery = em.createNamedQuery(PhysicianPojo.PHYSICIAN_FIND_ALL,
				PhysicianPojo.class);
		return allPhysiciansQuery.getResultList();
	}

	@Transactional
	public PhysicianPojo createPhysician(PhysicianPojo physician) {
		em.persist(physician);
		return physician;
	}

	public PhysicianPojo readPhysicianById(int physicianId) {
		return em.find(PhysicianPojo.class, physicianId);
	}

	@Transactional
	public PhysicianPojo updatePhysician(PhysicianPojo physicianWithUpdates) {
		return em.merge(physicianWithUpdates);
	}

	@Transactional
	public void deletePhysicianById(int physicianId) {
		PhysicianPojo physician = readPhysicianById(physicianId);
		if (physician != null) {
			em.remove(em.contains(physician) ? physician : em.merge(physician));
		}
	}
}
