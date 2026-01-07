package logger;

/**
 * Class provides a logger for database operations.
 * Utilizes singleton design pattern to ensure single instance.
 * @author Jake Elliott
 */
public class LMSLogger {
    
    private static LMSLogger instance;

    private LMSLogger() {}

    /**
     * Returns the singleton instance of the logger.
     * @return instance of LMSLogger
     */
    public static LMSLogger getInstance() {
        synchronized (LMSLogger.class) {
            if (instance == null) {
                instance = new LMSLogger();
            }
        }
        return instance;
    }

    /**
     * Logs a message with the specified log level.
     * @param level the log level
     * @param message the message to log
     */
    public void log(LogLevel level, String message) {
        System.out.println("[" + level + "] " + message);
    }
}
