package interfaces;

/**
 * Interface for libraries.
 * Provides a method for creating events based on event type.
 * This method will be implemented by specific library types.
 * @autor Jake Elliott
 */
public interface Library {
   
    /**
     * Creates an event based on the event type provided.
     * @param eventType the type of event
     * @return the created event
     */
    Events createEvent(String eventType);
}
