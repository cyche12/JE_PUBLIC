package libraries;

import events.AcademicLibraryBookLaunch;
import events.AcademicLibraryWorkshop;
import interfaces.Events;
import interfaces.Library;

/**
 * Class for creating events for an Academic Library.
 * Implements the Factory Method design pattern to create events.
 * @author Jake Elliott
 */
public class AcademicLibrary implements Library {

    /**
     * Creates an event based on the event type provided.
     * @param eventType the type of event
     * @return the created event
     */
    @Override
    public Events createEvent(String eventType) {
        return switch (eventType) {
            case "Workshop" -> new AcademicLibraryWorkshop();
            case "BookLaunch" -> new AcademicLibraryBookLaunch();
            default -> throw new IllegalArgumentException("Unknown event type for Academic Library.");
        };
    }
}
