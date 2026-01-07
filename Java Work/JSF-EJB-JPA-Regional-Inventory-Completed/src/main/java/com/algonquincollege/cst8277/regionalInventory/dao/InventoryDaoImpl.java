/*****************************************************************
 * InventoryDaoImpl.java Course Materials CST 8277
 * 
 * @author Teddy Yap
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.regionalInventory.dao;

import java.io.Serializable;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import com.algonquincollege.cst8277.regionalInventory.ejb.InventoryService;
import com.algonquincollege.cst8277.regionalInventory.model.InventoryPojo;

@Named
@ApplicationScoped
public class InventoryDaoImpl implements InventoryDao, Serializable {
	/** explicit set serialVersionUID */
	private static final long serialVersionUID = 1L;

	@EJB
	protected InventoryService inventoryService;

	@Override
	public List< InventoryPojo> readAllInventoryForRegion( String region) {
		return inventoryService.findAllInventorysForRegion( region);
	}

	//TODO - fill in rest of C-R-U-D methods
	public void createInventory(InventoryPojo inv) {
		inventoryService.persistInventory(inv);
	}
	
	public InventoryPojo readInventoryById(int invId) {
		return inventoryService.findInventoryByPrimaryKey(invId);
	}
	
	public void updateInventory(InventoryPojo inv) {
		inventoryService.mergeInventory(inv);
	}

	public void deleteInventory(int invId) {
		inventoryService.removeInventory(invId);
	}
	
	
}