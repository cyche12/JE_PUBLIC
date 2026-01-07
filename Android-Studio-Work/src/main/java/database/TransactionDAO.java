package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import enums.PurchaseType;

public class TransactionDAO {

    public boolean addTransaction(TransactionDTO transaction) {
        String sql = "INSERT INTO transaction (foodId, userId, purchaseType, purchaseDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transaction.getFoodId());
            pstmt.setInt(2, transaction.getUserId());
            pstmt.setString(3, transaction.getPurchaseType().name());
            pstmt.setDate(4, transaction.getPurchaseDate());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public TransactionDTO getTransactionById(int transactionId) {
        String sql = "SELECT * FROM transaction WHERE transactionId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                TransactionDTO transaction = new TransactionDTO();
                transaction.setTransactionId(rs.getInt("transactionId"));
                transaction.setFoodId(rs.getInt("foodId"));
                transaction.setUserId(rs.getInt("userId"));
                transaction.setPurchaseType(PurchaseType.valueOf(rs.getString("purchaseType")));
                transaction.setPurchaseDate(rs.getDate("purchaseDate"));
                return transaction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateTransaction(TransactionDTO transaction) {
        String sql = "UPDATE transaction SET foodId = ?, userId = ?, purchaseType = ?, purchaseDate = ? WHERE transactionId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transaction.getFoodId());
            pstmt.setInt(2, transaction.getUserId());
            pstmt.setString(3, transaction.getPurchaseType().name());
            pstmt.setDate(4, transaction.getPurchaseDate());
            pstmt.setInt(5, transaction.getTransactionId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTransaction(int transactionId) {
        String sql = "DELETE FROM transaction WHERE transactionId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<TransactionDTO> getAllTransactions() {
        List<TransactionDTO> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                TransactionDTO transaction = new TransactionDTO();
                transaction.setTransactionId(rs.getInt("transactionId"));
                transaction.setFoodId(rs.getInt("foodId"));
                transaction.setUserId(rs.getInt("userId"));
                transaction.setPurchaseType(PurchaseType.valueOf(rs.getString("purchaseType")));
                transaction.setPurchaseDate(rs.getDate("purchaseDate"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
