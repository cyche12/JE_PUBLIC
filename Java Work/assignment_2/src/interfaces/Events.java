package interfaces;

/**
 * Interface for events.
 * Provides methods for calculating admission fees and getting descriptions.
 * These methods will be implemented by specific event types.
 * @autor Jake Elliott
 */
public interface Events {
    
    /**
     * Calculates the admission fee for the event.
     * @return the admission fee
     */
    double calculateAdmissionFee();
    
    /**
     * Gets the description of the event.
     * @return the event description
     */
    String getDescription();
}
