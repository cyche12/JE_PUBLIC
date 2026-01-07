//Class: CST8288//
//Section: 033//
//Professor: Islam Gomaa//
//Assignment: Final Project//
//Date: 23/7/2024//
//Authors: Jake Elliott, Navjot Kaur, Heeseok Yang, Scott Valair//

package services;

import enums.CommunicationMethod;

public class SubscriptionService {

	private int id;
	private int userId;
	private CommunicationMethod communicationMethod;
	private String preferences;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public int getUserId() {

		return userId;
	}

	public void setUserId(int userId) {

		this.userId = userId;
	}

	public CommunicationMethod getCommunicationMethod() {

		return communicationMethod;
	}

	public void setCommunicationMethod(CommunicationMethod communicationMethod) {

		this.communicationMethod = communicationMethod;
	}

	public String getPreferences() {

		return preferences;
	}

	public void setPreferences(String preferences) {

		this.preferences = preferences;
	}

}
