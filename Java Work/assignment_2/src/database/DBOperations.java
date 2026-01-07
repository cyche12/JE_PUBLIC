package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import events.AcademicLibraryBookLaunch;
import events.AcademicLibraryWorkshop;
import events.PublicLibraryKidsStoryTime;
import events.PublicLibraryMovieNight;
import interfaces.Events;

/**
 * Class defines the operations performed by the database for each type of event.
 * Provides CRUD operations for events in the database.
 * @autor Jake Elliott
 */
public class DBOperations {
	
    private final Connection connection;

    /**
     * Constructor initializes the database connection.
     */
    public DBOperations() {
        this.connection = DBConnection.getInstance().getConnection();
    }

    /**
     * Creates an event in the database.
     * @param event the event to create
     */
    public void createEvent(Events event) {
        String sql = "INSERT INTO events (description, admission_fee) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, event.getDescription());
            pstmt.setDouble(2, event.calculateAdmissionFee());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads an event from the database.
     * @param eventId the ID of the event to read
     * @return the event, or null if not found
     */
    public Events readEvent(int eventId) {
        String sql = "SELECT * FROM events WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String description = rs.getString("description");
                double fee = rs.getDouble("admission_fee");

                return switch (description) {
                    case "Academic Library Workshop" -> new AcademicLibraryWorkshop(fee);
                    case "Academic Library Book Launch" -> new AcademicLibraryBookLaunch(fee);
                    case "Public Library Movie Night" -> new PublicLibraryMovieNight(fee);
                    case "Public Library Kids Story Time" -> new PublicLibraryKidsStoryTime(fee);
                    default -> null;
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an event in the database.
     * @param eventId the ID of the event to update
     * @param description the new description of the event
     * @param fee the new admission fee of the event
     */
    public void updateEvent(int eventId, String description, double fee) {
        String sql = "UPDATE events SET description = ?, admission_fee = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.setDouble(2, fee);
            pstmt.setInt(3, eventId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an event from the database.
     * @param eventId the ID of the event to delete
     */
    public void deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
