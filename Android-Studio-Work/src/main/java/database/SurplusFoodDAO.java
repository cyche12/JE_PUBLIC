package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SurplusFoodDAO {

    public List<SurplusFoodDTO> getDonatedSurplusFood() {
        List<SurplusFoodDTO> surplusFoods = new ArrayList<>();
        String sql = "SELECT * FROM surplusFood WHERE isDonated = TRUE AND isClaimed = FALSE";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                SurplusFoodDTO food = new SurplusFoodDTO();
                food.setSurplusId(rs.getInt("surplusId"));
                food.setFoodId(rs.getInt("foodId"));
                food.setRetailerId(rs.getInt("retailerId"));
                food.setListingType(rs.getString("listingType"));
                food.setListingDate(rs.getDate("listingDate"));
                food.setDonated(rs.getBoolean("isDonated"));
                food.setClaimed(rs.getBoolean("isClaimed"));
                food.setQuantity(rs.getInt("quantity"));
                surplusFoods.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return surplusFoods;
    }

    public List<SurplusFoodDTO> getAllClaimedFood() {
        List<SurplusFoodDTO> claimedFoods = new ArrayList<>();
        String sql = "SELECT * FROM surplusFood WHERE isClaimed = TRUE";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                SurplusFoodDTO food = new SurplusFoodDTO();
                food.setSurplusId(rs.getInt("surplusId"));
                food.setFoodId(rs.getInt("foodId"));
                food.setRetailerId(rs.getInt("retailerId"));
                food.setListingType(rs.getString("listingType"));
                food.setListingDate(rs.getDate("listingDate"));
                food.setDonated(rs.getBoolean("isDonated"));
                food.setClaimed(rs.getBoolean("isClaimed"));
                food.setQuantity(rs.getInt("quantity"));
                claimedFoods.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claimedFoods;
    }

    public List<SurplusFoodDTO> getAllSurplusFood() {
        List<SurplusFoodDTO> surplusFoods = new ArrayList<>();
        String sql = "SELECT * FROM surplusFood";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                SurplusFoodDTO food = new SurplusFoodDTO();
                food.setSurplusId(rs.getInt("surplusId"));
                food.setFoodId(rs.getInt("foodId"));
                food.setRetailerId(rs.getInt("retailerId"));
                food.setListingType(rs.getString("listingType"));
                food.setListingDate(rs.getDate("listingDate"));
                food.setDonated(rs.getBoolean("isDonated"));
                food.setClaimed(rs.getBoolean("isClaimed"));
                food.setQuantity(rs.getInt("quantity"));
                surplusFoods.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return surplusFoods;
    }

    public boolean listSurplusFood(SurplusFoodDTO surplusFood) {
        String sql = "INSERT INTO surplusFood (foodId, retailerId, listingType, listingDate, isDonated, isClaimed, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, surplusFood.getFoodId());
            pstmt.setInt(2, surplusFood.getRetailerId());
            pstmt.setString(3, surplusFood.getListingType());
            pstmt.setDate(4, surplusFood.getListingDate());
            pstmt.setBoolean(5, surplusFood.isDonated());
            pstmt.setBoolean(6, surplusFood.isClaimed());
            pstmt.setInt(7, surplusFood.getQuantity());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean claimSurplusFood(int foodId, int quantity) {
        String updateFoodItemSQL = "UPDATE foodItem SET foodQuantity = foodQuantity - ? WHERE foodId = ?";
        String updateSurplusFoodSQL = "UPDATE surplusFood SET isClaimed = TRUE WHERE foodId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement updateFoodItemPstmt = conn.prepareStatement(updateFoodItemSQL);
             PreparedStatement updateSurplusFoodPstmt = conn.prepareStatement(updateSurplusFoodSQL)) {
            conn.setAutoCommit(false);
            updateFoodItemPstmt.setInt(1, quantity);
            updateFoodItemPstmt.setInt(2, foodId);
            int updatedRows = updateFoodItemPstmt.executeUpdate();
            updateSurplusFoodPstmt.setInt(1, foodId);
            int surplusUpdatedRows = updateSurplusFoodPstmt.executeUpdate();
            conn.commit();
            return updatedRows > 0 && surplusUpdatedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean addReceivedItem(int foodId, int quantity) {
        String sql = "INSERT INTO received_food (foodId, quantity, receiveDate) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, foodId);
            pstmt.setInt(2, quantity);
            pstmt.setDate(3, new Date(System.currentTimeMillis()));
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAlreadyListed(int foodId, String listingType) {
        String sql = "SELECT COUNT(*) FROM surplusFood WHERE foodId = ? AND listingType = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, foodId);
            pstmt.setString(2, listingType);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateClaimedFoodQuantity(int foodId, int quantity) {
        String sql = "UPDATE surplusFood SET quantity = quantity - ? WHERE foodId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, foodId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<FoodItemDTO> identifySurplusFood() {
        List<FoodItemDTO> surplusFoods = new ArrayList<>();
        String sql = "SELECT * FROM foodItem WHERE foodQuantity > 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                FoodItemDTO food = new FoodItemDTO();
                food.setFoodId(rs.getInt("foodId"));
                food.setFoodName(rs.getString("foodName"));
                food.setFoodQuantity(rs.getInt("foodQuantity"));
                food.setFoodCost(rs.getDouble("foodCost"));
                food.setFoodExpiry(rs.getDate("foodExpiry"));
                surplusFoods.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return surplusFoods;
    }
}
