//Author: Jake Elliott//
//Professor: Islam Gomaa//
//Class: CST8288//
//Section: 032//
//Date: 6/9/24//

package interfaces;

/**
 * Interface for events.
 * Provides methods for calculating admission fees, getting descriptions,
 * getting event names, and getting event activities.
 * These methods will be implemented by specific event types.
 * Author: Jake Elliott
 */
public interface EventsInterface {
    
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
    
    /**
     * Gets the name of the event.
     * @return the event name
     */
    String getName();

    /**
     * Gets the activities of the event.
     * @return the event activities
     */
    String getActivities();
}
