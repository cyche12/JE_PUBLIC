package main;

import database.DBOperations;
import interfaces.Events;
import logger.LMSLogger;
import logger.LogLevel;
import libraries.AcademicLibrary;
import libraries.PublicLibrary;
import interfaces.Library;

/**
 * Main class for running the application.
 * @author Jake Elliott
 */
public class Main {

    /**
     * Main method for running the application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBOperations dbOperations = new DBOperations();

        Library academicLibrary = new AcademicLibrary();
        Library publicLibrary = new PublicLibrary();

        Events workshop = academicLibrary.createEvent("Workshop");
        Events movieNight = publicLibrary.createEvent("MovieNight");

        dbOperations.createEvent(workshop);
        dbOperations.createEvent(movieNight);
        
        LMSLogger logger = LMSLogger.getInstance();
        logger.log(LogLevel.INFO, "Thank you, the event has been logged.");
    }
}
