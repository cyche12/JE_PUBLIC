package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FoodItemDAO {
    private static final Logger LOGGER = Logger.getLogger(FoodItemDAO.class.getName());

    public boolean addFoodItem(FoodItemDTO foodItem) {
        String sql = "INSERT INTO foodItem (foodName, foodQuantity, foodCost, foodExpiry, retailerId, isPurchased) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, foodItem.getFoodName());
            pstmt.setInt(2, foodItem.getFoodQuantity());
            pstmt.setDouble(3, foodItem.getFoodCost());
            pstmt.setDate(4, foodItem.getFoodExpiry());
            pstmt.setInt(5, foodItem.getRetailerId());
            pstmt.setBoolean(6, false); // 기본값은 구매되지 않음
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to add food item", e);
        }
        return false;
    }

    public boolean updateFoodItemQuantity(int foodId, int quantity) {
        String sql = "UPDATE foodItem SET foodQuantity = ? WHERE foodId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, foodId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update food item quantity", e);
        }
        return false;
    }

    public boolean purchaseFoodItem(int foodId) {
        String sql = "UPDATE foodItem SET isPurchased = TRUE WHERE foodId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, foodId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to purchase food item", e);
        }
        return false;
    }

    public List<FoodItemDTO> getAllFoodItems() {
        List<FoodItemDTO> foodItems = new ArrayList<>();
        String sql = "SELECT * FROM foodItem WHERE isPurchased = FALSE";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                FoodItemDTO foodItem = new FoodItemDTO();
                foodItem.setFoodId(rs.getInt("foodId"));
                foodItem.setFoodName(rs.getString("foodName"));
                foodItem.setFoodQuantity(rs.getInt("foodQuantity"));
                foodItem.setFoodCost(rs.getDouble("foodCost"));
                foodItem.setFoodExpiry(rs.getDate("foodExpiry"));
                foodItem.setRetailerId(rs.getInt("retailerId"));
                foodItems.add(foodItem);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to get all food items", e);
        }
        return foodItems;
    }

    public List<FoodItemDTO> getPurchasedFoodItems() {
        List<FoodItemDTO> foodItems = new ArrayList<>();
        String sql = "SELECT * FROM foodItem WHERE isPurchased = TRUE";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                FoodItemDTO foodItem = new FoodItemDTO();
                foodItem.setFoodId(rs.getInt("foodId"));
                foodItem.setFoodName(rs.getString("foodName"));
                foodItem.setFoodQuantity(rs.getInt("foodQuantity"));
                foodItem.setFoodCost(rs.getDouble("foodCost"));
                foodItem.setFoodExpiry(rs.getDate("foodExpiry"));
                foodItem.setRetailerId(rs.getInt("retailerId"));
                foodItems.add(foodItem);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to get purchased food items", e);
        }
        return foodItems;
    }
}
