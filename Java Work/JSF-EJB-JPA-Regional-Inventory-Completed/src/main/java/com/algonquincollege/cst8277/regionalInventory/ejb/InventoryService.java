/*****************************************************************
 * File: InventoryService.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author (original) Mike Norman
 *
 */
package com.algonquincollege.cst8277.regionalInventory.ejb;

import java.io.Serializable;
import java.util.List;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;

import com.algonquincollege.cst8277.regionalInventory.model.InventoryPojo;

/**
 * Stateless Session Bean - InventoryService
 */
//@Stateless
@Singleton
public class InventoryService implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(name = "regionalInventory-PU")
	protected EntityManager em;

	/**
	 * Default constructor.
	 */
	public InventoryService() {
	}

	public List<InventoryPojo> findAllInventorys() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<InventoryPojo> cq = cb.createQuery(InventoryPojo.class);
		cq.select(cq.from(InventoryPojo.class));
		return em.createQuery(cq).getResultList();
	}

	public List<InventoryPojo> findAllInventorysForRegion(String region) {
		TypedQuery<InventoryPojo> tq = em.createQuery("select i from Inventory i where i.region = :param1",
				InventoryPojo.class);
		tq.setParameter("param1", region);
		return tq.getResultList();
	}

	@Transactional
	public InventoryPojo persistInventory(InventoryPojo inventory) {
		em.persist(inventory);
		return inventory;
	}

	public InventoryPojo findInventoryByPrimaryKey(int empPK) {
		return em.find(InventoryPojo.class, empPK);
	}

	@Transactional
	public void mergeInventory(InventoryPojo inventoryWithUpdates) {
		InventoryPojo inventoryToBeUpdated = findInventoryByPrimaryKey(inventoryWithUpdates.getId());
		if (inventoryToBeUpdated != null) {
			em.merge(inventoryWithUpdates);
		}
	}

	@Transactional
	public void removeInventory(int inventoryId) {
		InventoryPojo inventory = findInventoryByPrimaryKey(inventoryId);
		if (inventory != null) {
			em.refresh(inventory);
			em.remove(inventory);
		}
	}

}