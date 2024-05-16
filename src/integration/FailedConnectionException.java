package integration;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Exception that occurs when the inventory database couldn't be connected to.
 */
public class FailedConnectionException extends RuntimeException {
    public FailedConnectionException(LocalTime timeOfException){
        super("Unable to connect to inventory system, please check network connection. Failed connection occurred at: " + timeOfException.truncatedTo(ChronoUnit.SECONDS));
    }
}
