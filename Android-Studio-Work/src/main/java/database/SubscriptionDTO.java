package database;

import enums.CommunicationMethod;

public class SubscriptionDTO {

    private int subscriptionId;
    private int userId;
    private String location;
    private CommunicationMethod communicationMethod;
    private String foodPreferences;

    public SubscriptionDTO() {}

    public SubscriptionDTO(int subscriptionId, int userId, String location, CommunicationMethod communicationMethod, String foodPreferences) {
        this.subscriptionId = subscriptionId;
        this.userId = userId;
        this.location = location;
        this.communicationMethod = communicationMethod;
        this.foodPreferences = foodPreferences;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CommunicationMethod getCommunicationMethod() {
        return communicationMethod;
    }

    public void setCommunicationMethod(CommunicationMethod communicationMethod) {
        this.communicationMethod = communicationMethod;
    }

    public String getFoodPreferences() {
        return foodPreferences;
    }

    public void setFoodPreferences(String foodPreferences) {
        this.foodPreferences = foodPreferences;
    }
}
