package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import enums.UserType;

public class UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    public boolean addUser(UserDTO user) {
        String sql = "INSERT INTO user (firstName, lastName, userPassword, userEmail, joinDate, userType) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getUserPassword());
            pstmt.setString(4, user.getUserEmail());
            pstmt.setDate(5, user.getJoinDate());
            pstmt.setString(6, user.getUserType().name());

            LOGGER.info("Executing query: " + pstmt.toString());
            int result = pstmt.executeUpdate();
            LOGGER.info("Insert result: " + result);
            return result > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to add user", e);
        }
        return false;
    }

    public boolean authenticate(String email, String password) {
        String sql = "SELECT * FROM user WHERE userEmail = ? AND userPassword = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            boolean authenticated = rs.next();
            LOGGER.info("User authenticated: " + authenticated);
            return authenticated;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Authentication failed", e);
        }
        return false;
    }

    public String getUserType(String email) {
        String sql = "SELECT userType FROM user WHERE userEmail = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String userType = rs.getString("userType");
                LOGGER.info("User type retrieved: " + userType);
                return userType;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to get user type", e);
        }
        return null;
    }

    public boolean isUserExists(String email) {
        String sql = "SELECT * FROM user WHERE userEmail = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to check if user exists", e);
        }
        return false;
    }

    public UserDTO getUserById(int userId) {
        String sql = "SELECT * FROM user WHERE userId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserId(rs.getInt("userId"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setUserEmail(rs.getString("userEmail"));
                user.setJoinDate(rs.getDate("joinDate"));
                user.setUserType(UserType.valueOf(rs.getString("userType").toUpperCase()));
                LOGGER.info("User retrieved: " + user);
                return user;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to get user by ID", e);
        }
        return null;
    }

    public UserDTO getUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE userEmail = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserId(rs.getInt("userId"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setUserEmail(rs.getString("userEmail"));
                user.setJoinDate(rs.getDate("joinDate"));
                user.setUserType(UserType.valueOf(rs.getString("userType").toUpperCase()));
                LOGGER.info("User retrieved: " + user);
                return user;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to get user by email", e);
        }
        return null;
    }
}
