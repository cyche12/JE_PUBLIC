package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import enums.CommunicationMethod;

public class SubscriptionDAO {

    public boolean addSubscription(SubscriptionDTO subscription) {
        String sql = "INSERT INTO subscription (userId, location, communicationMethod, foodPreferences) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, subscription.getUserId());
            pstmt.setString(2, subscription.getLocation());
            pstmt.setString(3, subscription.getCommunicationMethod().name());
            pstmt.setString(4, subscription.getFoodPreferences());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public SubscriptionDTO getSubscriptionById(int subscriptionId) {
        String sql = "SELECT * FROM subscription WHERE subscriptionId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, subscriptionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                SubscriptionDTO subscription = new SubscriptionDTO();
                subscription.setSubscriptionId(rs.getInt("subscriptionId"));
                subscription.setUserId(rs.getInt("userId"));
                subscription.setLocation(rs.getString("location"));
                subscription.setCommunicationMethod(CommunicationMethod.valueOf(rs.getString("communicationMethod")));
                subscription.setFoodPreferences(rs.getString("foodPreferences"));
                return subscription;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateSubscription(SubscriptionDTO subscription) {
        String sql = "UPDATE subscription SET userId = ?, location = ?, communicationMethod = ?, foodPreferences = ? WHERE subscriptionId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, subscription.getUserId());
            pstmt.setString(2, subscription.getLocation());
            pstmt.setString(3, subscription.getCommunicationMethod().name());
            pstmt.setString(4, subscription.getFoodPreferences());
            pstmt.setInt(5, subscription.getSubscriptionId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSubscription(int subscriptionId) {
        String sql = "DELETE FROM subscription WHERE subscriptionId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, subscriptionId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SubscriptionDTO> getAllSubscriptions() {
        List<SubscriptionDTO> subscriptions = new ArrayList<>();
        String sql = "SELECT * FROM subscription";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                SubscriptionDTO subscription = new SubscriptionDTO();
                subscription.setSubscriptionId(rs.getInt("subscriptionId"));
                subscription.setUserId(rs.getInt("userId"));
                subscription.setLocation(rs.getString("location"));
                subscription.setCommunicationMethod(CommunicationMethod.valueOf(rs.getString("communicationMethod")));
                subscription.setFoodPreferences(rs.getString("foodPreferences"));
                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }
}
