/*****************************************************************
 * File:  InventoryController.java
 * Course Materials CST 8277
 * @author Teddy Yap
 * @author (original) Prof. Mike Norman
 *
 */
package com.algonquincollege.cst8277.regionalInventory.jsf;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.SessionMap;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.algonquincollege.cst8277.regionalInventory.model.InventoryPojo;
import com.algonquincollege.cst8277.regionalInventory.dao.InventoryDao;

/**
* JSF Controller class for Regional Inventory (version 2)
*
* @author Prof. Mike Norman
*/
@Named // used to be @javax.faces.bean.ManagedBean from JSF spec, but as of Java EE 7 (JSF 2.2),
       // switched to use CDI (Contexts and Dependency Injection) javax.inject
       // Note:  Cannot 'mix' JSF and CDI scope annotations
@SessionScoped // this scope (like @RequestScoped or @ApplicationScoped) is about *how many* instances there are
               // and *how long* they are in scope:
               //  @RequestScoped - 1 for every (HTTP) request, goes out-of-scope when request finishes
               //  @SessionScoped - 1 for every session (ie. browser session), goes out-of-scope when session finishes (browser closes)
               //  @ApplicationScoped - 1 for the application, goes out-of-scope when app terminates/is un-deployed
public class InventoryController implements Serializable {
    private static final long serialVersionUID = 1L;

    @SessionMap
    @Inject
    private Map<String, Object> sessionMap;
    
    private static Map<String,String> regions;
    static {
        //label, value
        regions = new LinkedHashMap<String,String>();
        regions.put("Canada", "CA");
        regions.put("United States", "US");
    }

    protected String region = "CA";
    protected InventoryDao theDao;
    protected List<InventoryPojo> theInventoryList;

    @Inject
    public InventoryController(InventoryDao theDao) {
        this.theDao = theDao;
    }

    public void loadInventory() {
        theInventoryList = theDao.readAllInventoryForRegion(region);
    }

    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    public Map<String, String> getRegions() {
        return regions;
    }

    public List<InventoryPojo> getInventory() {
        return theInventoryList;
    }
    public void setInventory(List<InventoryPojo> theInventoryList) {
        this.theInventoryList = theInventoryList;
    }

    //value change event listener
    public void valueChangeMethod(ValueChangeEvent e) {
        Object newChangedValue = e.getNewValue();
        if (newChangedValue != null) {
          setRegion(newChangedValue.toString());
          loadInventory();
        }
    }

    public String goToAddInventory() {
    	sessionMap.put("newInventory", new InventoryPojo());
    	return "add-inventory.xhtml?faces-redirect=true";
    }
    
    public String submitNewInventory(InventoryPojo inv) {
    	theDao.createInventory(inv);
    	return "index.xhtml?faces-redirect=true";
    }
    
    public String goToEditInventory(int invId) {
    	sessionMap.put("existingInventory", theDao.readInventoryById(invId));
    	return "edit-inventory.xhtml?faces-redirect=true";
    }
    
    public String submitExistingInventory(InventoryPojo inv) {
    	theDao.updateInventory(inv);
    	return "index.xhtml?faces-redirect=true";
    }
    
    public String deleteInventory(int invId) {
    	theDao.deleteInventory(invId);
    	return "index.xhtml?faces-redirect=true";
    }

}