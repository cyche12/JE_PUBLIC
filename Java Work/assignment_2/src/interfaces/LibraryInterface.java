//Author: Jake Elliott//
//Professor: Islam Gomaa//
//Class: CST8288//
//Section: 032//
//Date: 6/9/24//

package interfaces;

/**
 * Interface for libraries.
 * Provides a method for creating events based on event type.
 * This method will be implemented by specific library types.
 * @author Jake Elliott
 */
public interface LibraryInterface {
   
    /**
     * Creates an event based on the event type provided.
     * @param eventType the type of event
     * @return the created event
     */
    EventsInterface createEvent(String eventType);
}
