package events;

import interfaces.Events;

/**
 * Class for Public Library Movie Night event.
 * Implements the Events interface and provides specific behavior for this event type.
 * @autor Jake Elliott
 */
public class PublicLibraryMovieNight implements Events {
    
    private static final double RATE = 20.0;
    private static final int DURATION = 3;
    private final double fee;
    
    /**
     * Default constructor calculates the fee based on the rate and duration.
     */
    public PublicLibraryMovieNight() {
        this.fee = RATE * DURATION;
    }
    
    /**
     * Constructor with fee parameter.
     * @param fee the admission fee
     */
    public PublicLibraryMovieNight(double fee) {
        this.fee = fee;
    }

    /**
     * Calculates the admission fee for the event.
     * @return the admission fee
     */
    @Override
    public double calculateAdmissionFee() {
        return fee;
    }

    /**
     * Gets the description of the event.
     * @return the event description
     */
    @Override
    public String getDescription() {
        return "Public Library Movie Night";
    }
}
