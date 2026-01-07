package database;

import java.sql.Date;

public class FoodItemDTO {
    private int foodId;
    private String foodName;
    private int foodQuantity;
    private double foodCost;
    private Date foodExpiry;
    private int retailerId;

    // Getters and Setters
    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public double getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(double foodCost) {
        this.foodCost = foodCost;
    }

    public Date getFoodExpiry() {
        return foodExpiry;
    }

    public void setFoodExpiry(Date foodExpiry) {
        this.foodExpiry = foodExpiry;
    }

    public int getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }
}
