package database;

import java.sql.Date;

public class SurplusFoodDTO {
    private int surplusId;
    private int foodId;
    private int retailerId;
    private String listingType;
    private Date listingDate;
    private boolean isDonated;
    private boolean isClaimed;
    private int quantity;

    public int getSurplusId() {
        return surplusId;
    }

    public void setSurplusId(int surplusId) {
        this.surplusId = surplusId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public Date getListingDate() {
        return listingDate;
    }

    public void setListingDate(Date listingDate) {
        this.listingDate = listingDate;
    }

    public boolean isDonated() {
        return isDonated;
    }

    public void setDonated(boolean isDonated) {
        this.isDonated = isDonated;
    }

    public boolean isClaimed() {
        return isClaimed;
    }

    public void setClaimed(boolean isClaimed) {
        this.isClaimed = isClaimed;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
