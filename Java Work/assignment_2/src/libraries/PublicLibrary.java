package libraries;

import events.PublicLibraryKidsStoryTime;
import events.PublicLibraryMovieNight;
import interfaces.Events;
import interfaces.Library;

/**
 * Class for creating events for a Public Library.
 * Implements the Factory Method design pattern to create events.
 * @author Jake Elliott
 */
public class PublicLibrary implements Library {
    
    /**
     * Creates an event based on the event type provided.
     * @param eventType the type of event
     * @return the created event
     */
    @Override
    public Events createEvent(String eventType) {
        return switch (eventType) {
            case "MovieNight" -> new PublicLibraryMovieNight();
            case "KidsStoryTime" -> new PublicLibraryKidsStoryTime();
            default -> throw new IllegalArgumentException("Unknown event type for Public Library.");
        };
    }
}
